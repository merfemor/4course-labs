create table Cat
(
    id       NUMBER PRIMARY KEY,
    name     VARCHAR(128),
    birthday NUMBER
);

insert into Cat(id, name, birthday)
values (1, 'murzik', 10);
insert into Cat(id, name, birthday)
values (2, 'barsik', 11);
insert into Cat(id, name, birthday)
values (3, 'untitled', 1);


drop table DOG;

create table DOG(
    id1 NUMBER,
    id2 NUMBER,
    name VARCHAR(256),
    birth NUMBER,
    PRIMARY KEY (id1, id2)
);

insert into DOG values (1, 2, 'gg', 23);

select * from all_tables where OWNER = 'S225111';

grant ALL on DOG to s225102;
grant UPDATE on DOG to S225102;
grant ALTER on DOG to S225102;