--https://www.dailycred.com/article/bcrypt-calculator
--https://bcrypt-generator.com
insert into usuarios(username,password,enabled)
--clave: daniel
values ('ucem','$2a$10$MQNISMDZrMQAl3/6PeviyONcSr/B37QXMZlgjL2B05Umn3BYbVCeO',true);

insert into usuarios(username,password,enabled)
--clave 1234
values ('jperez','$2a$10$qHt9M7kgjZMBp/MsSk5Q3OVaO5JCTmyrP7/6WDwcAaKeQ1IGrt1Rq',true);

INSERT INTO usuarios_roles(username,role)
VALUES ('ucem','ROLE_ADMIN');

INSERT INTO usuarios_roles(username,role)
VALUES ('jperez','ROLE_USER');