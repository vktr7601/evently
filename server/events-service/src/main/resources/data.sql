INSERT INTO performers (name, bio, updated_at, created_at)
VALUES ('The Midnight Echo', 'An indie-synthwave band known for cinematic soundscapes and 80s nostalgia.', now(),
        now()),
       ('Dr. Sarah Jenkins', 'Renowned neuroscientist and keynote speaker on the future of AI and human ethics.', now(),
        now()),
       ('Velocity Dance Crew', 'Award-winning contemporary dance troupe specializing in urban-classical fusion.', now(),
        now()),
       ('Chef Marco Pierre', 'Michelin-starred chef presenting an interactive culinary masterclass.', now(), now()),
       ('Laughter Collective', 'A high-energy stand-up comedy troupe touring globally.', now(), now());

INSERT INTO locations (name, updated_at, created_at)
VALUES ('Madison Square Garden', now(), now()),
       ('The O2 Arena', now(), now()),
       ('Sydney Opera House', now(), now()),
       ('Red Rocks Amphitheatre', now(), now()),
       ('Royal Albert Hall', now(), now());

INSERT INTO categories (name, updated_at, created_at)
VALUES ('Music', now(), now()),
       ('Conference', now(), now()),
       ('Comedy', now(), now()),
       ('Food & Drink', now(), now()),
       ('Dance', now(), now());

INSERT INTO events (name, description, performer_id, updated_at, created_at)
VALUES ('Neon Horizons Tour', 'A night of synthwave anthems and retro-futuristic visuals by The Midnight Echo.', 1, now(), now()),
       ('AI & Ethics Summit', 'Dr. Sarah Jenkins explores the intersection of artificial intelligence and human ethics.', 2, now(), now()),
       ('Urban Pulse Live', 'Velocity Dance Crew presents a breathtaking fusion of urban and classical dance styles.', 3, now(), now()),
       ('The Culinary Experience', 'An interactive masterclass with Michelin-starred Chef Marco Pierre.', 4, now(), now()),
       ('Stand-Up Spectacular', 'An evening of non-stop laughs with the globally touring Laughter Collective.', 5, now(), now());

INSERT INTO events_locations (event_id, location_id, date, number, total_tickets, booked_tickets, price, status, updated_at, created_at)
VALUES (1, 1, '2026-03-15 20:00:00', 1000001, 18000, 4500, 85.00, 'AVAILABLE', now(), now()),
       (1, 4, '2026-03-22 19:30:00', 1000002, 9500, 9500, 75.00, 'SOLD_OUT', now(), now()),
       (2, 3, '2026-04-10 09:00:00', 1000003, 2500, 800, 150.00, 'AVAILABLE', now(), now()),
       (3, 2, '2026-05-05 19:00:00', 1000004, 20000, 12000, 60.00, 'AVAILABLE', now(), now()),
       (4, 5, '2026-05-20 18:00:00', 1000005, 5000, 3200, 120.00, 'AVAILABLE', now(), now()),
       (5, 1, '2026-06-01 21:00:00', 1000006, 18000, 16500, 55.00, 'AVAILABLE', now(), now()),
       (5, 2, '2026-06-08 20:00:00', 1000007, 20000, 0, 50.00, 'AVAILABLE', now(), now());

INSERT INTO events_categories (event_id, category_id, active, updated_at, created_at)
VALUES (1, 1, true, now(), now()),
       (1, 5, true, now(), now()),
       (2, 2, true, now(), now()),
       (3, 5, true, now(), now()),
       (3, 1, true, now(), now()),
       (4, 4, true, now(), now()),
       (4, 2, true, now(), now()),
       (5, 3, true, now(), now());