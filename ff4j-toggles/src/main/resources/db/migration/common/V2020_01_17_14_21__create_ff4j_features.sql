create table if not exists ff4j_features
(
    feat_uid    varchar(100) not null
        constraint ff4j_features_pkey
            primary key,
    enable      integer      not null,
    description varchar(1000),
    strategy    varchar(1000),
    expression  varchar(255),
    groupname   varchar(100)
);
