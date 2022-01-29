package ai.ecma.appticket.mapper;

import ai.ecma.appticket.entity.*;
import ai.ecma.appticket.enums.PermissionEnum;
import ai.ecma.appticket.payload.*;
import ai.ecma.appticket.payload.seatTemplate.RowDto;
import ai.ecma.appticket.payload.seatTemplate.SeatTemplateDto;
import ai.ecma.appticket.payload.seatTemplate.SectionDto;
import ai.ecma.appticket.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Muhammad Mo'minov
 * 07.10.2021
 */
@Component
@RequiredArgsConstructor

public class CustomMapper {
//    private final EntityMapper entityMapper;


    public static SpeakerResDto speakerToDto(Speaker speaker) {
        return new SpeakerResDto(
                speaker.getId(),
                speaker.getFullName(),
                speaker.getSpecializations().stream().map(CustomMapper::specializationToDto).collect(Collectors.toSet()),
                speaker.getDescription(),
                speaker.getPhoto()!=null?CommonUtils.buildPhotoUrl(speaker.getPhoto().getId()):null,
                speaker.getPhoto().getId()
        );
    }

    public static SpecializationDto specializationToDto(Specialization specialization) {
        return new SpecializationDto(specialization.getId(), specialization.getName());
    }

    public static AddressResDto addressToDto(Address address) {
        return new AddressResDto(
                address.getId(),
                address.getLat(),
                address.getLon(),
                address.getName(),
                address.getTarget(),
                address.getPhoto()!=null?CommonUtils.buildPhotoUrl(address.getPhoto().getId()):null,
                address.getPhoto().getId()

        );
    }

    public static EventTypeResDto eventTypeToResDto(EventType eventType) {
        return new EventTypeResDto(
                eventType.getId(),
                eventType.getName()
        );
    }

    public static SeatTemplateResDto seatTemplateToResDto(SeatTemplate seatTemplate) {
        return new SeatTemplateResDto(
                seatTemplate.getId(),
                seatTemplate.getName(),
                CommonUtils.buildPhotoUrl(seatTemplate.getSchema().getId()),
                seatTemplate.getSchema().getId()
        );
    }

    public static PayBackTariffDto payBackTariffToDto(PayBackTariff payBackTariff) {
        return new PayBackTariffDto(
                payBackTariff.getId(),
                payBackTariff.getReminingHour(),
                payBackTariff.getPercent(),
                payBackTariff.getEvent().getId()
        );
    }

    public static SeatTemplateDto seatTemplateToDto(SeatTemplate seatTemplate) {
        return new SeatTemplateDto(
                seatTemplate.getId(),
                CommonUtils.buildPhotoUrl(seatTemplate.getSchema().getId()),
                sectionToDto(seatTemplate.getSeatTemplateChairList())
        );
    }

    private static Set<SectionDto> sectionToDto(List<SeatTemplateChair> seatTemplateChairList) {
        Set<SectionDto> sectionDtoList = new HashSet<>();
        Set<String> allSection = seatTemplateChairList.stream().map(SeatTemplateChair::getSection).collect(Collectors.toSet());
        for (String oneSection : allSection) {
            sectionDtoList.add(
                    new SectionDto(
                            oneSection,
                            rowToDto(seatTemplateChairList, oneSection)
                    )
            );
        }
        return sectionDtoList;
    }

    private static Set<RowDto> rowToDto(List<SeatTemplateChair> seatTemplateChairList, String sectionName) {
        Set<RowDto> rowDtoSet = new HashSet<>();
        Set<String> allRows = seatTemplateChairList.stream().filter(o -> o.getSection().equals(sectionName))
                .map(SeatTemplateChair::getRow).collect(Collectors.toSet());
        for (String oneRow : allRows) {
            rowDtoSet.add(
                    new RowDto(
                            oneRow,
                            seatTemplateChairList.stream().filter(o -> o.getRow().equals(oneRow)&&o.getSection().equals(sectionName)).map(CustomMapper::chairToDTO).collect(Collectors.toSet())
                    )
            );
        }
        return rowDtoSet;
    }

    public static SeatTemplateChairResDto chairToDTO(SeatTemplateChair seatTemplateChair) {
        return new SeatTemplateChairResDto(
                seatTemplateChair.getId(),
                seatTemplateChair.getPrice(),
                seatTemplateChair.getStatus(),
                seatTemplateChair.getSection(),
                seatTemplateChair.getRow(),
                seatTemplateChair.getName()
        );
    }

    public static EventResDTO eventToDTO(Event event) {
        return new EventResDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getAddress()!=null?CustomMapper.addressToDto(event.getAddress()):null,
                event.getEventType()!=null?CustomMapper.eventTypeToResDto(event.getEventType()):null,
                event.getSchema()!=null?CommonUtils.buildPhotoUrl(event.getSchema().getId()):null,
                event.getSchema()!=null?event.getSchema().getId():null,
                event.getBanner()!=null?CommonUtils.buildPhotoUrl(event.getBanner().getId()):null,
                event.getBanner()!=null?event.getBanner().getId():null
        );
    }

    public static EventSessionResDto eventSessionToDto(EventSession eventSession){
        return new EventSessionResDto(
                eventSession.getId(),
                eventSession.getEvent()!=null?CustomMapper.eventToDTO(eventSession.getEvent()):null,
                eventSession.getStartTime(),
                eventSession.getEndTime()

        );
    }

    public static TicketResDto ticketResDto(Ticket ticket) {
        return new TicketResDto(
                ticket.getId(),
                ticket.getUser()!=null?ticket.getUser().getId():null,
                ticket.getEventSession().getId(),
                ticket.getSection(),
                ticket.getRow(),
                ticket.getName(),
                ticket.getStatus(),
                ticket.getPrice()
        );
    }
    public static OrderResDto orderToDto(Order order){
        return new OrderResDto(
                order.getId(),
                order.getOrderPrice(),
                order.getUser().getId(),
                order.getTickets(),
                order.isFinished(),
                order.getType()
        );
    }
    public static PayDto orderToPayDto(Order order){
        return new PayDto(
                order.getId(),
                order.getOrderPrice()
        );
    }

    public static EventSpeakerResDto eventSpeakertoDto(EventSpeaker eventSpeaker){
        return new EventSpeakerResDto(
               eventSpeaker.getEvent().getId(),
                eventSpeaker.getSpeaker().getId(),
                eventSpeaker.isMain()
        );
    }

    public static PayTypeResDto payTypetoDto(PayType payType){
        return new PayTypeResDto(
                payType.getId(),
                payType.getName(),
                payType.getPayTypeEnum()
        );
    }

    public static BronTariffResDto bronTariffToDto(BronTariff bronTariff) {
        return new BronTariffResDto(
                bronTariff.getId(),
                bronTariff.getLifeTime(),
                bronTariff.getPercent(),
                bronTariff.isDisable(),
                bronTariff.getFinishTime()
        );
    }

    public static RefundResDto payBackToRefundDto(List<UUID> paybackIdList, String cardNumber, List<UUID> ticketIdList, double price) {
        return new RefundResDto(
                paybackIdList,
                cardNumber,
                price,
                ticketIdList
        );
    }

    public static RoleResDto roleToDto(Role role) {
        return new RoleResDto(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getPermissions()
        );
    }

    public static Set<PermissionEnum> permissionToSet(PermissionEnum[] values) {
        return new HashSet<>(Arrays.asList(values));
    }
}
