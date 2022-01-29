package ai.ecma.appticket.utils;

import java.util.UUID;

/**
 * @author Muhammad Mo'minov
 * 07.10.2021
 */
public class CommonUtils {
    public static String buildPhotoUrl(UUID id) {
        return RestConstant.DOMAIN+RestConstant.ATTACHMENT_CONTROLLER+"/download/"+id;
    }
}
