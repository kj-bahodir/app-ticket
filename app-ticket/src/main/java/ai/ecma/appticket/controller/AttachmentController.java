package ai.ecma.appticket.controller;

import ai.ecma.appticket.entity.Attachment;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.utils.AppConstant;
import ai.ecma.appticket.utils.RestConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping(RestConstant.ATTACHMENT_CONTROLLER)
@Tag(name = "Rasmlar bilan ishlovchi operatsiyalar",description = "Rasmlar")
public interface AttachmentController {

    @Operation(summary = "Rasmni tizimga yuklash")
    @PostMapping("/upload")
    ApiResult<?> upload(MultipartHttpServletRequest request) throws IOException;

    @Operation(summary = "Rasmlar ma'lumotlarining listini sahifalab olish")
    @GetMapping("/info")
    ApiResult<CustomPage<Attachment>> getAll(@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) int page,
                                             @RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_SIZE) int size);

    @Operation(summary = "Rasmning ma'lumotini olish")
    @GetMapping("/info/{id}")
    ApiResult<Attachment> getById(@PathVariable UUID id);

    @Operation(summary = "Rasmni yuklab olish")
    @GetMapping("/download/{id}")
    ApiResult<?> getFile(@PathVariable UUID id, HttpServletResponse response) throws IOException;

}
