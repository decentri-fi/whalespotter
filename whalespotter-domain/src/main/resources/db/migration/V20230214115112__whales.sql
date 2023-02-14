create table whales
(
    id         serial primary key,
    address    varchar(100) not null,
    ens        varchar(100) default null,
    logo       text         default null,
    importance bigint       default 0
)