package ai.ecma.appticket.repository;

import ai.ecma.appticket.entity.Bron;
import ai.ecma.appticket.enums.BronStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BronRepository extends JpaRepository<Bron, UUID> {
    List<Bron> findAllByExpireTimeBeforeAndBronStatusEnum(Timestamp currentTime, BronStatusEnum bronStatusEnum);

    List<Bron> findAllByBronStatusEnumAndTicketIdIn(BronStatusEnum bronStatusEnum, Collection<UUID> ticket_id);

    @Query(value = "select b.* from bron b\n" +
            "join ticket t on b.ticket_id = t.id\n" +
            "join event_session es on t.event_session_id = es.id\n" +
            "join event e on e.id = es.event_id\n" +
            "where bron_tariff_id =:bronTariffId",
            nativeQuery = true)
    List<Bron> findAllByBronTariff(@Param("bronTariffId") UUID bronTariffId);

    Optional<Bron> findByUserIdAndPaymentTicketIdAndTicketId(UUID user_id, UUID paymentTicket_id, UUID ticket_id);
    Optional<Bron> findFirstByUserIdAndPaymentTicketIdAndTicketId(UUID user_id, UUID paymentTicket_id, UUID ticket_id);


}
