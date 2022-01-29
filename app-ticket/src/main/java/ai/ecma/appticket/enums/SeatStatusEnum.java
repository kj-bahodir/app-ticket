package ai.ecma.appticket.enums;

public enum SeatStatusEnum {
    VACANT ("Bo'sh"),
    SOLD ("Sotilgan"),
    BOOKED ("Band qilingan"),
    RESERVED ("Zaxiraga olingan"),
    VIP ("VIP"),
    PAYMENT_PROCESS ("To'lov jarayonida");

    private String nameUz;

    SeatStatusEnum(String nameUz) {
        this.nameUz = nameUz;
    }

    public String getNameUz() {
        return nameUz;
    }
}
