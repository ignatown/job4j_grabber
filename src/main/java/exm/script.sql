CREATE TABLE company
(
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person
(
    id integer NOT NULL,
    name character varying,
    company_id integer references company(id),
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

insert into company (id, name) values (1, 'k1');
insert into company (id, name) values (2, 'k2');
insert into company (id, name) values (3, 'k3');
insert into company (id, name) values (4, 'k4');
insert into company (id, name) values (5, 'k5');

insert into person (id, name, company_id) values (1, 'p1', 1);
insert into person (id, name, company_id) values (2, 'p2', 2);
insert into person (id, name, company_id) values (3, 'p3', 3);
insert into person (id, name, company_id) values (4, 'p4', 2);
insert into person (id, name, company_id) values (5, 'p5', 5);
insert into person (id, name, company_id) values (6, 'p6', 5);
insert into person (id, name) values (7, 'p7');
insert into person (id, name, company_id) values (8, 'p8', 5);

-- 1)
select person.name as p_name, company.name as c_name from company right join person on company.id = person.company_id where person.company_id != 5 OR person.company_id IS NULL;
-- 2)
select company.name, count(*) from company join person on company.id = person.company_id group by company.name order by count(*) desc limit 1;