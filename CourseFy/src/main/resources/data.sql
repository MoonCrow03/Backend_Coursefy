INSERT INTO role (name) VALUES ('ROLE_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_TEACHER');
INSERT INTO role (name) VALUES ('ROLE_STUDENT');
INSERT INTO role (name) VALUES ('ROLE_UNREGISTERED');

INSERT INTO user_security (email, username, password) VALUES ('julia@gmail.com', 'julia', '$2a$10$fVKfcc47q6lrNbeXangjYeY000dmjdjkdBxEOilqhapuTO5ZH0co2');
INSERT INTO user_security (email, username, password) VALUES ('ruben@gmail.com', 'ruben', '$2a$10$fVKfcc47q6lrNbeXangjYeY000dmjdjkdBxEOilqhapuTO5ZH0co2');
INSERT INTO user_security (email, username, password) VALUES ('josep@tecnocampus.cat', 'alfredo', '$2a$10$fVKfcc47q6lrNbeXangjYeY000dmjdjkdBxEOilqhapuTO5ZH0co2');
INSERT INTO user_security (email, username, password) VALUES ('jordi@tecnocampus.cat', 'josep', '$2a$10$fVKfcc47q6lrNbeXangjYeY000dmjdjkdBxEOilqhapuTO5ZH0co2');
INSERT INTO user_security (email, username, password) VALUES ('maria@tecnocampus.cat', 'maria', '$2a$10$fVKfcc47q6lrNbeXangjYeY000dmjdjkdBxEOilqhapuTO5ZH0co2');
INSERT INTO user_security (email, username, password) VALUES ('admin@tecnocampus.cat', 'admin', '$2a$10$fVKfcc47q6lrNbeXangjYeY000dmjdjkdBxEOilqhapuTO5ZH0co2');

INSERT INTO user_roles (USER_ID, ROLE_ID) VALUES (1, 3);
INSERT INTO user_roles (USER_ID, ROLE_ID) VALUES (2, 3);
INSERT INTO user_roles (USER_ID, ROLE_ID) VALUES (3, 2);
INSERT INTO user_roles (USER_ID, ROLE_ID) VALUES (4, 2);
INSERT INTO user_roles (USER_ID, ROLE_ID) VALUES (5, 4);
INSERT INTO user_roles (USER_ID, ROLE_ID) VALUES (6, 1);