CREATE TABLE public.movies
(
    id           BIGINT PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    description  VARCHAR(500),
    country      VARCHAR(255),
    director_id  BIGINT       NOT NULL,
    release_date DATE,
    poster_path  VARCHAR(255) NOT NULL,
    length       BIGINT,
    budget       BIGINT,
    box_office   BIGINT,
    CONSTRAINT movies_director_id_fkey FOREIGN KEY (director_id)
        REFERENCES public.directors (id) ON DELETE CASCADE
);

CREATE SEQUENCE public.movies_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.movies_id_seq OWNER TO postgres;

ALTER SEQUENCE public.movies_id_seq OWNED BY public.movies.id;

CREATE TABLE public.movie_actor (
                                    movie_id BIGINT NOT NULL,
                                    actor_id BIGINT NOT NULL,
                                    PRIMARY KEY (movie_id, actor_id),
                                    CONSTRAINT movie_actor_actor_id_fkey FOREIGN KEY (actor_id) REFERENCES public.actors(id),
                                    CONSTRAINT movie_actor_movie_id_fkey FOREIGN KEY (movie_id) REFERENCES public.movies(id) ON DELETE CASCADE
);


ALTER TABLE public.movie_actor OWNER TO postgres;

CREATE TABLE public.movie_genre (
                                    movie_id BIGINT NOT NULL,
                                    genre_id BIGINT NOT NULL,
                                    PRIMARY KEY (movie_id, genre_id),
                                    CONSTRAINT movie_genre_genre_id_fkey FOREIGN KEY (genre_id) REFERENCES public.genres(id),
                                    CONSTRAINT movie_genre_movie_id_fkey FOREIGN KEY (movie_id) REFERENCES public.movies(id) ON DELETE CASCADE
);


ALTER TABLE public.movie_genre OWNER TO postgres;




ALTER TABLE public.movies OWNER TO postgres;




