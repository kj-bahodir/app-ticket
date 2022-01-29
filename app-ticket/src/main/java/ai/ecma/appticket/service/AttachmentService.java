package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.Attachment;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface AttachmentService {
    ApiResult<?> upload(MultipartHttpServletRequest request) throws IOException;

    ApiResult<CustomPage<Attachment>> getAll(int page, int size);

    ApiResult<Attachment> getById(UUID id);

    ApiResult<?> getFile(UUID id, HttpServletResponse response) throws IOException;
}
