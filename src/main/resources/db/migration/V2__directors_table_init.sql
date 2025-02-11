CREATE TABLE public.directors (
                                  id BIGINT PRIMARY KEY,
                                  name VARCHAR(255) NOT NULL UNIQUE,
                                  bio VARCHAR(1500),
                                  date_of_birth DATE,
                                  photo_path VARCHAR(255)
);



ALTER TABLE public.directors OWNER TO postgres;

CREATE SEQUENCE public.directors_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.directors_id_seq OWNER TO postgres;

ALTER SEQUENCE public.directors_id_seq OWNED BY public.directors.id;
