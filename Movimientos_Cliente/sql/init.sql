CREATE SCHEMA movimientos;


CREATE TABLE movimientos.mov_tclient (
                                         idclient uuid NOT NULL,
                                         age int8 NULL,
                                         direction varchar(200) NOT NULL,
                                         gender varchar(1) NULL,
                                         identification_card varchar(10) NOT NULL,
                                         "name" varchar(200) NOT NULL,
                                         phone varchar(20) NULL,
                                         "password" varchar(255) NOT NULL,
                                         status bool NOT NULL,
                                         CONSTRAINT mov_tclient_pkey PRIMARY KEY (idclient),
                                         CONSTRAINT uk_t6nk705adwab38pu2j54yv5m1 UNIQUE (identification_card)
);



CREATE TABLE movimientos.mov_taccount (
                                          idaccount uuid NOT NULL,
                                          accountnumber varchar(255) NOT NULL,
                                          accounttype varchar(255) NOT NULL,
                                          initialbalance numeric(19, 2) NOT NULL,
                                          status bool NOT NULL,
                                          idclient uuid NOT NULL,
                                          CONSTRAINT mov_taccount_pkey PRIMARY KEY (idaccount),
                                          CONSTRAINT fkehrc55eku8s6n93orcw89debq FOREIGN KEY (idclient) REFERENCES movimientos.mov_tclient(idclient)
);




CREATE TABLE movimientos.mov_tmovement (
                                           idmovement uuid NOT NULL,
                                           balance numeric(19, 2) NOT NULL,
                                           movementdate timestamp NOT NULL,
                                           movementtype varchar(255) NOT NULL,
                                           movementvalue numeric(19, 2) NOT NULL,
                                           status bool NOT NULL,
                                           idaccount uuid NOT NULL,
                                           CONSTRAINT mov_tmovement_pkey PRIMARY KEY (idmovement),
                                           CONSTRAINT fk6jbt3ufdqwob3pcikuojrt3aw FOREIGN KEY (idaccount) REFERENCES movimientos.mov_taccount(idaccount)
);
