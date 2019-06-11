package by.itacademy.kostusev.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Payment {

    CASH("Наличными"),
    CARD("Банковской картой");

    private String name;
}
