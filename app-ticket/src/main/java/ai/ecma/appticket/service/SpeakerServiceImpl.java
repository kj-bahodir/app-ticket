package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.Speaker;
import ai.ecma.appticket.exception.RestException;
import ai.ecma.appticket.mapper.CustomMapper;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.SpeakerResDto;
import ai.ecma.appticket.payload.SpeakerReqDto;
import ai.ecma.appticket.repository.AttachmentRepository;
import ai.ecma.appticket.repository.SpeakerRepository;
import ai.ecma.appticket.repository.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpeakerServiceImpl implements SpeakerService {

    private final SpeakerRepository speakerRepository;
    //    private final EntityMapper entityMapper;
    private final SpecializationRepository specializationRepository;
    private final AttachmentRepository attachmentRepository;

    @Override
    public ApiResult<CustomPage<SpeakerResDto>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "fullName", "created_at"));
        Page<Speaker> speakers = speakerRepository.findAll(pageable);
        CustomPage<SpeakerResDto> speakerDtoCustomPage = speakerToCustomPage(speakers);
        return ApiResult.successResponse(speakerDtoCustomPage);
    }

        @Override
        public ApiResult<SpeakerResDto> getOne(UUID id){
            Speaker speaker = speakerRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Speaker topilmadi"));
            return ApiResult.successResponse(CustomMapper.speakerToDto(speaker));
        }

        @Override
        public ApiResult<SpeakerResDto> add (SpeakerReqDto speakerDto){
            Speaker speaker = new Speaker();
            speaker.setFullName(speakerDto.getFullName());
            speaker.setDescription(speakerDto.getDescription());
            speaker.setSpecializations(new HashSet<>(specializationRepository.findAllById(speakerDto.getSpecializationsId())));
            speaker.setPhoto(attachmentRepository.findById(speakerDto.getPhotoId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Photo topilmadi")));
            speakerRepository.save(speaker);
            return ApiResult.successResponse(CustomMapper.speakerToDto(speaker));
        }

        @Override
        public ApiResult<SpeakerResDto> edit (SpeakerReqDto speakerDto, UUID id){
            Speaker speaker = speakerRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Speaker topilmadi"));
            speaker.setFullName(speakerDto.getFullName());
            speaker.setDescription(speakerDto.getDescription());
            speaker.setSpecializations(new HashSet<>(specializationRepository.findAllById(speakerDto.getSpecializationsId())));
            speaker.setPhoto(attachmentRepository.findById(speakerDto.getPhotoId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Photo topilmadi")));
            speakerRepository.save(speaker);
            return ApiResult.successResponse(CustomMapper.speakerToDto(speaker));
        }

        @Override
        public ApiResult<?> delete (UUID id){
            try {
                speakerRepository.deleteById(id);
                return ApiResult.successResponse("O'chirildi");
            } catch (Exception e) {
                throw new RestException(HttpStatus.NOT_FOUND, "Topilmadi");
            }
        }

        public CustomPage<SpeakerResDto> speakerToCustomPage (Page < Speaker > speakerPage) {
            return new CustomPage<SpeakerResDto>(
                    speakerPage.getContent().stream().map(CustomMapper::speakerToDto).collect(Collectors.toList()),
                    speakerPage.getNumberOfElements(),
                    speakerPage.getNumber(),
                    speakerPage.getTotalElements(),
                    speakerPage.getTotalPages(),
                    speakerPage.getSize()
            );
        }
    }
