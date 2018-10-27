# --- !Ups

create table "items" ("name" VARCHAR NOT NULL,"quantity" BIGINT NOT NULL,"weight" BIGINT NOT NULL,"size" BIGINT NOT NULL,"is_fragile" BOOLEAN NOT NULL,"refrigeration_level" VARCHAR NOT NULL,"request_id" BIGINT NOT NULL,"item_id" BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT);
alter table "items" add constraint "sending_request_FK" foreign key("request_id") references "sending_requests"("sending_request_id") on update NO ACTION on delete CASCADE

# --- !Downs

alter table "items" drop constraint "sending_request_FK"
drop table "items" if exists;
