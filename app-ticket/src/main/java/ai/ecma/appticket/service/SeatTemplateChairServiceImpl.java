package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.SeatTemplate;
import ai.ecma.appticket.entity.SeatTemplateChair;
import ai.ecma.appticket.exception.RestException;
import ai.ecma.appticket.mapper.CustomMapper;
import ai.ecma.appticket.payload.AddSeatTemplateChairDto;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.SeatTemplateChairReqDto;
import ai.ecma.appticket.payload.SeatTemplateChairResDto;
import ai.ecma.appticket.payload.seatTemplate.*;
import ai.ecma.appticket.repository.SeatTemplateChairRepository;
import ai.ecma.appticket.repository.SeatTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SeatTemplateChairServiceImpl implements SeatTemplateChairService {
    private final SeatTemplateChairRepository seatTemplateChairRepository;
    private final SeatTemplateRepository seatTemplateRepository;

    @Override
    public ApiResult<SeatTemplateDto> getAllChairsBySeatTemplate(UUID seatTemplateId) {
        SeatTemplate seatTemplate = seatTemplateRepository.findById(seatTemplateId).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Seat template topilmadi"));
        return ApiResult.successResponse(CustomMapper.seatTemplateToDto(seatTemplate));
    }

    @Override
    public ApiResult<SeatTemplateChairResDto> getOneChair(UUID id) {
        SeatTemplateChair seatTemplateChair = seatTemplateChairRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Joy topilmadi!"));
        return ApiResult.successResponse(CustomMapper.chairToDTO(seatTemplateChair));
    }

    @Override
    public ApiResult<SeatTemplateDto> addSection(SectionAddDto sectionAddDto) {
        SeatTemplate seatTemplate = seatTemplateRepository.findById(sectionAddDto.getSeatTemplateId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "SeatTemplate not foun"));
        List<SeatTemplateChair> seatTemplateChairList = new ArrayList<>();
        for (int i = 1; i <= sectionAddDto.getRowCount(); i++) {
            for (int j = 1; j <= sectionAddDto.getChairCountPerRow(); j++) {
                seatTemplateChairList.add(
                        new SeatTemplateChair(
                                seatTemplate,
                                sectionAddDto.getPrice(),
                                sectionAddDto.getSeatStatusEnum(),
                                sectionAddDto.getName(),
                                String.valueOf(i),
                                String.valueOf(j)
                        )
                );

            }
        }
        seatTemplateChairRepository.saveAll(seatTemplateChairList);
        return ApiResult.successResponse(CustomMapper.seatTemplateToDto(seatTemplate));
    }

    @Override
    public ApiResult<SeatTemplateDto> addRow(RowAddDto rowAddDto) {
        SeatTemplate seatTemplate = seatTemplateRepository.findById(rowAddDto.getSeatTemplateId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "SeatTemplate topilmadi"));
        List<SeatTemplateChair> seatTemplateChairList = new ArrayList<>();
        long row = seatTemplateChairRepository.countAllByRow(rowAddDto.getSeatTemplateId(), rowAddDto.getSectionName());
        if(row==0) throw new RestException(HttpStatus.NOT_FOUND, "Section topilmadi!");
        for (int i = 1; i <=rowAddDto.getChairCountPerRow() ; i++) {
            seatTemplateChairList.add(
                    new SeatTemplateChair(
                            seatTemplate,
                            rowAddDto.getPrice(),
                            rowAddDto.getSeatStatusEnum(),
                            rowAddDto.getSectionName(),
                            String.valueOf(row+1),
                            String.valueOf(i)

                    )
            );
        }
        seatTemplateChairRepository.saveAll(seatTemplateChairList);
        return ApiResult.successResponse(CustomMapper.seatTemplateToDto(seatTemplate));
    }

    @Override
    public ApiResult<SeatTemplateDto> addChair(ChairAddDTO chairAddDTO) {
        SeatTemplate seatTemplate = seatTemplateRepository.findById(chairAddDTO.getSeatTemplateId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "SeatTemplate topilmadi"));
        long chair = seatTemplateChairRepository.countAllBySeatTemplate_IdAndSectionAndRow(seatTemplate.getId(), chairAddDTO.getSectionName(), chairAddDTO.getRowName());
        if(chair==0) throw new RestException(HttpStatus.NOT_FOUND, "Section yoki qator topilmadi!");
        SeatTemplateChair seatTemplateChair=new SeatTemplateChair(
                seatTemplate,
                chairAddDTO.getPrice(),
                chairAddDTO.getSeatStatusEnum(),
                chairAddDTO.getSectionName(),
                chairAddDTO.getRowName(),
                String.valueOf(chair+1)
        );
        seatTemplateChairRepository.save(seatTemplateChair);
        return ApiResult.successResponse(CustomMapper.seatTemplateToDto(seatTemplate));
    }

    @Override
    public ApiResult<SeatTemplateDto> editSection(SectionEditDto sectionEditDto) {
        SeatTemplate seatTemplate = seatTemplateRepository.findById(sectionEditDto.getSeatTemplateId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "SeatTemplate topilmadi"));
        List<SeatTemplateChair> chairList = seatTemplateChairRepository.findAllBySeatTemplate_IdAndSection(sectionEditDto.getSeatTemplateId(), sectionEditDto.getOldName());
        if(chairList.isEmpty()) throw new RestException(HttpStatus.NOT_FOUND, "Section topilmadi!");
        for (SeatTemplateChair chair : chairList) {
            chair.setPrice(sectionEditDto.getPrice()!=null
                    ? sectionEditDto.getPrice() : chair.getPrice());
            chair.setStatus(sectionEditDto.getSeatStatusEnum()!=null
                    ?sectionEditDto.getSeatStatusEnum():chair.getStatus());
            chair.setSection(sectionEditDto.getNewName());
        }
        seatTemplateChairRepository.saveAll(chairList);
        return ApiResult.successResponse(CustomMapper.seatTemplateToDto(seatTemplate));
    }

    @Override
    public ApiResult<SeatTemplateDto> editRow(RowEditDto rowEditDto) {
        SeatTemplate seatTemplate = seatTemplateRepository.findById(rowEditDto.getSeatTemplateId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "SeatTemplate topilmadi"));
        List<SeatTemplateChair> chairs = seatTemplateChairRepository.findAllBySeatTemplate_IdAndSectionAndRow(seatTemplate.getId(), rowEditDto.getSection(), rowEditDto.getRow());
        if(chairs.isEmpty()) throw new RestException(HttpStatus.NOT_FOUND, "Qator topilmadi!");
        for (SeatTemplateChair chair : chairs) {
            chair.setPrice(rowEditDto.getPrice()!=null
                    ? rowEditDto.getPrice() : chair.getPrice());
            chair.setStatus(rowEditDto.getSeatStatusEnum()!=null
                    ?rowEditDto.getSeatStatusEnum():chair.getStatus());
            chair.setSection(rowEditDto.getSection());
        }
        seatTemplateChairRepository.saveAll(chairs);
        return ApiResult.successResponse(CustomMapper.seatTemplateToDto(seatTemplate));
    }

    @Override
    public ApiResult<SeatTemplateDto> editChair(ChairEditDto chairEditDto) {
        SeatTemplateChair seatTemplateChair = seatTemplateChairRepository.findById(chairEditDto.getId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "seat topilmadi"));
        seatTemplateChair.setPrice(chairEditDto.getPrice()!=null? chairEditDto.getPrice() : seatTemplateChair.getPrice());
        seatTemplateChair.setStatus(chairEditDto.getSeatStatusEnum()!=null?chairEditDto.getSeatStatusEnum():seatTemplateChair.getStatus());
        seatTemplateChairRepository.save(seatTemplateChair);
        return ApiResult.successResponse(CustomMapper.seatTemplateToDto(seatTemplateChair.getSeatTemplate()));
    }

    @Override
    public ApiResult<?> deleteSection(SectionDeleteDto sectionDeleteDto) {
        List<SeatTemplateChair> chairList = seatTemplateChairRepository.findAllBySeatTemplate_IdAndSection(sectionDeleteDto.getSeatTemplateId(), sectionDeleteDto.getSectionName());
        if(chairList.isEmpty()) throw new RestException(HttpStatus.NOT_FOUND, "Section topilmadi!");
        seatTemplateChairRepository.deleteAll(chairList);
        return ApiResult.successResponse("Section o'chirilidi!");
    }

    @Override
    public ApiResult<?> deleteRow(RowDeleteDto rowDeleteDto) {
        List<SeatTemplateChair> chairList = seatTemplateChairRepository.findAllBySeatTemplate_IdAndSectionAndRow(rowDeleteDto.getSeatTemplateId(), rowDeleteDto.getSectionName(), rowDeleteDto.getRowName());
        if(chairList.isEmpty()) throw new RestException(HttpStatus.NOT_FOUND, "Row topilmadi!");
        seatTemplateChairRepository.deleteAll(chairList);
        return ApiResult.successResponse("Row o'chirilidi!");
    }

    @Override
    public ApiResult<?> deleteChair(UUID id) {
        try {
            seatTemplateChairRepository.deleteById(id);
            return ApiResult.successResponse("Chair o'chirildi!");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RestException(HttpStatus.NOT_FOUND, "Chair topilmadi!");
        }
    }
}
