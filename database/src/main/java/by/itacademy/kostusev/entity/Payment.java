package by.itacademy.kostusev.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum Payment {

    CASH("Наличными"),
    CARD("Банковской картой");

    private String name;
}
