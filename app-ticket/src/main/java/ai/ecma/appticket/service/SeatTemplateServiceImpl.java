package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.SeatTemplate;
import ai.ecma.appticket.exception.RestException;
import ai.ecma.appticket.mapper.CustomMapper;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.SeatTemplateReqDto;
import ai.ecma.appticket.payload.SeatTemplateResDto;
import ai.ecma.appticket.repository.AttachmentRepository;
import ai.ecma.appticket.repository.SeatTemplateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SeatTemplateServiceImpl implements SeatTemplateService {
    private final SeatTemplateRepository seatTemplateRepository;
    private final AttachmentRepository attachmentRepository;


    public SeatTemplateServiceImpl(SeatTemplateRepository seatTemplateRepository, AttachmentRepository attachmentRepository, AttachmentRepository attachmentRepository1) {
        this.seatTemplateRepository = seatTemplateRepository;
        this.attachmentRepository = attachmentRepository1;
    }

    @Override
    public ApiResult<CustomPage<SeatTemplateResDto>> getSeatTemplateList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name", "createdAt"));
        Page<SeatTemplate> seatTemplates = seatTemplateRepository.findAll(pageable);
        CustomPage<SeatTemplateResDto> seatTemplateResDtoCustomPage = seatTemplateResDtoToCustomPage(seatTemplates);
        return ApiResult.successResponse(seatTemplateResDtoCustomPage);

    }

    @Override
    public ApiResult<SeatTemplateResDto> getSeatTemplateById(UUID id) {
        SeatTemplate seatTemplate = seatTemplateRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.BAD_REQUEST, "Berilgan idlik SeatTemplate topilmadi"));
        return ApiResult.successResponse(CustomMapper.seatTemplateToResDto(seatTemplate));
    }

    @Override
    public ApiResult<SeatTemplateResDto> addSeatTemplate(SeatTemplateReqDto seatTemplateReqDto) {
        SeatTemplate seatTemplate = seatTemplateRepository.findById(Objects.isNull(seatTemplateReqDto.getId())
                ? UUID.randomUUID()
                : seatTemplateReqDto.getId()).orElseGet(SeatTemplate::new);
        seatTemplate.setName(seatTemplateReqDto.getName());
        seatTemplate.setSchema(attachmentRepository.findById(seatTemplateReqDto.getSchemaId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,"Schema topilmadi")));


        seatTemplateRepository.save(seatTemplate);
        return ApiResult.successResponse(CustomMapper.seatTemplateToResDto(seatTemplate));

    }

    @Override
    public ApiResult<SeatTemplateResDto> editSeatTemplate(SeatTemplateReqDto seatTemplateReqDto, UUID id) {
        SeatTemplate seatTemplate = seatTemplateRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.BAD_REQUEST, "Edit qilinmoqchi bo'lgan idlik seatTemplate topilmadi"));
        seatTemplate.setName(seatTemplateReqDto.getName());
        seatTemplate.setSchema(attachmentRepository.findById(seatTemplateReqDto.getSchemaId()).orElseThrow(() -> new RestException(HttpStatus.BAD_REQUEST, "Schema topilmadi")));
        seatTemplateRepository.save(seatTemplate);
        return ApiResult.successResponse(CustomMapper.seatTemplateToResDto(seatTemplate));
    }


    @Override
    public ApiResult<?> deleteSeatTemplateById(UUID id) {
        try {
            seatTemplateRepository.deleteById(id);
            return ApiResult.successResponse("SeatTemplate muvaffaqiyatli o'chirildi");
        } catch (Exception e) {
            throw new RestException(HttpStatus.BAD_REQUEST, "O'chiriladigan SeatTemplate idsi xato kiritildi");
        }
    }

    public CustomPage<SeatTemplateResDto> seatTemplateResDtoToCustomPage(Page<SeatTemplate> seatTemplatePage) {
        return new CustomPage<>(
                seatTemplatePage.getContent().stream().map(CustomMapper::seatTemplateToResDto).collect(Collectors.toList()),
                seatTemplatePage.getNumberOfElements(),
                seatTemplatePage.getNumber(),
                seatTemplatePage.getTotalElements(),
                seatTemplatePage.getTotalPages(),
                seatTemplatePage.getSize()
        );
    }
}
