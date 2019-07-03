package by.itacademy.kostusev.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {

    CANCELLED("Отменен"),
    UNPROCESSED("Не обработан"),
    PROCESSED("Обработан"),
    ASSEMBLY("Комплектуется"),
    SENT("Отправлен"),
    PAID("Оплачен");

    private String name;

    public static Status getByName(String name) {
        return Status.valueOf(name);
    }
}
