package by.itacademy.util;

import by.itacademy.entity.Address;
import by.itacademy.entity.Category;
import by.itacademy.entity.Customer;
import by.itacademy.entity.News;
import by.itacademy.entity.Order;
import by.itacademy.entity.Payment;
import by.itacademy.entity.Product;
import by.itacademy.entity.ProductOrder;
import by.itacademy.entity.ProductOrderPK;
import by.itacademy.entity.Role;
import by.itacademy.entity.Status;
import by.itacademy.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestDataImporter {

    private static TestDataImporter INSTANCE = new TestDataImporter();

    public void importTestData(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Category potion = saveCategory(session, "Зелья");
            Category ingredient = saveCategory(session, "Ингредиенты");

            Product holyWater = saveProduct(session, "Святая вода", potion, 12.5, 9, 4.0,
                    "Может, убить оборотня или вампира и не сможет, но Ваш организм точно прочистит.");
            Product jollier = saveProduct(session, "Весельчак", potion, 15.2, 3, 5.0,
                    "Прекрасное зелье, которое скрасит не один осенний вечерок.");
            Product monsterBlood = saveProduct(session, "Кровь утопца", ingredient, 5.0, 21, 4.5,
                    "Ингредиент для многих чернокнижных зелий.");

            User admin = saveUser(session, "Admin", "admin", Role.ADMIN);
            User ivan = saveUser(session, "Ivan", "ivan123", Role.CUSTOMER);
            User max = saveUser(session, "Max", "max123", Role.CUSTOMER);

            Address firstAddress = saveAddress(session, "Минск", "Мира", "53", "61");
            Address secondAddress = saveAddress(session, "Гомель", "Независимости", "22", "14");

            Customer customerIvan = saveCustomer(session, "Иван", "Иванов", "Иванович",
                    "80(44)125-44-61", "ivan@mail.com", firstAddress, ivan);
            Customer customerMax = saveCustomer(session, "Максим", "Максимов", "Максимович",
                    "80(44)256-22-16", "max@mail.com", secondAddress, max);

            Order firstOrder = saveOrder(session, customerIvan, Payment.CASH, LocalDateTime.now(), Status.UNPROCESSED);
            Order secondOrder = saveOrder(session, customerMax, Payment.CARD, LocalDateTime.now(), Status.PROCESSED);

            saveProductOrder(session, holyWater, firstOrder, 2);
            saveProductOrder(session, jollier, firstOrder, 1);
            saveProductOrder(session, monsterBlood, secondOrder, 4);

            News firstNews = saveNews(session, admin, LocalDateTime.now(), "Мы открылись!",
                    "Мы рады сообщить вам, что открылся первый в Беларуси онлайн-магаиз волшебных зелий!");
            News secondNews = saveNews(session, admin, LocalDateTime.now(), "Новинка! Зелье безумия!",
                    "Внимание! В продаже появилось новое Зелье безумия! Новое зелье станет отличным добавлением для любой вечеринки!");
        }
    }

    private Category saveCategory(Session session, String name) {
        Category category = Category.builder()
                .name(name)
                .build();
        session.save(category);
        return category;
    }

    private User saveUser(Session session, String login, String password, Role role) {
        User user = User.builder()
                .login(login)
                .password(password)
                .role(role)
                .build();
        session.save(user);
        return user;
    }

    private Address saveAddress(Session session, String city, String street, String house, String apartment) {
        Address address = Address.builder()
                .city(city)
                .street(street)
                .house(house)
                .apartment(apartment)
                .build();
        session.save(address);
        return address;
    }

    private Customer saveCustomer(Session session, String firstName, String lastName, String middleName, String phone,
                                  String mail, Address address, User user) {
        Customer customer = Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .middleName(middleName)
                .phone(phone)
                .mail(mail)
                .address(address)
                .user(user)
                .build();
        session.save(customer);
        return customer;
    }

    private Order saveOrder(Session session, Customer customer, Payment payment, LocalDateTime date, Status status) {
        Order order = Order.builder()
                .customer(customer)
                .payment(payment)
                .date(date)
                .status(status)
                .build();
        session.save(order);
        return order;
    }

    private Product saveProduct(Session session, String name, Category category, Double price,
                                Integer number, Double rating, String description) {
        Product product = Product.builder()
                .name(name)
                .category(category)
                .price(price)
                .number(number)
                .rating(rating)
                .description(description)
                .build();
        session.save(product);
        return product;
    }

    private void saveProductOrder(Session session, Product product, Order order, Integer quantity) {
        ProductOrder productOrder = ProductOrder.builder()
                .id(ProductOrderPK.builder()
                        .product(product)
                        .order(order)
                        .build())
                .quantity(quantity)
                .build();
        session.save(productOrder);
    }

    private News saveNews(Session session, User user, LocalDateTime date, String title, String text) {
        News news = News.builder()
                .user(user)
                .date(date)
                .title(title)
                .text(text)
                .build();
        session.save(news);
        return news;
    }

    public static TestDataImporter getInstance() {
        return INSTANCE;
    }
}
