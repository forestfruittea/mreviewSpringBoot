CREATE TABLE public.ratings (
                                id BIGINT PRIMARY KEY,
                                user_id BIGINT NOT NULL,
                                movie_id BIGINT NOT NULL,
                                rating INTEGER NOT NULL,
                                CONSTRAINT unique_user_movie UNIQUE (user_id, movie_id),
                                CONSTRAINT ratings_rating_check CHECK (rating >= 1 AND rating <= 100),
                                CONSTRAINT fk_movie FOREIGN KEY (movie_id) REFERENCES public.movies(id) ON DELETE CASCADE,
                                CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE
);


ALTER TABLE public.ratings OWNER TO postgres;

CREATE SEQUENCE public.ratings_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.ratings_id_seq OWNER TO postgres;

ALTER SEQUENCE public.ratings_id_seq OWNED BY public.ratings.id;
