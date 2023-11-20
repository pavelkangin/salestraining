--
-- PostgreSQL database dump
--

-- Dumped from database version 12.16
-- Dumped by pg_dump version 12.16

-- Started on 2023-11-21 00:12:05

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 211 (class 1259 OID 16447)
-- Name: analytics_sq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.analytics_sq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.analytics_sq OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 212 (class 1259 OID 16449)
-- Name: analytics; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.analytics (
    id integer DEFAULT nextval('public.analytics_sq'::regclass) NOT NULL,
    script_id integer NOT NULL,
    user_id integer NOT NULL,
    duration integer DEFAULT 0 NOT NULL,
    last_date date,
    record character varying(255) DEFAULT ''::character varying NOT NULL
);


ALTER TABLE public.analytics OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16434)
-- Name: dialogues_sq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.dialogues_sq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.dialogues_sq OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16436)
-- Name: dialogues; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dialogues (
    id integer DEFAULT nextval('public.dialogues_sq'::regclass) NOT NULL,
    script_id integer,
    sentence text,
    client boolean DEFAULT false
);


ALTER TABLE public.dialogues OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 16406)
-- Name: roles_sq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_sq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_sq OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 16408)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id integer DEFAULT nextval('public.roles_sq'::regclass) NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 16423)
-- Name: scripts_sq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.scripts_sq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.scripts_sq OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 16425)
-- Name: scripts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.scripts (
    id integer DEFAULT nextval('public.scripts_sq'::regclass) NOT NULL,
    name character varying(150) NOT NULL,
    description text
);


ALTER TABLE public.scripts OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16394)
-- Name: users_sq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_sq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_sq OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 16396)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer DEFAULT nextval('public.users_sq'::regclass) NOT NULL,
    name character varying(100) NOT NULL,
    email character varying(150) NOT NULL,
    password character varying(255) NOT NULL,
    active boolean DEFAULT false,
    expiry_date date,
    reset_code character varying(50),
    reset_expiry timestamp without time zone,
    role_id integer,
    wrong_pass_count integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 16419)
-- Name: ttt; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.ttt AS
 SELECT users.id,
    users.name,
    users.email,
    users.password,
    users.active,
    users.expiry_date,
    users.reset_code,
    users.reset_expiry,
    users.role_id
   FROM public.users;


ALTER TABLE public.ttt OWNER TO postgres;

--
-- TOC entry 2875 (class 0 OID 16449)
-- Dependencies: 212
-- Data for Name: analytics; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.analytics (id, script_id, user_id, duration, last_date, record) FROM stdin;
\.


--
-- TOC entry 2873 (class 0 OID 16436)
-- Dependencies: 210
-- Data for Name: dialogues; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dialogues (id, script_id, sentence, client) FROM stdin;
\.


--
-- TOC entry 2869 (class 0 OID 16408)
-- Dependencies: 205
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, name) FROM stdin;
1	Студент
2	Менеджер
\.


--
-- TOC entry 2871 (class 0 OID 16425)
-- Dependencies: 208
-- Data for Name: scripts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.scripts (id, name, description) FROM stdin;
\.


--
-- TOC entry 2867 (class 0 OID 16396)
-- Dependencies: 203
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, name, email, password, active, expiry_date, reset_code, reset_expiry, role_id, wrong_pass_count) FROM stdin;
3	Ivan Ivanov	ivan2@apple.com	61bd60c60d9fb60cc8fc7767669d40a1	f	\N	\N	\N	1	0
2	Peter	peter@apple.com	61bd60c60d9fb60cc8fc7767669d40a1	f	\N	\N	\N	2	0
5	aaa	aaa@bbb.com	e99a18c428cb38d5f260853678922e03	f	\N	\N	\N	1	0
1	Ivan	ivan@apple.com	61bd60c60d9fb60cc8fc7767669d40a1	f	\N	\N	\N	1	8
\.


--
-- TOC entry 2881 (class 0 OID 0)
-- Dependencies: 211
-- Name: analytics_sq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.analytics_sq', 1, false);


--
-- TOC entry 2882 (class 0 OID 0)
-- Dependencies: 209
-- Name: dialogues_sq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.dialogues_sq', 1, false);


--
-- TOC entry 2883 (class 0 OID 0)
-- Dependencies: 204
-- Name: roles_sq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_sq', 2, true);


--
-- TOC entry 2884 (class 0 OID 0)
-- Dependencies: 207
-- Name: scripts_sq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.scripts_sq', 1, false);


--
-- TOC entry 2885 (class 0 OID 0)
-- Dependencies: 202
-- Name: users_sq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_sq', 8, true);


--
-- TOC entry 2737 (class 2606 OID 16456)
-- Name: analytics analytics_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.analytics
    ADD CONSTRAINT analytics_pkey PRIMARY KEY (id);


--
-- TOC entry 2735 (class 2606 OID 16444)
-- Name: dialogues dialogues_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dialogues
    ADD CONSTRAINT dialogues_pkey PRIMARY KEY (id);


--
-- TOC entry 2731 (class 2606 OID 16413)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 2733 (class 2606 OID 16433)
-- Name: scripts scripts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scripts
    ADD CONSTRAINT scripts_pkey PRIMARY KEY (id);


--
-- TOC entry 2729 (class 2606 OID 16405)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 2738 (class 2606 OID 16414)
-- Name: users users_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id);


-- Completed on 2023-11-21 00:12:05

--
-- PostgreSQL database dump complete
--

