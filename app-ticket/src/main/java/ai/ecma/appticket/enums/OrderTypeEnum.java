package ai.ecma.appticket.enums;

public enum OrderTypeEnum {
    SOLD(""),
    BRON(""),
    PAY_AFTER(""),
    CLOSED_FOREVER("");

    private final String nameUz;

    OrderTypeEnum(String nameUz) {
        this.nameUz = nameUz;
    }

    public String getNameUz() {
        return nameUz;
    }
}
