package ai.ecma.appticket.enums;

public enum PermissionEnum {
    ADD_EVENT(""),
    EDIT_EVENT(""),
    DELETE_EVENT(""),
    ADD_EVENT_SESSION(""),
    EDIT_EVENT_SESSION(""),
    DELETE_EVENT_SESSION(""),

    ADD_ADDRESS("Address"),
    EDIT_ADDRESS(""),
    DELETE_ADDRESS(""),

    ADD_SPEAKER(""),
    EDIT_SPEAKER(""),
    DELETE_SPEAKER(""),

    ADD_ATTACHMENT(""),
    EDIT_ATTACHMENT(""),
    DELETE_ATTACHMENT(""),

    VIEW_SEAT_TEMPLATE(""),
    ADD_SEAT_TEMPLATE(""),
    EDIT_SEAT_TEMPLATE(""),
    DELETE_SEAT_TEMPLATE(""),

    VIEW_SEAT_TEMPLATE_CHAIR(""),
    ADD_SEAT_TEMPLATE_CHAIR(""),
    EDIT_SEAT_TEMPLATE_CHAIR(""),
    DELETE_SEAT_TEMPLATE_CHAIR(""),

    ADD_TICKET(""),
    EDIT_TICKET(""),
    DELETE_TICKET(""),

    ADD_BRON_TARIFF(""),
    EDIT_BRON_TARIFF(""),
    DELETE_BRON_TARIFF(""),

    ADD_PAY_BACK_TARIFF(""),
    EDIT_PAY_BACK_TARIFF(""),
    DELETE_PAY_BACK_TARIFF(""),

    ADD_STAFF(""),
    EDIT_STAFF(""),
    DELETE_STAFF(""),

    VIEW_USER(""),
    VIEW_STAFF(""),

    VIEW_ROLE(""),
    ADD_ROLE(""),
    EDIT_ROLE(""),
    DELETE_ROLE(""),

    PAYMENT("");

    private String nameUz;

    PermissionEnum(String nameUz) {
        this.nameUz = nameUz;
    }

    public String getNameUz() {
        return nameUz;
    }
}
