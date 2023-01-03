INSERT INTO USERS (NAME,EMAIL, PASSWORD)
VALUES ( 'User_First','user@gmail.com',  '{noop}password'),
       ( 'Admin','admin@gmail.com', '{noop}admin'),
( 'User_Second', 'user2@gmail.com',  '{noop}password2');

INSERT INTO USER_ROLES (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3);




INSERT INTO RESTAURANT (NAME)
VALUES ('First_Restaurant'),
       ('Second_Restaurant'),
       ('Third_Restaurant');

INSERT INTO DISH (NAME,PRICE, restaurant_id)
VALUES ('Fried_Fish',150, 1),
       ('Fried_Potatoes',120, 1),
       ('Salad',100, 2),
       ('Ice cream',90, 2);

INSERT INTO DISH (NAME,DATE_IN_MENU,PRICE, restaurant_id)
VALUES('Fried_Fishwqdqwd','2019-06-26', 12,3),
       ('Fried_Potatoesasdsda','2019-06-26',120, 3),
       ('Saladqwdqdw','2019-06-26',100, 3),
       ('Ice creamdqwdwq','2019-06-26',90, 3);


INSERT INTO VOTE (user_id, restaurant_id)
VALUES (1,1),
       (2, 2);
