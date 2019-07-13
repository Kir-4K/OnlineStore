package by.itacademy.kostusev.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum Payment {

    CASH("Наличными"),
    CARD("Банковской картой");

    private String name;

    public static Payment getByName(String name) {
        return Payment.valueOf(name);
    }
}
