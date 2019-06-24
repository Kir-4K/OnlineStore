package by.itacademy.kostusev.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {

    UNPROCESSED("Не обработан"),
    PROCESSED("Обработан"),
    SENT("Отправлен"),
    PAID("Оплачен");

    private String name;
}
