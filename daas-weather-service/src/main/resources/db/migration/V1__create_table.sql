DO
$body$
BEGIN

IF NOT EXISTS (
     SELECT 1
       FROM   information_schema.tables
       WHERE  table_schema = 'weather'
       AND    table_name = 'weather_audit'
  )
THEN

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
-- SET row_security = off;

SET search_path = weather, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

CREATE TABLE weather.weather_audit
(
       id_audit serial primary key,
       des_city text,
       des_date text,
       des_day text,
       des_high text,
       des_low text,
       des_text text
);

END IF;

END;

$body$;