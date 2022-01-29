package ai.ecma.appticket.repository;

import ai.ecma.appticket.entity.SeatTemplateChair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SeatTemplateChairRepository extends JpaRepository<SeatTemplateChair, UUID> {
    @Query(value ="select count(*) from (select distinct row from seat_template_chair\n" +
            "    where seat_template_id=:id and section=:section) as f",nativeQuery = true)
    long countAllByRow(@Param("id") UUID id,@Param("section") String section);


    long countAllBySeatTemplate_IdAndSectionAndRow(UUID seatTemplate_id, String section, String row);

    List<SeatTemplateChair> findAllBySeatTemplate_IdAndSection(UUID seatTemplate_id, String section);

    List<SeatTemplateChair> findAllBySeatTemplate_IdAndSectionAndRow(UUID seatTemplate_id, String section, String row);
}
