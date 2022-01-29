package ai.ecma.appticket.controller;

import ai.ecma.appticket.entity.Attachment;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AttachmentControllerImpl implements AttachmentController{

    private final AttachmentService attachmentService;

    @Override
    public ApiResult<?> upload(MultipartHttpServletRequest request) throws IOException {
        return attachmentService.upload(request);
    }

    @Override
    public ApiResult<CustomPage<Attachment>> getAll(int page, int size) {
        return attachmentService.getAll(page,size);
    }


    @Override
    public ApiResult<Attachment> getById(UUID id) {
        return attachmentService.getById(id);
    }

    @Override
    public ApiResult<?> getFile(UUID id, HttpServletResponse response) throws IOException {
        return attachmentService.getFile(id,response);

    }
}
