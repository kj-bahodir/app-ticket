package ai.ecma.appticket.repository;

import ai.ecma.appticket.entity.TicketHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketHistoryRepository extends JpaRepository<TicketHistory, UUID> {
}
