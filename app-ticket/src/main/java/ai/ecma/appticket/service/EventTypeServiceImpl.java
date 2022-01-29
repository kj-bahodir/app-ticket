package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.EventType;
import ai.ecma.appticket.exception.RestException;
import ai.ecma.appticket.mapper.CustomMapper;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.EventTypeResDto;
import ai.ecma.appticket.payload.EventTypeReqDto;
import ai.ecma.appticket.repository.EventTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EventTypeServiceImpl implements EventTypeService{

    private final EventTypeRepository eventTypeRepository;

    public EventTypeServiceImpl(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;
    }


    @Override
    public ApiResult<List<EventTypeResDto>> getAll() {
        List<EventType> eventTypes = eventTypeRepository.findAll();
        List<EventTypeResDto> eventTypeDtoList = new ArrayList<>();
        for (EventType eventType : eventTypes) {
            eventTypeDtoList.add(CustomMapper.eventTypeToResDto(eventType));
        }
        return ApiResult.successResponse(eventTypeDtoList);
    }

    @Override
    public ApiResult<EventTypeResDto> get(UUID id) {
        EventType eventType = eventTypeRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Event Type topilmadi"));

        return ApiResult.successResponse(CustomMapper.eventTypeToResDto(eventType));
    }

    @Override
    public ApiResult<EventTypeResDto> add(EventTypeReqDto eventTypeReqDto) {
        EventType eventType = new EventType(eventTypeReqDto.getName());
        eventTypeRepository.save(eventType);
        return ApiResult.successResponse(CustomMapper.eventTypeToResDto(eventType));
    }

    @Override
    public ApiResult<EventTypeResDto> edit(EventTypeReqDto eventTypeReqDto, UUID id) {
        EventType eventType = eventTypeRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Event Type topilmadi"));
        eventType.setName(eventTypeReqDto.getName());
        eventTypeRepository.save(eventType);
        return ApiResult.successResponse(CustomMapper.eventTypeToResDto(eventType));
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        try {
            eventTypeRepository.deleteById(id);
            return ApiResult.successResponse("event type o'chirildi");
        }catch (Exception e){
            throw new RestException(HttpStatus.NOT_FOUND, "Topilmadi");
        }
    }
}
