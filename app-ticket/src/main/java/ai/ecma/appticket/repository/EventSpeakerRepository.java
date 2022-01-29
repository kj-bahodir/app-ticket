package ai.ecma.appticket.repository;

import ai.ecma.appticket.entity.EventSpeaker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventSpeakerRepository extends JpaRepository<EventSpeaker, UUID> {
}
