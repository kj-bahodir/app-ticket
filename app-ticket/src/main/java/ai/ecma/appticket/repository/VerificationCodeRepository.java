package ai.ecma.appticket.repository;

import ai.ecma.appticket.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode,UUID> {
    Long countAllByPhoneNumberAndCreatedAtAfter(String phoneNumber, Timestamp pastTime);

    @Query(value = "select *\n" +
            "from verification_code vc\n" +
            "where phone_number = :phoneNumber\n" +
            "  and code = :code\n" +
            "  and created_at > :pastTime\n" +
            "  and confirmed = false\n" +
            "order by created_at desc\n" +
            "limit 1",nativeQuery = true)
    Optional<VerificationCode> getByCondition(@Param("phoneNumber") String phoneNumber,
                                              @Param("code") String code,
                                              @Param("pastTime") Timestamp pastTime);
//    Optional<VerificationCode> findAllByPhoneNumberAndCodeAndConfirmedFalseAndCreatedAtAfterOrderByCreatedAtDesc


    boolean  existsByPhoneNumberAndCodeAndConfirmedTrue(String phoneNumber, String code);
}
