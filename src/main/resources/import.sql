insert into funcionarios(codigo,usuario,senha,nome) values (0,'admin','admin','Administrador');

--atendente
insert into funcionarios(codigo,usuario,senha,nome) values (-1,'atendente','teste123','Atendente Modelo');
insert into funcionarios_papeis(func,papel) values (-1,'ATENDENTE');

--tecnico
insert into funcionarios(codigo,usuario,senha,nome) values (-2,'tecnico','teste123','Tecnico Modelo');
insert into funcionarios_papeis(func,papel) values (-2,'TECNICO');
