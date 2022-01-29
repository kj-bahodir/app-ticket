package ai.ecma.appticket.repository;

import ai.ecma.appticket.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventTypeRepository extends JpaRepository<EventType, UUID> {
}
