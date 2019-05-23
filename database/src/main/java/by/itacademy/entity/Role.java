package by.itacademy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    ADMIN("Администратор"),
    CUSTOMER("Клиент");

    private String name;

    public static Role getByName(String name) {
        return Role.valueOf(name);
    }
}
