CREATE TABLE public.actors (
                               id BIGINT PRIMARY KEY,
                               name VARCHAR(255) NOT NULL UNIQUE,
                               bio VARCHAR(1500),
                               year_of_birth BIGINT,
                               photo_path VARCHAR(255),
                               height BIGINT
);


ALTER TABLE public.actors OWNER TO postgres;

CREATE SEQUENCE public.actors_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.actors_id_seq OWNER TO postgres;

ALTER SEQUENCE public.actors_id_seq OWNED BY public.actors.id;