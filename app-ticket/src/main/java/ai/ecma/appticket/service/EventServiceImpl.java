package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.*;
import ai.ecma.appticket.exception.RestException;
import ai.ecma.appticket.mapper.CustomMapper;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.EventReqDto;
import ai.ecma.appticket.payload.EventResDTO;
import ai.ecma.appticket.repository.*;
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
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventTypeRepository eventTypeRepository;
    private final AddressRepository addressRepository;
    private final AttachmentRepository attachmentRepository;
    private final BronTariffRepository bronTariffRepository;

    @Override
    public ApiResult<CustomPage<EventResDTO>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Event> eventPage = eventRepository.findAll(pageable);
        CustomPage<EventResDTO> eventResDTOCustomPage = eventToCustomPage(eventPage);
        return ApiResult.successResponse(eventResDTOCustomPage);
    }

    @Override
    public ApiResult<EventResDTO> getOne(UUID id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Tadbir topilmadi!"));
        return ApiResult.successResponse(CustomMapper.eventToDTO(event));
    }

    @Override
    public ApiResult<EventResDTO> add(EventReqDto eventReqDto) {
        Event event = eventAddOrEdit(eventReqDto, new Event());
        return ApiResult.successResponse(CustomMapper.eventToDTO(event));
    }

    @Override
    public ApiResult<EventResDTO> edit(EventReqDto eventReqDto, UUID id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Tadbir topilmadi!"));
        eventAddOrEdit(eventReqDto, event);
        return ApiResult.successResponse(CustomMapper.eventToDTO(event));
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        try {
            eventRepository.deleteById(id);
            return ApiResult.successResponse("Tadbir o'chirildi!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestException(HttpStatus.NOT_FOUND, "Tadbir topilmadi!");
        }
    }

    private CustomPage<EventResDTO> eventToCustomPage(Page<Event> eventPage) {
        return new CustomPage<>(
                eventPage.getContent().stream().map(CustomMapper::eventToDTO).collect(Collectors.toList()),
                eventPage.getNumberOfElements(),
                eventPage.getNumber(),
                eventPage.getTotalElements(),
                eventPage.getTotalPages(),
                eventPage.getSize()
        );
    }

    private Event eventAddOrEdit(EventReqDto eventReqDto, Event event) {
        EventType eventType = eventTypeRepository.findById(eventReqDto.getEventTypeId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Tadbir turi topilmadi!"));
        Address address = addressRepository.findById(eventReqDto.getAddressId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Manzil topilmadi!"));
        Attachment schema = attachmentRepository.findById(eventReqDto.getSchemaId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Tadbir zali chizmasi topilmadi!"));
        Attachment banner = attachmentRepository.findById(eventReqDto.getBannerId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Tadbir banneri topilmadi!"));
        BronTariff bronTariff = bronTariffRepository.findById(eventReqDto.getBronTariffId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Bron tariff topilmadi!"));
        event.setName(eventReqDto.getName());
        event.setDescription(eventReqDto.getDescription());
        event.setEventType(eventType);
        event.setAddress(address);
        event.setSchema(schema);
        event.setBanner(banner);
        event.setBronTariff(bronTariff);
        eventRepository.save(event);
        return event;
    }
}
