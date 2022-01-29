package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Muhammad Mo'minov
 * 08.10.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResDTO {
    private UUID id;
    private String name;
    private String description;
    private AddressResDto address;
    private EventTypeResDto eventType;
    private String schemaUrl;
    private UUID schemaId;
    private String bannerUrl;
    private UUID bannerId;
}
