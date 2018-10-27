# --- !Ups

create table "sending_requests" ("name" VARCHAR NOT NULL,"origin" VARCHAR NOT NULL,"destination" VARCHAR NOT NULL,"sending_request_id" BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT);

# --- !Downs

drop table "sending_requests" if exists;