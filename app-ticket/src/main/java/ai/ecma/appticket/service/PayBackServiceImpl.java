package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.*;
import ai.ecma.appticket.enums.PaymentTicketStatusEnum;
import ai.ecma.appticket.enums.SeatStatusEnum;
import ai.ecma.appticket.exception.RestException;
import ai.ecma.appticket.mapper.CustomMapper;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.RefundReqDto;
import ai.ecma.appticket.payload.RefundResDto;
import ai.ecma.appticket.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PayBackServiceImpl implements PayBackService {
    private final PaybackRepository paybackRepository;
    private final TicketRepository ticketRepository;
    private final PaymentTicketRepository paymentTicketRepository;
    private final PaybackTariffRepository paybackTariffRepository;
    private final BronRepository bronRepository;
    private final TicketHistoryRepository ticketHistoryRepository;


    public PayBackServiceImpl(PaybackRepository paybackRepository, TicketRepository ticketRepository, PaymentTicketRepository paymentTicketRepository, PaybackTariffRepository paybackTariffRepository, BronRepository bronRepository, TicketHistoryRepository ticketHistoryRepository) {
        this.paybackRepository = paybackRepository;
        this.ticketRepository = ticketRepository;
        this.paymentTicketRepository = paymentTicketRepository;
        this.paybackTariffRepository = paybackTariffRepository;
        this.bronRepository = bronRepository;
        this.ticketHistoryRepository = ticketHistoryRepository;
    }


    @Override
    public ApiResult<RefundResDto> refund(RefundReqDto refundReqDto, User user) {
        List<Ticket> ticketList = ticketRepository.findAllById(refundReqDto.getTicketIdList());
        User user1 = ticketList.get(0).getUser();
        List<TicketHistory> ticketHistoryList = new ArrayList<>();
        List<PayBack> payBackList = new ArrayList<>();
        List<PaymentTicket> paymentTicketList = new ArrayList<>();
        if (refundReqDto.getTicketIdList().size() != ticketList.size())
            throw new RestException(HttpStatus.BAD_REQUEST, "Chiptalar topilmadi");

        double price = 0;
        for (Ticket ticket : ticketList) {
            if (!ticket.getUser().getId().equals(user.getId()) || !ticket.getStatus().equals(SeatStatusEnum.SOLD))
                throw new RestException(HttpStatus.BAD_REQUEST, "BU chipta sizga tegishli emas!");

            double remainHour = (double) (ticket.getEventSession().getStartTime().getTime() - System.currentTimeMillis()) / (1000 * 3600);

            PayBackTariff payBackTariff = paybackTariffRepository.findFirstByReminingHour(
                    ticket.getEventSession().getEvent().getId(),
                    remainHour
            ).orElseThrow(() -> new RestException(HttpStatus.BAD_REQUEST, "Pay back tariff topilmadi!"));

            PaymentTicket paymentTicket = paymentTicketRepository.findFirstByTicketIdOrderByCreatedAtDesc(ticket.getId())
                    .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "To'lov topilmadi!"));

            PayBack payBack = new PayBack(
                    payBackTariff,
                    (payBackTariff.getPercent() / 100d) * ticket.getPrice(),
                    refundReqDto.getCardNumber()
            );

            price += payBack.getAmount();
            payBackList.add(payBack);
            paymentTicket.setPaymentTicketStatus(PaymentTicketStatusEnum.PAY_BACK);
            if (!Objects.equals(paymentTicket.getPrice(), ticket.getPrice())) {
                PaymentTicket bronTicketPayment = paymentTicketRepository.getBronTicketPayment(
                        user.getId(),
                        ticket.getId(),
                        PaymentTicketStatusEnum.BRON.name()
                ).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "To'lov topilmadi"));

                bronTicketPayment.setPaymentTicketStatus(PaymentTicketStatusEnum.PAY_BACK);
                Bron bron = bronRepository.findFirstByUserIdAndPaymentTicketIdAndTicketId(
                        user.getId(),
                        bronTicketPayment.getId(),
                        ticket.getId()
                ).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Bron Topilmadi!"));
                bronRepository.deleteById(bron.getId());
                paymentTicketList.add(bronTicketPayment);
            }

            ticket.setStatus(SeatStatusEnum.VACANT);
            TicketHistory ticketHistory = new TicketHistory(
                    ticket,
                    ticket.getStatus(),
                    user
            );
            ticketHistoryList.add(ticketHistory);
            paymentTicketList.add(paymentTicket);

        }


        ticketRepository.saveAll(ticketList);
        paymentTicketRepository.saveAll(paymentTicketList);
        paybackRepository.saveAll(payBackList);
        ticketHistoryRepository.saveAll(ticketHistoryList);
        List<UUID> paybackIdList = payBackList.stream().map(PayBack::getId).collect(Collectors.toList());

        return ApiResult.successResponse(CustomMapper.payBackToRefundDto(paybackIdList, refundReqDto.getCardNumber(), refundReqDto.getTicketIdList(), price));
    }

}
