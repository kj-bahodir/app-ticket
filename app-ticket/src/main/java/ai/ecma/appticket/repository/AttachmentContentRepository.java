package ai.ecma.appticket.repository;

import ai.ecma.appticket.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, UUID> {

    AttachmentContent findByAttachmentId(UUID attachment_id);
}
