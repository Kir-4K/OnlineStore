package by.itacademy.kostusev.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = "customers")
@Entity
@Table(name = "address", schema = "online_store", catalog = "online_store_repository")
public class Address implements BaseEntity<Long> {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city", nullable = true, length = 32)
    private String city;

    @Column(name = "street", nullable = true, length = 64)
    private String street;

    @Column(name = "house", nullable = true, length = 4)
    private String house;

    @Column(name = "apartment", nullable = true, length = 4)
    private String apartment;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private Set<Customer> customers;
}
