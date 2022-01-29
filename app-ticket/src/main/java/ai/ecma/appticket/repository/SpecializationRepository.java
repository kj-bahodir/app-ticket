package ai.ecma.appticket.repository;

import ai.ecma.appticket.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Muhammad Mo'minov
 * 07.10.2021
 */
public interface SpecializationRepository extends JpaRepository<Specialization, UUID> {
}
