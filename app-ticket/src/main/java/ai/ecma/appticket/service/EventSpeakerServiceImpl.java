package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.Event;
import ai.ecma.appticket.entity.EventSpeaker;
import ai.ecma.appticket.entity.Speaker;
import ai.ecma.appticket.exception.RestException;
import ai.ecma.appticket.mapper.CustomMapper;
import ai.ecma.appticket.payload.*;
import ai.ecma.appticket.repository.EventRepository;
import ai.ecma.appticket.repository.EventSpeakerRepository;
import ai.ecma.appticket.repository.SpeakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventSpeakerServiceImpl implements EventSpeakerService{

    private final EventSpeakerRepository eventSpeakerRepository;
    private final EventRepository eventRepository;
    private final SpeakerRepository speakerRepository;

    @Override
    public ApiResult<List<EventSpeakerResDto>> getAll() {
        List<EventSpeaker> eventSpeakerList = eventSpeakerRepository.findAll();
        List<EventSpeakerResDto> eventSpeakerResDtoList=new ArrayList<>();
        for (EventSpeaker eventSpeaker : eventSpeakerList) {
            eventSpeakerResDtoList.add(CustomMapper.eventSpeakertoDto(eventSpeaker));
        }
        return ApiResult.successResponse(eventSpeakerResDtoList);
    }

    @Override
    public ApiResult<EventSpeakerResDto> getOne(UUID id) {
        EventSpeaker eventSpeaker = eventSpeakerRepository.findById(id)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Speaker topilmadi"));
        return ApiResult.successResponse(CustomMapper.eventSpeakertoDto(eventSpeaker));
    }

    @Override
    public ApiResult<EventSpeakerResDto> add(EventSpeakerReqDto eventSpeakerReqDto) {
        EventSpeaker eventSpeaker=new EventSpeaker(
                eventRepository.findById(eventSpeakerReqDto.getEventId())
                        .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Tadbir topilmadi")),
                speakerRepository.findById(eventSpeakerReqDto.getSpeakerId())
                                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Speaker topilmadi")),
                eventSpeakerReqDto.isMain()
        );
        eventSpeakerRepository.save(eventSpeaker);
        return ApiResult.successResponse(CustomMapper.eventSpeakertoDto(eventSpeaker));
    }

    @Override
    public ApiResult<EventSpeakerResDto> edit(EventSpeakerReqDto eventSpeakerReqDto, UUID id) {
        EventSpeaker eventSpeaker = eventSpeakerRepository.findById(id)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Tadbir Speakeri topilmadi"));
        Event event = eventRepository.findById(eventSpeakerReqDto.getEventId())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Tadbir topilmadi"));
        Speaker speaker = speakerRepository.findById(eventSpeakerReqDto.getSpeakerId())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Speaker topilmadi"));
        eventSpeaker.setEvent(event);
        eventSpeaker.setSpeaker(speaker);
        eventSpeaker.setMain(eventSpeakerReqDto.isMain());
        eventSpeakerRepository.save(eventSpeaker);
        return ApiResult.successResponse(CustomMapper.eventSpeakertoDto(eventSpeaker));
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        try {
            eventSpeakerRepository.deleteById(id);
            return ApiResult.successResponse("Tadbir Speakeri o'chirildi");
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RestException(HttpStatus.NOT_FOUND, "Tadbir Speakeri topilmadi");
        }
    }
}
