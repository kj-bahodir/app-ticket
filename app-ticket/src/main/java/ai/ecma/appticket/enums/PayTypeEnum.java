package ai.ecma.appticket.enums;

public enum PayTypeEnum {
    CASH("Naqd"),
    TRANSFER("Pul o'tkazish"),
    CARD("Karta orqali");

    private final String  nameUz;

    PayTypeEnum(String nameUz) {
        this.nameUz = nameUz;
    }

    public String getNameUz() {
        return nameUz;
    }
}
