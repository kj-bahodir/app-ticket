package ai.ecma.appticket.repository;

import ai.ecma.appticket.entity.Order;
import ai.ecma.appticket.enums.OrderTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findAllByCreatedAtBeforeAndFinishedFalseAndTypeNot(Timestamp createdAt, OrderTypeEnum type);

    @Transactional
    @Modifying
    void deleteAllByCreatedAtBeforeAndFinishedFalseAndType(Timestamp pastTime, OrderTypeEnum type);
}
