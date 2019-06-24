package by.itacademy.kostusev.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private String lastName;
    private String firstName;
    private String phone;
    private String mail;
    private AddressDto address;
}
