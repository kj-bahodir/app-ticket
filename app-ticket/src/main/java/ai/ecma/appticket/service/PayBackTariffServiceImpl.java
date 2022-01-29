package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.Event;
import ai.ecma.appticket.entity.PayBackTariff;
import ai.ecma.appticket.exception.RestException;
import ai.ecma.appticket.mapper.CustomMapper;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.PayBackTariffDto;
import ai.ecma.appticket.payload.PayBackTariffReqDto;
import ai.ecma.appticket.repository.EventRepository;
import ai.ecma.appticket.repository.PaybackTariffRepository;
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
public class PayBackTariffServiceImpl implements PayBackTariffServise {

    private final PaybackTariffRepository paybackTariffRepository;
    private final EventRepository eventRepository;


    @Override
    public ApiResult<CustomPage<PayBackTariffDto>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC,"percent"));
        Page<PayBackTariff> tariffPage = paybackTariffRepository.findAll(pageable);
        CustomPage<PayBackTariffDto> payBackTariffDtoCustomPage = payBackTariffToCustomPage(tariffPage);
        return ApiResult.successResponse(payBackTariffDtoCustomPage);
    }

    @Override
    public ApiResult<PayBackTariffDto> getById(UUID payBackTariffId) {
        PayBackTariff payBackTariff = paybackTariffRepository.findById(payBackTariffId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Payback tariff not found!"));
        return ApiResult.successResponse(CustomMapper.payBackTariffToDto(payBackTariff));
    }

    @Override
    public ApiResult<PayBackTariffDto> save(PayBackTariffReqDto payBackTariffReqDto) {
        PayBackTariff payBackTariff = new PayBackTariff(
                payBackTariffReqDto.getReminingHour(),
                payBackTariffReqDto.getPercent(),
                eventRepository.findById(payBackTariffReqDto.getEventId())
                        .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Event not found!"))
        );
        PayBackTariff savedPayBackTariff = paybackTariffRepository.save(payBackTariff);
        return ApiResult.successResponse(CustomMapper.payBackTariffToDto(savedPayBackTariff));
    }

    @Override
    public ApiResult<PayBackTariffDto> edit(PayBackTariffReqDto payBackTariffReqDto, UUID payBackTariffId) {
        PayBackTariff payBackTariff = paybackTariffRepository.findById(payBackTariffId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Payback tariff not found!"));

        Event event = eventRepository.findById(payBackTariffReqDto.getEventId())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Event not found!"));

        payBackTariff.setReminingHour(payBackTariffReqDto.getReminingHour());
        payBackTariff.setPercent(payBackTariffReqDto.getPercent());
        payBackTariff.setEvent(event);
        PayBackTariff savedPayBackTariff = paybackTariffRepository.save(payBackTariff);
        return ApiResult.successResponse(CustomMapper.payBackTariffToDto(savedPayBackTariff));
    }

    @Override
    public ApiResult<?> delete(UUID payBackTariffId) {
        try {
            paybackTariffRepository.deleteById(payBackTariffId);
            return ApiResult.successResponse("Payback tariff deleted!");
        } catch (Exception e) {
            throw new RestException(HttpStatus.NOT_FOUND, "Payback tariff not found!");
        }
    }

    public CustomPage<PayBackTariffDto> payBackTariffToCustomPage(Page<PayBackTariff> payBackTariffs){
        return new CustomPage<>(
                payBackTariffs.getContent().stream().map(CustomMapper::payBackTariffToDto).collect(Collectors.toList()),
                payBackTariffs.getNumberOfElements(),
                payBackTariffs.getNumber(),
                payBackTariffs.getTotalElements(),
                payBackTariffs.getTotalPages(),
                payBackTariffs.getSize()
        );
    }
}
