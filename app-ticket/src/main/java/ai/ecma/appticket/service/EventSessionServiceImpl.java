package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.*;
import ai.ecma.appticket.exception.RestException;
import ai.ecma.appticket.mapper.CustomMapper;
import ai.ecma.appticket.payload.*;
import ai.ecma.appticket.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventSessionServiceImpl implements EventSessionService {

    private final EventSessionRepository eventSessionRepository;
    private final EventRepository eventRepository;
    private final SeatTemplateRepository seatTemplateRepository;
    private final TicketRepository ticketRepository;

    @Override
    public ApiResult<CustomPage<EventSessionResDto>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EventSession> sessionPage = eventSessionRepository.findAll(pageable);
        CustomPage<EventSessionResDto> customPage = eventSessionToCustomPage(sessionPage);
        return ApiResult.successResponse(customPage);
    }

    @Override
    public ApiResult<EventSessionResDto> getOne(UUID id) {
        EventSession session = eventSessionRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Tadbir sessiyasi topilmadi"));
        return ApiResult.successResponse(CustomMapper.eventSessionToDto(session));
    }

    @Override
    public ApiResult<List<EventSessionResDto>> getByEventId(UUID eventId) {
        List<EventSession> sessions = eventSessionRepository.findAllByEventId(eventId);
        List<EventSessionResDto> list = sessions.stream().map(CustomMapper::eventSessionToDto).collect(Collectors.toList());
        return ApiResult.successResponse(list);
    }

    @Override
    public ApiResult<EventSessionResDto> add(EventSessionReqDto eventSessionReqDto) {
        if (eventSessionReqDto.getStartTime().after(eventSessionReqDto.getEndTime()))
            throw new RestException(HttpStatus.BAD_REQUEST, "Vaqtni to'g'ri kiriting");
        Event event = eventRepository.findById(eventSessionReqDto.getEventId()).
                orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Tadbir topilmadi"));
        List<EventSession> conflictSession = eventSessionRepository.findAllTimeConflict(
                event.getAddress().getId(),
                eventSessionReqDto.getStartTime(),
                eventSessionReqDto.getEndTime()
        );
        if (!conflictSession.isEmpty())
            throw new RestException(
                    HttpStatus.CONFLICT,
                    "Bu vaqtda tadbir bor",
                    conflictSession.stream().map(CustomMapper::eventSessionToDto).collect(Collectors.toSet())
            );
        EventSession eventSession = new EventSession(
                event,
                eventSessionReqDto.getStartTime(),
                eventSessionReqDto.getEndTime()
        );
        eventSessionRepository.save(eventSession);

        SeatTemplate seatTemplate = seatTemplateRepository.findById(eventSessionReqDto.getSeatTemplateId()).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "template topilmadi"));
        List<Ticket> ticketList = seatTemplate.getSeatTemplateChairList().stream().map(
                seatTemplateChair -> new Ticket(
                        null,
                        eventSession,
                        seatTemplateChair.getSection(),
                        seatTemplateChair.getRow(),
                        seatTemplateChair.getName(),
                        seatTemplateChair.getStatus(),
                        seatTemplateChair.getPrice()
                )
        ).collect(Collectors.toList());
        ticketRepository.saveAll(ticketList);
        return ApiResult.successResponse(CustomMapper.eventSessionToDto(eventSession));
    }

    @Override
    public ApiResult<EventSessionResDto> edit(EventSessionReqDto eventSessionReqDto, UUID id) {
        if (eventSessionReqDto.getStartTime().after(eventSessionReqDto.getEndTime()))
            throw new RestException(HttpStatus.BAD_REQUEST, "Vaqtni to'g'ri kiriting");
        Event event = eventRepository.findById(eventSessionReqDto.getEventId()).
                orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Tadbir topilmadi"));
        EventSession eventSession = eventSessionRepository.findById(id)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Tadbir sessiyasi topilmadi"));
        List<EventSession> conflictIdNot = eventSessionRepository.findAllTimeConflictIdNot(
                eventSession.getEvent().getAddress().getId(),
                id,
                eventSessionReqDto.getStartTime(),
                eventSessionReqDto.getEndTime()
        );

        if (!conflictIdNot.isEmpty())
            throw new RestException(
                    HttpStatus.CONFLICT,
                    "Bu vaqtda tadbir bor",
                    conflictIdNot.stream().map(CustomMapper::eventSessionToDto).collect(Collectors.toSet())
            );
        eventSession.setEvent(event);
        eventSession.setStartTime(eventSessionReqDto.getStartTime());
        eventSession.setEndTime(eventSessionReqDto.getEndTime());
        eventSessionRepository.save(eventSession);
        return ApiResult.successResponse(CustomMapper.eventSessionToDto(eventSession));
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        try {
            eventSessionRepository.deleteById(id);
            return ApiResult.successResponse("Tadbir sessiyasi o'chirildi");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RestException(HttpStatus.NOT_FOUND, "Topilmadi");
        }
    }

    private CustomPage<EventSessionResDto> eventSessionToCustomPage(Page<EventSession> eventSessionPage) {
        return new CustomPage<>(
                eventSessionPage.getContent().stream().map(CustomMapper::eventSessionToDto).collect(Collectors.toList()),
                eventSessionPage.getNumberOfElements(),
                eventSessionPage.getNumber(),
                eventSessionPage.getTotalElements(),
                eventSessionPage.getTotalPages(),
                eventSessionPage.getSize()
        );
    }

}
