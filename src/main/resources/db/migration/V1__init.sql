create table comments (
    id bigserial primary key,
    comment text not null,
    time timestamp default current_timestamp
);

create table notifications (
    id bigserial primary key,
    comment_id bigint not null,
    time timestamp default current_timestamp,
    delivered boolean default true
);