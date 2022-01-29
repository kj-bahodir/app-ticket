package ai.ecma.appticket.repository;

import ai.ecma.appticket.entity.BronTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.UUID;

public interface BronTariffRepository extends JpaRepository<BronTariff, UUID> {
    @Transactional
    @Modifying
    @Query(value = "update bron_tariff set disable=true where expire_time>=:currentTime", nativeQuery = true)
    void disableBronTariff(@Param("currentTime") Timestamp currentTime);

}
