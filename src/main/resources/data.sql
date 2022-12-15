INSERT INTO USERS (NAME,EMAIL, PASSWORD, has_vote)
VALUES ( 'User_First','user@gmail.com',  'password','true'),
       ( 'Admin_First','admin@javaops.ru', 'admin','true');

INSERT INTO USER_ROLES (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);



INSERT INTO RESTAURANTS (NAME)
VALUES ('First_Restaurant'),
       ('Second_Restaurant');

INSERT INTO DISHES (NAME,PRICE, restaurant_id)
VALUES ('Fried_Fish',150, 1),
       ('Fried_POTATE',120, 1),
       ('Salad',100, 2),
       ('Ice cream',90, 2);

INSERT INTO VOTES (user_id, restaurant_id)
VALUES (1,2),
       (2, 1);
