package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.Bron;
import ai.ecma.appticket.entity.BronTariff;
import ai.ecma.appticket.exception.RestException;
import ai.ecma.appticket.mapper.CustomMapper;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.BronTariffReqDto;
import ai.ecma.appticket.payload.BronTariffResDto;
import ai.ecma.appticket.repository.BronRepository;
import ai.ecma.appticket.repository.BronTariffRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class BronTariffServiceImpl implements BronTariffService {
    private final BronTariffRepository bronTariffRepository;
    private final BronRepository bronRepository;

    public BronTariffServiceImpl(BronTariffRepository bronTariffRepository, BronRepository bronRepository) {
        this.bronTariffRepository = bronTariffRepository;
        this.bronRepository = bronRepository;
    }

    @Override
    public ApiResult<BronTariffResDto> add(BronTariffReqDto bronTariffReqDto) {
        BronTariff bronTariff = new BronTariff(
                bronTariffReqDto.getLifeTime(),
                bronTariffReqDto.getPercent(),
                bronTariffReqDto.isDisable(),
                bronTariffReqDto.getFinishTime(),
                new Timestamp((long) (System.currentTimeMillis() + (bronTariffReqDto.getFinishTime() * 3600 * 1000)))
        );
        bronTariffRepository.save(bronTariff);
        return ApiResult.successResponse(CustomMapper.bronTariffToDto(bronTariff));
    }


    @Override
    public ApiResult<BronTariffResDto> getOne(UUID id) {
        BronTariff bronTariff = bronTariffRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Bron tariff topilmadi"));
        return ApiResult.successResponse(CustomMapper.bronTariffToDto(bronTariff));
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        List<Bron> bronList = bronRepository.findAllByBronTariff(id);
        if (!bronList.isEmpty())
            throw new RestException(HttpStatus.CONFLICT, "Bron tariffda bronlar mavjud", bronList);
        try {
            bronTariffRepository.deleteById(id);
            return ApiResult.successResponse("Bron tariff muvaffaqiyatli o'chirildi");
        } catch (Exception e) {
            return ApiResult.errorResponse("Bron Tariff topilmadi!");
        }
    }
}
