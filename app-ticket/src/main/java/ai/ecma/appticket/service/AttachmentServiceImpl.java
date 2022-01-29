package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.Attachment;
import ai.ecma.appticket.entity.AttachmentContent;
import ai.ecma.appticket.exception.RestException;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.repository.AttachmentContentRepository;
import ai.ecma.appticket.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService{

    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    @Override
    public ApiResult<?> upload(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file!=null){
            String originalFilename = file.getOriginalFilename();
            Attachment attachment=new Attachment(
                    originalFilename,
                    file.getContentType(),
                    file.getSize()
            );
            Attachment save = attachmentRepository.save(attachment);
            AttachmentContent attachmentContent=new AttachmentContent(
                    file.getBytes(),
                    save
            );
            attachmentContentRepository.save(attachmentContent);
            return ApiResult.successResponse(attachment.getId());
        }
        return ApiResult.successResponse("Error");
    }

    @Override
    public ApiResult<CustomPage<Attachment>> getAll(int page, int size) {
        Pageable pageable= PageRequest.of(page,size);
        Page<Attachment> attachmentPage = attachmentRepository.findAll(pageable);
        CustomPage<Attachment> attachmentCustomPage = attachmentCustomPage(attachmentPage);
        return ApiResult.successResponse(attachmentCustomPage);
    }

    @Override
    public ApiResult<Attachment> getById(UUID id) {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "File topilmadi"));
        return ApiResult.successResponse(attachment);
    }

    @Override
    public ApiResult<?> getFile(UUID id, HttpServletResponse response) throws IOException {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "File topilmadi"));
        AttachmentContent attachmentContent = attachmentContentRepository.findByAttachmentId(attachment.getId());
        if (attachmentContent==null)
            throw new RestException(HttpStatus.NOT_FOUND,"Bunday content topilmadi");
        response.setHeader("Content-Disposition","attachment; filename=\""+attachment.getName()+"\"");
        response.setContentType(attachment.getContentType());
        FileCopyUtils.copy(attachmentContent.getBytes(),response.getOutputStream());
        return ApiResult.successResponse("Successfully");
    }

    public CustomPage<Attachment> attachmentCustomPage(Page<Attachment> attachmentPage){
        return new CustomPage<>(
                attachmentPage.getContent(),
                attachmentPage.getNumberOfElements(),
                attachmentPage.getNumber(),
                attachmentPage.getTotalElements(),
                attachmentPage.getTotalPages(),
                attachmentPage.getSize()
        );
    }
}
