package ai.ecma.appticket.repository;

import ai.ecma.appticket.entity.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpeakerRepository extends JpaRepository<Speaker, UUID> {
}
