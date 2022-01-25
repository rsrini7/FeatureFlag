create table if not exists ff4j_roles
(
    feat_uid  varchar(100) not null
        constraint ff4j_roles_feat_uid_fkey
            references ff4j_features,
    role_name varchar(100) not null,
    constraint ff4j_roles_pkey
        primary key (feat_uid, role_name)
);
