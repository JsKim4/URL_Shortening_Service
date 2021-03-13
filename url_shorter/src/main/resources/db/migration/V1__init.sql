create table short_url
(
    short_url_id        bigint        not null,
    short_resource      varchar(255) ,
    host                varchar(4000) not null,
    resource            varchar(4000) not null,
    port                int,
    protocol            varchar(255)  not null,
    request_count       bigint        not null default 0,
    aggregate_date_time datetime(6),
    primary key (short_url_id)
);
create table short_url_seq_generator(
    next_val bigint not null
);
insert into short_url_seq_generator values(1);