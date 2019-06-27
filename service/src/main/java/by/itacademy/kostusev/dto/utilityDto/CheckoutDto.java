package by.itacademy.kostusev.dto.utilityDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutDto {

    @NotBlank
    @Size(min = 2, max = 30)
    private String firstName;

    private String lastName;

    @NotBlank
    private String phone;

    @Email
    private String mail;

    private String city;

    private String street;

    private String house;

    private String apartment;

    private String payment;
}
