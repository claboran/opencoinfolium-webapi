-- coin basic data

create TABLE int_test_coin_basic_data
(
    id                 BIGINT PRIMARY KEY NOT NULL,
    name               NVARCHAR(50),
    symbol             NVARCHAR(10),
    slug               NVARCHAR(50),
    image_url          NVARCHAR(512),
    circulating_supply BIGINT,
    total_supply       BIGINT,
    max_supply         BIGINT
);
alter table int_test_coin_basic_data
    add CONSTRAINT int_test_coin_basic_data_un UNIQUE (id);

create table int_test_coin_quote
(
    id                 BIGINT PRIMARY KEY auto_increment,
    coin_id            bigint not null,
    quote              double precision,
    quoted_at          timestamp,
    volume_24h         double precision,
    market_cap         double precision,
    percent_change_1h  double precision,
    percent_change_24h double precision,
    percent_change_7d  double precision

);

alter table int_test_coin_quote
    add foreign key (coin_id) references int_test_coin_basic_data (id);


create view int_test_quote_last_data(coin_id,
                                     quote,
                                     volume_24h,
                                     market_cap,
                                     quoted_at,
                                     percent_change_1h,
                                     percent_change_24h,
                                     percent_change_7d) as (
    select q.coin_id            as coin_id,
           q.quote              AS quote,
           q.volume_24h         as volume_24h,
           q.market_cap         as market_cap,
           m.max_time           AS quoted_at,
           q.percent_change_1h  AS percent_change_1h,
           q.percent_change_24h AS percent_change_24h,
           q.percent_change_7d  AS percent_change_7d

    from int_test_coin_quote q
             inner join
         (select coin_id, max(quoted_at) AS max_time
          from int_test_coin_quote
          group by coin_id) m on ((q.coin_id = m.coin_id) AND (q.quoted_at = m.max_time)));
