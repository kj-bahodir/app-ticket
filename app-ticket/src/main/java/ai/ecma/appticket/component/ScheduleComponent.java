package ai.ecma.appticket.component;

import ai.ecma.appticket.entity.Bron;
import ai.ecma.appticket.entity.Order;
import ai.ecma.appticket.entity.Ticket;
import ai.ecma.appticket.enums.BronStatusEnum;
import ai.ecma.appticket.enums.OrderTypeEnum;
import ai.ecma.appticket.enums.SeatStatusEnum;
import ai.ecma.appticket.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class ScheduleComponent {
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final BronRepository bronRepository;
    private final BronTariffRepository bronTariffRepository;
    private final EventSessionRepository eventSessionRepository;

    @Value(value = "${orderLifeTime}")
    private Long orderLifeTime;

    @Scheduled(fixedRate = 60000L)
    private void orderRemover() {
        Timestamp pastTime = new Timestamp(System.currentTimeMillis() - orderLifeTime);
        List<Order> orderList = orderRepository.findAllByCreatedAtBeforeAndFinishedFalseAndTypeNot(pastTime, OrderTypeEnum.PAY_AFTER);
        List<UUID> tickedIdList = new ArrayList<>();
        if (!orderList.isEmpty()) {
            for (Order order : orderList) {
                tickedIdList.addAll(Arrays.stream(order.getTickets()).map(UUID::fromString).collect(Collectors.toList()));
            }
            ticketRepository.updateStatus(
                    SeatStatusEnum.VACANT.name(),
                    tickedIdList
            );
        }
        orderRepository.deleteAllByCreatedAtBeforeAndFinishedFalseAndType(pastTime, OrderTypeEnum.PAY_AFTER);
    }

    @Scheduled(fixedRate = 60000L)
    private void bronRemover() {
        List<Bron> bronList = bronRepository.findAllByExpireTimeBeforeAndBronStatusEnum(
                new Timestamp(System.currentTimeMillis()),
                BronStatusEnum.ACTIVE
        );
        List<Ticket> ticketList = new ArrayList<>();
        for (Bron bron : bronList) {
            bron.setBronStatusEnum(BronStatusEnum.CANCEL);
            Ticket ticket = bron.getTicket();
            ticket.setStatus(SeatStatusEnum.VACANT);
            ticketList.add(ticket);
        }
        bronRepository.saveAll(bronList);
        ticketRepository.saveAll(ticketList);
    }

    @Scheduled(fixedRate = 60000L)
    private void bronTariffDisabler() {
        bronTariffRepository.disableBronTariff(new Timestamp(System.currentTimeMillis()));
    }


    @Scheduled(fixedRate = 60000L)
    private void removeOldTicketAfterSomeTime() {
        List<String> expiredIds = eventSessionRepository.findAllByExpiredEventSessions(new Timestamp(System.currentTimeMillis()));
        if (expiredIds.isEmpty())
            return;
        List<UUID> uuidList = expiredIds.stream().map(UUID::fromString).collect(Collectors.toList());
        ticketRepository.updateActiveFieldFalse(uuidList);
        eventSessionRepository.updateActiveFieldFalse(uuidList);
    }
}
