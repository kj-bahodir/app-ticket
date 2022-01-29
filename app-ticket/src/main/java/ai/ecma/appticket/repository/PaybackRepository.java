package ai.ecma.appticket.repository;

import ai.ecma.appticket.entity.PayBack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaybackRepository extends JpaRepository<PayBack, UUID> {
}
