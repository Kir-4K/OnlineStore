/*DML*/
-- Блок Товаров
INSERT INTO online_store.category(name)
VALUES ('Зелья'),
       ('Ингредиенты');

INSERT INTO online_store.product(name, price, description, number, category_id)
VALUES ('Ласточка', 24.50, 'Великолепное зелье от всех возможных болезней!', 10,
        (SELECT id FROM online_store.category WHERE name = 'Зелья')),
       ('Весельчак', 27.25, 'Прекрасное зелье, которое скрасит не один осенний вечерок.', 20,
        (SELECT id FROM online_store.category WHERE name = 'Зелья')),
       ('Иволга', 21.50,
        'Если Вы всегда мечтали разговаривать с животными и птицами, то вы наконец-таки сможете сделать это', 20,
        (SELECT id FROM online_store.category WHERE name = 'Зелья')),
       ('Зелье безумия', 29.50,
        'Вас бросила девушка? У Вас проблемы в семье и на работе? У нас есть средство от всех ваших бед!', 20,
        (SELECT id FROM online_store.category WHERE name = 'Зелья')),
       ('Святая вода', 16.50, 'Может, убить оборотня или вампира и не сможет, но Ваш организм точно прочистит.', 20,
        (SELECT id FROM online_store.category WHERE name = 'Зелья')),
       ('Кровь программиста', 7.0, 'Замедляет время до дедлайна. Но только для вас.', 20,
        (SELECT id FROM online_store.category WHERE name = 'Ингредиенты')),
       ('Кровь утопца', 6.25, 'Ингредиент для многих чернокнижных зелий.', 20,
        (SELECT id FROM online_store.category WHERE name = 'Ингредиенты'));

-- Блок Пользоваталей
INSERT INTO online_store.user(login, password, role)
VALUES ('admin', 'admin', 'ADMIN'),
       ('Karil', 'qwerty', 'CUSTOMER'),
       ('Ivan', 'qwerty', 'CUSTOMER');

INSERT INTO online_store.address(city, street, house, apartment)
VALUES ('Минск', 'Мира', '1', '11'),
       ('Минск', 'Пушкина', '2', '10');

INSERT INTO online_store.customer(customer_type, last_name, first_name, middle_name, mail, phone, user_id, address_id)
VALUES ('REG', 'Максим', 'Максимов', 'Максимвич', 'max@mail.ru', '80291112221',
        (SELECT id FROM online_store.user WHERE login = 'Karil'),
        (SELECT id FROM online_store.address WHERE id = 1)),
       ('REG', 'Иван', 'Иванов', 'Иванович', 'iavan@mail.ru', '80296122222',
        (SELECT id FROM online_store.user WHERE login = 'Ivan'),
        (SELECT id FROM online_store.address WHERE id = 2));

INSERT INTO online_store.customer(customer_type, first_name, phone)
VALUES ('NOT_REG', 'Маким', '80442215568');

-- Блок Новостей
INSERT INTO online_store.news(date, title, text, user_id)
VALUES (date_trunc('minute', CURRENT_TIMESTAMP(0) AT TIME ZONE 'Europe/Minsk'), 'Мы открылись!',
        'Мы рады сообщить вам, что открылся первый в Беларуси онлайн-магаиз волшебных зелий!',
        (SELECT id FROM online_store.user WHERE role = 'ADMIN')),
       (date_trunc('minute', CURRENT_TIMESTAMP(0) AT TIME ZONE 'Europe/Minsk'), 'Новинка! Зелье безумия!',
        'Внимание! В продаже появилось новое Зелье безумия! Новое зелье станет отличным добавлением для любой вечеринки!',
        (SELECT id FROM online_store.user WHERE role = 'ADMIN'));

-- Блок Заказов
-- 1-й Заказ
INSERT INTO online_store.online_order(customer_id, payment, date, status)
VALUES ((SELECT id FROM online_store.customer WHERE phone = '80291112221'), 'CASH',
        CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Minsk', 'UNPROCESSED');

INSERT INTO online_store.product_order(order_id, product_id, quantity)
VALUES ((SELECT id FROM online_store.online_order WHERE id = '1'),
        (SELECT id FROM online_store.product WHERE name = 'Кровь утопца'), 4);

INSERT INTO online_store.product_order(order_id, product_id, quantity)
VALUES ((SELECT id FROM online_store.online_order WHERE id = '1'),
        (SELECT id FROM online_store.product WHERE name = 'Зелье безумия'), 1);

-- 2-й Заказ
INSERT INTO online_store.online_order(customer_id, payment, date, status)
VALUES ((SELECT id FROM online_store.customer WHERE phone = '80296122222'), 'CASH',
        CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Minsk', 'UNPROCESSED');

INSERT INTO online_store.product_order(order_id, product_id, quantity)
VALUES ((SELECT id FROM online_store.online_order WHERE id = '2'),
        (SELECT id FROM online_store.product WHERE name = 'Кровь утопца'), 1),
       ((SELECT id FROM online_store.online_order WHERE id = '2'),
        (SELECT id FROM online_store.product WHERE name = 'Зелье безумия'), 2);

-- Блок Селекты
/*SELECT c.*
FROM online_store.user u
         JOIN online_store.customer c ON u.id = c.user_id
WHERE u.login = 'Ivan';

SELECT po.*
FROM online_store.online_order o
         JOIN online_store.product_order po ON o.id = po.order_id
WHERE o.id = 1;

SELECT ad.*
FROM online_store.address ad
         JOIN online_store.customer cu ON ad.id = cu.id
         JOIN online_store.user us ON cu.user_id = us.id
WHERE us.login = 'Ivan';

SELECT *
FROM online_store.product_order po
         LEFT JOIN online_store.product p ON po.product_id = p.id
         LEFT JOIN online_store.online_order o ON po.order_id = o.id
         LEFT JOIN online_store.customer c ON o.customer_id = c.id
         LEFT JOIN online_store.user u ON c.user_id = u.id
         LEFT JOIN online_store.address a ON c.address_id = a.id;

SELECT *
FROM online_store.online_order o
         LEFT JOIN online_store.product_order po ON o.id = po.order_id
         LEFT JOIN online_store.product p ON po.product_id = p.id
         LEFT JOIN online_store.customer c ON o.customer_id = c.id
         LEFT JOIN online_store.address a ON c.address_id = a.id
         LEFT JOIN online_store.user u ON c.user_id = u.id;

SELECT po.order_id, c.name, p.name
FROM online_store.product_order po
         LEFT JOIN online_store.product p ON po.product_id = p.id
         LEFT JOIN online_store.category c ON p.category_id = c.id
WHERE order_id = 1;*/