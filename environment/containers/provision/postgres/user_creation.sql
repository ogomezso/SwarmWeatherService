DO
$body$
BEGIN
   IF NOT EXISTS (
      SELECT *
      FROM   pg_catalog.pg_user
      WHERE  usename = 'weatheruser') THEN
    	CREATE USER weatheruser WITH PASSWORD 'weather';
   END IF;
END
$body$;