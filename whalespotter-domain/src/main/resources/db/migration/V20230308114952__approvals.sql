create table approvals(
    id bigserial primary key,
    owner varchar not null,
    spender varchar not null,
    amount varchar not null,
    token varchar not null,
    network varchar not null
);
