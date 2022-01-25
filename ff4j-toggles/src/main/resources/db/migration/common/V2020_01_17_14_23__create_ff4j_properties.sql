create table if not exists ff4j_properties
(
    property_id  varchar(100) not null
        constraint ff4j_properties_pkey
            primary key,
    clazz        varchar(255) not null,
    currentvalue varchar(255),
    fixedvalues  varchar(1000),
    description  varchar(1000)
);
