-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.1-beta
-- PostgreSQL version: 10.0
-- Project Site: pgmodeler.com.br
-- Model Author: ---


-- Database creation must be done outside an multicommand file.
-- These commands were put in this file only for convenience.
-- -- object: gym_db | type: DATABASE --
-- -- DROP DATABASE IF EXISTS gym_db;
-- CREATE DATABASE gym_db
-- ;
-- -- ddl-end --
-- 

-- object: public.clients | type: TABLE --
-- DROP TABLE IF EXISTS public.clients CASCADE;
CREATE TABLE public.clients(
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	card_number serial NOT NULL,
	is_active boolean NOT NULL DEFAULT true,
	last_visit int4,
	access_level varchar(15) NOT NULL,
	CONSTRAINT clients_pk PRIMARY KEY (card_number)
	 WITH (FILLFACTOR = 10)

);
-- ddl-end --

-- object: public.visits | type: TABLE --
-- DROP TABLE IF EXISTS public.visits CASCADE;
CREATE TABLE public.visits(
	id serial NOT NULL,
	start_time timestamp,
	end_time timestamp,
	card_number_clients integer,
	CONSTRAINT visits_pk PRIMARY KEY (id)
	 WITH (FILLFACTOR = 10)

);
-- ddl-end --

-- object: clients_fk | type: CONSTRAINT --
-- ALTER TABLE public.visits DROP CONSTRAINT IF EXISTS clients_fk CASCADE;
ALTER TABLE public.visits ADD CONSTRAINT clients_fk FOREIGN KEY (card_number_clients)
REFERENCES public.clients (card_number) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: fk_clients_last_visit | type: CONSTRAINT --
-- ALTER TABLE public.clients DROP CONSTRAINT IF EXISTS fk_clients_last_visit CASCADE;
ALTER TABLE public.clients ADD CONSTRAINT fk_clients_last_visit FOREIGN KEY (last_visit)
REFERENCES public.visits (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


