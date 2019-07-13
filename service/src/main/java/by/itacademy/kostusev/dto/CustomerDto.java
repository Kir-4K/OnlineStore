package by.itacademy.kostusev.dto;

import by.itacademy.kostusev.entity.Address;
import by.itacademy.kostusev.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private Long id;
    private String lastName;
    private String firstName;
    private String phone;
    private String mail;
    private User user;
    private Address address;
}
