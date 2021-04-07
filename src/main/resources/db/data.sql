INSERT INTO categoria_cuarto (nombre,descripcion,precio)
    VALUES ('Individual', 'Ideal para quienes viajan solos.',50.0);

INSERT INTO categoria_cuarto (nombre,descripcion,precio) values('Sencillo','Individual con Vista al Mar',60.0);
INSERT INTO categoria_cuarto (nombre,descripcion,precio) values('Doble','Dos Camas',100.0);
INSERT INTO categoria_cuarto (nombre,descripcion,precio) values('Doble full','Dos Camas + Vista al Mar',150.0);

INSERT INTO cuarto (numero, descripcion,categoria)
    VALUES(1,'Vista a la piscina',1);
INSERT INTO cuarto (numero, descripcion,categoria)
  VALUES(2,'Remodelado recientemente',1);

INSERT INTO cuarto (numero, descripcion,categoria)  VALUES(3,'Vista a la Calle',3);

INSERT INTO huesped (nombre,email,telefono) values('Daniel','daniel@gmail.com','22985600');
INSERT INTO huesped (nombre,email,telefono) values('Ernesto','ernesto@gmail.com','22985600');
INSERT INTO huesped (nombre,email,telefono) values('Miguel','miguel@gmail.com','22985600');
INSERT INTO huesped (nombre,email,telefono) values('Angel','angel@gmail.com','22985600');
INSERT INTO huesped (nombre,email,telefono) values('Sofía Rebeca','srbb@gmail.com','22985600');

/*
CREATE TABLE reservacion(
  id IDENTITY PRIMARY KEY,
  desde DATE NOT NULL,
  hasta DATE NOT NULL,
  cuarto INT NOT NULL,
  huesped INT NOT NULL
);
*/
Insert into reservacion (desde,hasta,cuarto,huesped) values (parsedatetime('2017-01-01','yyyy-MM-dd'),parsedatetime('2017-01-02','yyyy-MM-dd'),1,1);
Insert into reservacion (desde,hasta,cuarto,huesped) values (parsedatetime('2017-01-01 08:00:00','yyyy-MM-dd hh:mm:ss'),parsedatetime('2017-01-03 07:59:00','yyyy-MM-dd hh:mm:ss'),3,5);