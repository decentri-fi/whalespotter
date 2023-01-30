create table transactions
(
    id           varchar(100) not null,
    network      varchar(20)  not null,
    from_address varchar(255) not null,
    to_address   varchar(255) default null,
    block        varchar(100) not null,
    block_time   timestamp    not null,
    value        varchar(50)  not null,
    input        text         default null,
    primary key (id)
);
