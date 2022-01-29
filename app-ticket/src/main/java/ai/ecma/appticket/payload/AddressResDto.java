package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResDto {

    private UUID id;
    private double lat;
    private double lon;
    private String name;
    private String target;
    private String photoUrl;
    private UUID photoId;



}
