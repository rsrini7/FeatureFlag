create table if not exists ff4j_audit
(
    evt_uuid     varchar(40)  not null,
    evt_time     timestamp    not null,
    evt_type     varchar(30)  not null,
    evt_name     varchar(30)  not null,
    evt_action   varchar(30)  not null,
    evt_hostname varchar(100) not null,
    evt_source   varchar(30)  not null,
    evt_duration integer,
    evt_user     varchar(30),
    evt_value    varchar(100),
    evt_keys     varchar(255),
    constraint ff4j_audit_pkey
        primary key (evt_uuid, evt_time)
);
