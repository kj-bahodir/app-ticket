package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.Specialization;
import ai.ecma.appticket.exception.RestException;
import ai.ecma.appticket.mapper.CustomMapper;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.SpecializationDto;
import ai.ecma.appticket.payload.SpecializationReqDto;
import ai.ecma.appticket.repository.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecializationServiceImpl implements SpecializationService{

    private final SpecializationRepository specializationRepository;

    @Override
    public ApiResult<CustomPage<SpecializationDto>> getAll(int page, int size) {
        Pageable pageable= PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        Page<Specialization> specializationPage=specializationRepository.findAll(pageable);
        CustomPage<SpecializationDto> specializationDtoCustomPage=specializationDtoCustomPage(specializationPage);
        return ApiResult.successResponse(specializationDtoCustomPage);
    }

    @Override
    public ApiResult<SpecializationDto> getOne(UUID id) {
        Specialization specialization = specializationRepository.findById(id)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Mutaxassis topilmadi"));
        return ApiResult.successResponse(CustomMapper.specializationToDto(specialization));
    }

    @Override
    public ApiResult<SpecializationDto> add(SpecializationReqDto specializationReqDto) {
        Specialization specialization=new Specialization(
                specializationReqDto.getName()
        );
        Specialization specialization1 = specializationRepository.save(specialization);
        return ApiResult.successResponse(CustomMapper.specializationToDto(specialization1));
    }

    @Override
    public ApiResult<SpecializationDto> edit(SpecializationReqDto specializationReqDto, UUID uuid) {
        Specialization specialization = specializationRepository.findById(uuid).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Mutaxassis topilmadi"));
        specialization.setName(specializationReqDto.getName());
        Specialization specialization1 = specializationRepository.save(specialization);
        return ApiResult.successResponse(CustomMapper.specializationToDto(specialization1));
    }

    @Override
    public ApiResult<?> delete(UUID uuid) {
        try {
            specializationRepository.deleteById(uuid);
            return ApiResult.successResponse("Mutaxassis o'chirildi");
        }catch (Exception e){
            throw new RestException(HttpStatus.NOT_FOUND, "Mutaxassis topilmadi");
        }
    }

    public CustomPage<SpecializationDto> specializationDtoCustomPage(Page<Specialization> specializationPage){
        return new CustomPage<>(
                specializationPage.getContent().stream().map(CustomMapper::specializationToDto).collect(Collectors.toList()),
                specializationPage.getTotalPages(),
                specializationPage.getNumber(),
                specializationPage.getTotalElements(),
                specializationPage.getSize(),
                specializationPage.getNumberOfElements()
        );
    }
}
