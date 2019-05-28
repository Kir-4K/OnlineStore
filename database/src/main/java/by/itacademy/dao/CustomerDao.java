package by.itacademy.dao;

import by.itacademy.entity.Customer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerDao {

    private static final CustomerDao INSTANCE = new CustomerDao();

    public Customer findById(Session session, Long id) {
        return session.get(Customer.class, id);
    }

    public List<Customer> findAll(Session session) {
        return session.
                createQuery("SELECT c FROM Customer c", Customer.class)
                .list();
    }

    public static CustomerDao getInstance() {
        return INSTANCE;
    }
}
