package ai.ecma.appticket.repository;

import ai.ecma.appticket.entity.PayType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PayTypeRepository extends JpaRepository<PayType, UUID> {
}
