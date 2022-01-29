package ai.ecma.appticket.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  //frontendga null lar ko`rinmasligi uchun
public class ApiResult<T> {

    private String message;
    private T data;
    private List<ErrorData> errors;
    private boolean success;


    public static <T> ApiResult<T> successResponse(T data) {
        return new ApiResult<>(null, data, null, true);
    }

    public static ApiResult successResponse(String message) {
        return new ApiResult<>(message, null, null, true);
    }

    public static ApiResult errorResponse(String message) {
        return new ApiResult<>(message, null, null, false);

    }

    public static ApiResult errorResponse(String message, Object data) {
        return new ApiResult<>(message, data, null, false);
    }

    public static ApiResult errorResponse(List<ErrorData> errors) {
        return new ApiResult<>(null, null, errors, false);
    }
}
