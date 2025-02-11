CREATE TABLE public.genres (
                               id BIGINT PRIMARY KEY,
                               name VARCHAR(100) NOT NULL UNIQUE,
                               image_path VARCHAR(255),
                               description VARCHAR(500)
);


ALTER TABLE public.genres OWNER TO postgres;

CREATE SEQUENCE public.genres_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.genres_id_seq OWNER TO postgres;

ALTER SEQUENCE public.genres_id_seq OWNED BY public.genres.id;



