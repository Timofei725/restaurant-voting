INSERT INTO USERS (NAME,EMAIL, PASSWORD)
VALUES ( 'User_First','user@gmail.com',  'password'),
       ( 'Admin_First','admin@javaops.ru', 'admin'),
( 'User_Second', 'user2@gmail.com',  'password2');

INSERT INTO USER_ROLES (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3);




INSERT INTO RESTAURANTS (NAME)
VALUES ('First_Restaurant'),
       ('Second_Restaurant');

INSERT INTO DISHES (NAME,PRICE, restaurant_id)
VALUES ('Fried_Fish',150, 1),
       ('Fried_Potatoes',120, 1),
       ('Salad',100, 2),
       ('Ice cream',90, 2);

INSERT INTO VOTES (user_id, restaurant_id)
VALUES (1,1),
       (2, 2);
