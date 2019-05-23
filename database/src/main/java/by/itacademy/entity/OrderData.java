package by.itacademy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@DiscriminatorValue("NOT_REG")
public class OrderData extends Customer {

    @Column(name = "first_name", nullable = false, length = 32)
    private String firstName;

    @Column(name = "phone", nullable = false, length = 16, unique = true)
    private String phone;

    @Builder
    public OrderData(Long id, String firstName, String lastName, String middleName, String mail, String phone, User user, Address address, Set<Order> orders) {
        super(id, lastName, middleName, mail, user, address, orders);
        this.firstName = firstName;
        this.phone = phone;
    }
}
