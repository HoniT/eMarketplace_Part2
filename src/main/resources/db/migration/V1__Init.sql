CREATE TABLE public.items
(
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    price numeric(10, 2) NOT NULL,
    description character varying(255),
    submission_time timestamp with time zone NOT NULL,
    photo_url character varying,
    PRIMARY KEY (id)
);