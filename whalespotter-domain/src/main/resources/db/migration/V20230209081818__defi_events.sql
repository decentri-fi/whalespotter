create table defi_events
(
    id             varchar(255) not null,
    event_type     varchar(50)  not null,
    transaction_id varchar(100) not null,
    protocol       varchar(100) default null,
    metadata       json         default null,
    primary key (id),
    foreign key (transaction_id) references transactions (id)
);