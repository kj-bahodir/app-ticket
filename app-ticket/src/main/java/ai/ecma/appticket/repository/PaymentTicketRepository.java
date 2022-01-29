package ai.ecma.appticket.repository;

import ai.ecma.appticket.entity.PaymentTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PaymentTicketRepository extends JpaRepository<PaymentTicket, UUID> {

    Optional<PaymentTicket> findFirstByTicketIdOrderByCreatedAtDesc(UUID ticket_id);

    @Query(value = "select pt.* from payment_ticket pt\n" +
            "join payment p on p.id = pt.payment_id\n" +
            "join orders o on o.id = p.order_id\n" +
            "where o.user_id=:userId\n" +
            "and pt.ticket_id=:ticketId\n" +
            "and pt.payment_ticket_status=:status\n" +
            "order by pt.created_at desc limit 1", nativeQuery = true)
    Optional<PaymentTicket> getBronTicketPayment(@Param("userId") UUID userId,
                                                 @Param("ticketId") UUID ticketId,
                                                 @Param("status") String status);
}
