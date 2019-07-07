/*DML*/
-- Блок Товаров
INSERT INTO online_store.category(id, name)
VALUES (1, 'Зелья'),
       (2, 'Ингредиенты');

INSERT INTO online_store.product(id, name, price, rating, description, number, category_id)
VALUES (1, 'Ласточка', 24.50, 4.5, 'Великолепное зелье от всех возможных болезней!', 10,
        (SELECT id FROM online_store.category WHERE name = 'Зелья')),
       (2, 'Весельчак', 27.25, 4, 'Прекрасное зелье, которое скрасит не один осенний вечерок.', 20,
        (SELECT id FROM online_store.category WHERE name = 'Зелья')),
       (3, 'Гром', 12.50, 4.0, 'Зевс, Перун и Тор рекомендуют.', 10,
        (SELECT id FROM online_store.category WHERE name = 'Зелья')),
       (4, 'Иволга', 21.50, 3,
        'Если Вы всегда мечтали разговаривать с животными и птицами, то вы наконец-таки сможете сделать это', 20,
        (SELECT id FROM online_store.category WHERE name = 'Зелья')),
       (5, 'Зелье безумия', 29.50, 5,
        'Вас бросила девушка? У Вас проблемы в семье и на работе? У нас есть средство от всех ваших бед!', 20,
        (SELECT id FROM online_store.category WHERE name = 'Зелья')),
       (6, 'Святая вода', 16.50, 2.5, 'Может, убить оборотня или вампира и не сможет, но Ваш организм точно прочистит.',
        20,
        (SELECT id FROM online_store.category WHERE name = 'Зелья')),
       (7, 'Пурга', 17.50, 3.5, 'И каждый день Вам покажется праздником! Толко не пейте его на морозе!', 10,
        (SELECT id FROM online_store.category WHERE name = 'Зелья')),
       (8, 'Дикий мустанг', 22.50, 4.0,
        'Хотите стать сильным и необузданным, бежать навстречу ветру и диким прериям? Выпейте, и ваша жизнь не будет как прежде!',
        10,
        (SELECT id FROM online_store.category WHERE name = 'Зелья')),
       (9, 'Шок-Жокей', 24.70, 5.0, 'На вид может и не очень, но это сделает из вас настоящего Электро!..5 разряда.',
        10,
        (SELECT id FROM online_store.category WHERE name = 'Зелья')),
       (10, 'Поцелуй Дьявола', 17.70, 4.0, 'Просто огонь! Вы начнете дышать огнем как настоящий дракон.', 10,
        (SELECT id FROM online_store.category WHERE name = 'Зелья')),
       (11, 'Кровь программиста', 7.0, 1, 'Замедляет время до дедлайна. Но только для вас.', 20,
        (SELECT id FROM online_store.category WHERE name = 'Ингредиенты')),
       (12, 'Кровь утопца', 6.25, 1.5, 'Ингредиент для многих чернокнижных зелий.', 20,
        (SELECT id FROM online_store.category WHERE name = 'Ингредиенты'));

-- Блок Пользоваталей
INSERT INTO online_store.user(id, username, password, role)
VALUES (1, 'Admin', '$2a$14$Rna2ibeamuqE0ahnZBdRIO3g7.G1VsdrJ3UhhBzof/rvkv3vuQKne', 'ADMIN'),
       (2, 'Max', '$2a$14$x2YuZfrNzae0VdoiafpTXeGndq1ffFEpOYI9hoTNqCPlts30Hlq1C', 'CUSTOMER'),
       (3, 'Ivan', '$2a$14$zkNN2oHk6ZvgNIxvuaxCKOc1YbUwPLABRicQA5GS.yqd6Zj3t8zXW', 'CUSTOMER');

INSERT INTO online_store.address(id, city, street, house, apartment)
VALUES (1, 'Минск', 'Мира', '1', '11'),
       (2, 'Минск', 'Пушкина', '2', '10');

INSERT INTO online_store.customer(id, last_name, first_name, mail, phone, user_id, address_id)
VALUES (1, 'Максимов', 'Максим', 'max@mail.ru', '80291112221',
        (SELECT id FROM online_store.user WHERE username = 'Max'),
        (SELECT id FROM online_store.address WHERE id = 1)),
       (2, 'Иванов', 'Иван', 'iavan@mail.ru', '80296122222',
        (SELECT id FROM online_store.user WHERE username = 'Ivan'),
        (SELECT id FROM online_store.address WHERE id = 2));

INSERT INTO online_store.customer(id, first_name, phone)
VALUES (3, 'Взяткер', '80442215568');

-- Блок Новостей
INSERT INTO online_store.news(id, date, title, text, user_id)
VALUES (1, date_trunc('minute', CURRENT_TIMESTAMP(0)), 'Мы открылись!',
        'Мы рады сообщить вам, что открылся первый в Беларуси онлайн-магаиз волшебных зелий!',
        (SELECT id FROM online_store.user WHERE role = 'ADMIN')),
       (2, date_trunc('minute', CURRENT_TIMESTAMP(0)), 'Новинка! Зелье безумия!',
        'Внимание! В продаже появилось новое Зелье безумия! Новое зелье станет отличным добавлением для любой вечеринки!',
        (SELECT id FROM online_store.user WHERE role = 'ADMIN'));

-- Блок Заказов
-- 1-й Заказ
INSERT INTO online_store.online_order(id, customer_id, payment, date, status)
VALUES (1, (SELECT id FROM online_store.customer WHERE phone = '80291112221'), 'CASH',
        date_trunc('minute', CURRENT_TIMESTAMP(0)), 'UNPROCESSED');

INSERT INTO online_store.product_order(online_order_id, product_id, quantity)
VALUES ((SELECT id FROM online_store.online_order WHERE id = 1),
        (SELECT id FROM online_store.product WHERE name = 'Кровь утопца'), 4);

INSERT INTO online_store.product_order(online_order_id, product_id, quantity)
VALUES ((SELECT id FROM online_store.online_order WHERE id = 1),
        (SELECT id FROM online_store.product WHERE name = 'Зелье безумия'), 1);

-- 2-й Заказ
INSERT INTO online_store.online_order(id, customer_id, payment, date, status)
VALUES (2, (SELECT id FROM online_store.customer WHERE phone = '80296122222'), 'CASH',
        date_trunc('minute', CURRENT_TIMESTAMP(0)), 'UNPROCESSED');

INSERT INTO online_store.product_order(online_order_id, product_id, quantity)
VALUES ((SELECT id FROM online_store.online_order WHERE id = 2),
        (SELECT id FROM online_store.product WHERE name = 'Кровь утопца'), 1),
       ((SELECT id FROM online_store.online_order WHERE id = 2),
        (SELECT id FROM online_store.product WHERE name = 'Зелье безумия'), 2);

-- 3-й Заказ
INSERT INTO online_store.online_order(id, customer_id, payment, date, status)
VALUES (3, (SELECT id FROM online_store.customer WHERE phone = '80296122222'), 'CARD',
        date_trunc('minute', CURRENT_TIMESTAMP(0)), 'ASSEMBLY');

INSERT INTO online_store.product_order(online_order_id, product_id, quantity)
VALUES ((SELECT id FROM online_store.online_order WHERE id = 3),
        (SELECT id FROM online_store.product WHERE name = 'Святая вода'), 1);
