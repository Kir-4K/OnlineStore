package by.itacademy.kostusev.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {

    CANCELLED("Отменен"),
    UNPROCESSED("Не обработан"),
    ASSEMBLY("Комплектуется"),
    SENT("Отправлен"),
    PAID("Оплачен");

    private String name;

    public String getStatusName(){
        return name;
    }

    public static Status getByName(String name) {
        return Status.valueOf(name);
    }
}
