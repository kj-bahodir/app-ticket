package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.*;
import ai.ecma.appticket.enums.BronStatusEnum;
import ai.ecma.appticket.enums.OrderTypeEnum;
import ai.ecma.appticket.enums.PaymentTicketStatusEnum;
import ai.ecma.appticket.enums.SeatStatusEnum;
import ai.ecma.appticket.exception.RestException;
import ai.ecma.appticket.mapper.CustomMapper;
import ai.ecma.appticket.payload.*;
import ai.ecma.appticket.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final PaymentRepository paymentRepository;
    private final TicketHistoryRepository ticketHistoryRepository;
    private final BronRepository bronRepository;

    @Override
    public ApiResult<CustomPage<OrderResDto>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage = orderRepository.findAll(pageable);
        CustomPage<OrderResDto> orderResDtoCustomPage = orderCustomPage(orderPage);
        return ApiResult.successResponse(orderResDtoCustomPage);
    }

    @Override
    public ApiResult<OrderResDto> getOne(UUID id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Buyurtma topilmadi"));
        return ApiResult.successResponse(CustomMapper.orderToDto(order));
    }

    @Override
    public ApiResult<CustomPage<OrderResDto>> getByCreatedAt(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Order> orderPage = orderRepository.findAll(pageable);
        CustomPage<OrderResDto> orderResDtoCustomPage = orderCustomPage(orderPage);
        return ApiResult.successResponse(orderResDtoCustomPage);
    }

    @Override
    public ApiResult<PayDto> add(OrderReqDto orderReqDto, User user) {
        List<Ticket> ticketList = ticketRepository.findAllByIdInAndActiveTrue(orderReqDto.getTickets());
        if (orderReqDto.getTickets().size() != ticketList.size())
            throw new RestException(HttpStatus.BAD_REQUEST, "Chiptalar topilmadi");
        double orderPrice = 0;
        String[] ticketIdArray = new String[orderReqDto.getTickets().size()];
        boolean isBron = orderReqDto.getType().equals(OrderTypeEnum.BRON);
        for (int i = 0; i < ticketList.size(); i++) {
            Ticket ticket = ticketList.get(i);
            ticket.setStatus(SeatStatusEnum.PAYMENT_PROCESS);
            if (isBron) {
                BronTariff bronTariff = ticket.getEventSession().getEvent().getBronTariff();
                if (bronTariff.isDisable())
                    throw new RestException(HttpStatus.BAD_REQUEST, "Bron qilish muddati tugagan");
                orderPrice += ticket.getPrice() * (bronTariff.getPercent() / 100);
            } else {
                orderPrice += ticket.getPrice();
            }
            ticketIdArray[i] = ticket.getId().toString();
        }
        Order order = new Order(
                orderPrice,
                user,
                ticketIdArray,
                false,
                orderReqDto.getType()
        );
        ticketRepository.saveAll(ticketList);
        orderRepository.save(order);
        return ApiResult.successResponse(CustomMapper.orderToPayDto(order));
    }

    @Override
    public ApiResult<?> paymentProcess(PayDto payDto, User user) {
        Order order = orderRepository.findById(payDto.getOrderId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "User topilmadi"));
        if (order.getParentOrderId() != null)
            return remainingPaymentProcess(payDto, order, user);
        List<UUID> ticketIdList = Arrays.stream(order.getTickets()).map(UUID::fromString).collect(Collectors.toList());
        List<Ticket> ticketList = ticketRepository.findAllById(ticketIdList);
        if (ticketList.size() != ticketIdList.size())
            throw new RestException(HttpStatus.BAD_REQUEST, "Chipta topilmadi");
        if (!Objects.equals(payDto.getPrice(), order.getOrderPrice()))
            throw new RestException(HttpStatus.BAD_REQUEST, "Buyurtma narxi xato");
        List<TicketHistory> ticketHistories = new ArrayList<>();
        List<PaymentTicket> paymentTickets = new ArrayList<>();
        List<Bron> bronList = new ArrayList<>();
        Payment payment = new Payment(
                order.getOrderPrice(),
                order,
                user.getPayType()
        );
        for (Ticket ticket : ticketList) {
            if (!ticket.getStatus().equals(SeatStatusEnum.PAYMENT_PROCESS))
                throw new RestException(HttpStatus.CONFLICT, "Chipta to'lovini vaqti utib ketgan");
            ticket.setStatus(order.getType().equals(OrderTypeEnum.SOLD) ? SeatStatusEnum.SOLD : SeatStatusEnum.BOOKED);
            TicketHistory ticketHistory = new TicketHistory(
                    ticket,
                    ticket.getStatus(),
                    order.getUser()
            );
            ticketHistories.add(ticketHistory);

            Double paymentTicketPrice = order.getType().equals(OrderTypeEnum.SOLD)
                    ? ticket.getPrice()
                    : (ticket.getPrice() * ticket.getEventSession().getEvent().getBronTariff().getPercent());
            PaymentTicket paymentTicket = new PaymentTicket(
                    payment,
                    ticket,
                    ticket.getStatus().equals(SeatStatusEnum.SOLD) ? PaymentTicketStatusEnum.SOLD : PaymentTicketStatusEnum.BRON,
                    paymentTicketPrice
            );
            paymentTickets.add(paymentTicket);

            if (order.getType().equals(OrderTypeEnum.BRON)) {
                Bron bron = new Bron(
                        user,
                        ticket,
                        paymentTicket,
                        new Timestamp((long) (System.currentTimeMillis() + (ticket.getEventSession().getEvent().getBronTariff().getLifeTime() * 3600 * 1000))),
                        BronStatusEnum.ACTIVE
                );
                bronList.add(bron);
            }
            ticket.setUser(order.getUser());
        }
        payment.setPaymentTickets(paymentTickets);
        ticketRepository.saveAll(ticketList);
        paymentRepository.save(payment);
        bronRepository.saveAll(bronList);
        ticketHistoryRepository.saveAll(ticketHistories);

        return ApiResult.successResponse("Ok");
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        return null;
    }

    public CustomPage<OrderResDto> orderCustomPage(Page<Order> orderPage) {
        return new CustomPage<>(
                orderPage.getContent().stream().map(CustomMapper::orderToDto).collect(Collectors.toList()),
                orderPage.getNumberOfElements(),
                orderPage.getNumber(),
                orderPage.getTotalElements(),
                orderPage.getTotalPages(),
                orderPage.getSize()
        );
    }

    @Override
    public ApiResult<?> orderForBron(OrderBronDto orderBronDto, User user) {
        Order order = orderRepository.findById(orderBronDto.getOrderId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Buyurtma topilmadi!"));
        List<UUID> orderTickets = Arrays.stream(order.getTickets()).map(UUID::fromString).collect(Collectors.toList());
        boolean containsAll = orderTickets.containsAll(orderBronDto.getTickets());
        if (!containsAll) throw new RestException(HttpStatus.BAD_REQUEST, "Xatolik yuz berdi!");
        List<Bron> activeBrons = bronRepository.findAllByBronStatusEnumAndTicketIdIn(BronStatusEnum.ACTIVE, orderBronDto.getTickets());
        if (activeBrons.size() != orderBronDto.getTickets().size())
            throw new RestException(HttpStatus.BAD_REQUEST, "Xatolik yuz berdi!");

        double totalPrice = 0;
        String[] tickets = new String[activeBrons.size()];
        for (int i = 0; i < activeBrons.size(); i++) {
            Double percent = activeBrons.get(i).getTicket().getEventSession().getEvent().getBronTariff().getPercent();
            Double price = activeBrons.get(i).getTicket().getPrice();
            totalPrice += price * (1 - (percent / 100));
            tickets[i] = activeBrons.get(i).getTicket().getId().toString();
        }
        Order newOrder = new Order(
                totalPrice,
                order.getUser(),
                tickets,
                false,
                OrderTypeEnum.PAY_AFTER,
                orderBronDto.getOrderId()
        );
        orderRepository.save(newOrder);
        return ApiResult.successResponse(CustomMapper.orderToPayDto(newOrder));
    }

    /**
     * Ushbu metodning maqsadi - bron qilingan chiptalar uchun pulini to'lash.
     *
     * @param payDto - OrderId va summa keladi.
     * @param order  - Order bron qilingan chiptalarning qolgan pullarini to'lash. Orderni ichida ota OrderId bor.
     * @param user   - Tizimdagi user - Click, Payme ...
     * @return
     */
    @Transactional
    public ApiResult<?> remainingPaymentProcess(PayDto payDto, Order order, User user) {
        if (!Objects.equals(order.getOrderPrice(), payDto.getPrice()))
            throw new RestException(HttpStatus.BAD_REQUEST, "Xato summa kiritildi");
        Order parentOrder = orderRepository.findById(order.getParentOrderId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Buyurtma topilmadi"));
        List<UUID> orderTickets = Arrays.stream(order.getTickets()).map(UUID::fromString).collect(Collectors.toList());
        List<Bron> activeBrons = bronRepository.findAllByBronStatusEnumAndTicketIdIn(BronStatusEnum.ACTIVE, orderTickets);
        if (orderTickets.size() != activeBrons.size())
            throw new RestException(HttpStatus.BAD_REQUEST, "Xatolik yuz berdi");
        List<Ticket> tickets = new ArrayList<>();
        List<TicketHistory> ticketHistories = new ArrayList<>();
        List<PaymentTicket> paymentTickets = new ArrayList<>();
        Payment payment = new Payment(
                payDto.getPrice(),
                order,
                user.getPayType()
        );
        for (Bron activeBron : activeBrons) {
            activeBron.setBronStatusEnum(BronStatusEnum.COMPLETE);
            Ticket ticket = activeBron.getTicket();
            ticket.setStatus(SeatStatusEnum.SOLD);
            tickets.add(ticket);
            TicketHistory ticketHistory = new TicketHistory(
                    ticket,
                    SeatStatusEnum.SOLD,
                    user
            );
            ticketHistories.add(ticketHistory);
            Double bronTariffPercent = activeBron.getTicket().getEventSession().getEvent().getBronTariff().getPercent();
            double remainMoney = ticket.getPrice() * (1 - (bronTariffPercent / 100));
            PaymentTicket paymentTicket = new PaymentTicket(
                    payment,
                    ticket,
                    PaymentTicketStatusEnum.PAY_AFTER_BRON,
                    remainMoney
            );
            paymentTickets.add(paymentTicket);
        }
        List<String> remainTickets = Arrays.stream(parentOrder.getTickets()).filter(s -> !orderTickets.contains(UUID.fromString(s))).collect(Collectors.toList());
        String[] ticketIdArray = new String[remainTickets.size()];
        ticketIdArray = remainTickets.toArray(ticketIdArray);
        parentOrder.setTickets(ticketIdArray);
        order.setFinished(true);
        payment.setPaymentTickets(paymentTickets);
        ticketHistoryRepository.saveAll(ticketHistories);
        paymentRepository.save(payment);
        orderRepository.save(order);
        orderRepository.save(parentOrder);
        bronRepository.saveAll(activeBrons);
        ticketRepository.saveAll(tickets);
        return ApiResult.successResponse("OK");
    }
}
