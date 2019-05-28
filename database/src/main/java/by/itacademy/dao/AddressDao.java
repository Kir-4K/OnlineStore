package by.itacademy.dao;

import by.itacademy.entity.Address;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressDao {

    private static final AddressDao INSTANCE = new AddressDao();

    public Address findById(Session session, Long id) {
        return session.get(Address.class, id);
    }

    public static AddressDao getInstance() {
        return INSTANCE;
    }
}
