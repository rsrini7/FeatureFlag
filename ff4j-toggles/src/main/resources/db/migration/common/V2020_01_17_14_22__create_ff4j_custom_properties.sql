create table if not exists ff4j_custom_properties
(
    property_id  varchar(100) not null,
    clazz        varchar(255) not null,
    currentvalue varchar(255),
    fixedvalues  varchar(1000),
    description  varchar(1000),
    feat_uid     varchar(100) not null
        constraint ff4j_custom_properties_feat_uid_fkey
            references ff4j_features,
    constraint ff4j_custom_properties_pkey
        primary key (property_id, feat_uid)
);
