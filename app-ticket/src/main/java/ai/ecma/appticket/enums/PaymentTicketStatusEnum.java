package ai.ecma.appticket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentTicketStatusEnum {
    SOLD("Sotib olish"),
    BRON("Band qilish"),
    PAY_AFTER_BRON("Brondan keyin to'lash"),
    PAY_BACK("Pul qaytarish");

    private String nameUz;
}
