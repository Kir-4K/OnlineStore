package by.itacademy.kostusev.dto.utilityDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutDto {

    private String firstName;
    private String lastName;
    private String phone;
    private String mail;
    private String city;
    private String street;
    private String house;
    private String apartment;
    private String payment;
}
