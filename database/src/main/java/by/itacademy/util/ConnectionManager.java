package by.itacademy.util;

import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class ConnectionManager {

    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();

    public static Session getFactory() {
        return FACTORY.openSession();
    }
}
