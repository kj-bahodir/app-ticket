package ai.ecma.appticket.repository;

import ai.ecma.appticket.entity.PayBackTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PaybackTariffRepository extends JpaRepository<PayBackTariff, UUID> {

    @Query(value = "select p.* from pay_back_tariff p\n" +
            "join event e on e.id = p.event_id\n" +
            "where event_id =:eventId\n" +
            "and remining_hour <:reminingHour\n" +
            "order by remining_hour desc limit 1",
            nativeQuery = true)
    Optional<PayBackTariff> findFirstByReminingHour(@Param("eventId") UUID eventId,
                                                    @Param("reminingHour") Double reminingHour);
}
