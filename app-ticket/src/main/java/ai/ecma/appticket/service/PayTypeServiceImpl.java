package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.PayType;
import ai.ecma.appticket.exception.RestException;
import ai.ecma.appticket.mapper.CustomMapper;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.PayTypeResDto;
import ai.ecma.appticket.repository.PayTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PayTypeServiceImpl implements PayTypeService{

    private final PayTypeRepository payTypeRepository;

    @Override
    public ApiResult<List<PayTypeResDto>> getAll() {
        List<PayType> payTypeList = payTypeRepository.findAll();
        List<PayTypeResDto> payTypeResDtoList =new ArrayList<>();
        for (PayType payType : payTypeList) {
            payTypeResDtoList.add(CustomMapper.payTypetoDto(payType));
        }
        return ApiResult.successResponse(payTypeResDtoList);
    }

    @Override
    public ApiResult<PayTypeResDto> get(UUID id) {
        PayType payType = payTypeRepository.findById(id)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "To'lov turi topilmadi"));
        return ApiResult.successResponse(CustomMapper.payTypetoDto(payType));
    }

    @Override
    public ApiResult<PayTypeResDto> create(PayTypeResDto payTypeResDto) {
        return null;
    }

    @Override
    public ApiResult<PayTypeResDto> edit(PayTypeResDto payTypeResDto, UUID id) {
        return null;
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        return null;
    }
}
