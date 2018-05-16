create table books (
  id bigint auto_increment primary key,
  isbn varchar(255) not null,
  title varchar(255) unique not null
);