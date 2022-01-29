package ai.ecma.appticket.repository;

import ai.ecma.appticket.entity.SeatTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeatTemplateRepository extends JpaRepository<SeatTemplate, UUID> {
}
