CREATE TABLE public.reviews (
                                id BIGINT PRIMARY KEY,
                                content VARCHAR(255) NOT NULL,
                                created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                user_id BIGINT NOT NULL,
                                movie_id BIGINT NOT NULL,
                                CONSTRAINT reviews_movie_id_fkey FOREIGN KEY (movie_id) REFERENCES public.movies(id),
                                CONSTRAINT reviews_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id)
);


ALTER TABLE public.reviews OWNER TO postgres;

CREATE SEQUENCE public.reviews_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.reviews_id_seq OWNER TO postgres;

ALTER SEQUENCE public.reviews_id_seq OWNED BY public.reviews.id;
