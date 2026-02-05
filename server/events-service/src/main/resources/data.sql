INSERT INTO performers (name, bio, updated_at, created_at, image_url)
VALUES ('The Midnight Echo', 'An indie-synthwave band known for cinematic soundscapes and 80s nostalgia.', now(),
        now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/Abel-Tesfaye-The-Weeknd-hurry-up-tomorrow-051425-tout-85e96d2443a94312beed6480f6d1fa20.jpg'),
       ('Dr. Sarah Jenkins', 'Renowned neuroscientist and keynote speaker on the future of AI and human ethics.', now(),
        now(),'https://evently-spring.s3.eu-north-1.amazonaws.com/Abel-Tesfaye-The-Weeknd-hurry-up-tomorrow-051425-tout-85e96d2443a94312beed6480f6d1fa20.jpg'),
       ('Velocity Dance Crew', 'Award-winning contemporary dance troupe specializing in urban-classical fusion.', now(),
        now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/Abel-Tesfaye-The-Weeknd-hurry-up-tomorrow-051425-tout-85e96d2443a94312beed6480f6d1fa20.jpg'),
       ('Chef Marco Pierre', 'Michelin-starred chef presenting an interactive culinary masterclass.', now(), now(),'https://evently-spring.s3.eu-north-1.amazonaws.com/Abel-Tesfaye-The-Weeknd-hurry-up-tomorrow-051425-tout-85e96d2443a94312beed6480f6d1fa20.jpg'),
       ('Laughter Collective', 'A high-energy stand-up comedy troupe touring globally.', now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/Abel-Tesfaye-The-Weeknd-hurry-up-tomorrow-051425-tout-85e96d2443a94312beed6480f6d1fa20.jpg');

INSERT INTO venues (name, updated_at, created_at, image_url)
VALUES ('Madison Square Garden', now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/pexels-photo-33559338.jpeg'),
       ('The O2 Arena', now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/pexels-photo-33559338.jpeg'),
       ('Sydney Opera House', now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/pexels-photo-33559338.jpeg'),
       ('Red Rocks Amphitheatre', now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/pexels-photo-33559338.jpeg'),
       ('Royal Albert Hall', now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/pexels-photo-33559338.jpeg');

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

INSERT INTO events_venues (event_id, venue_id, date, number, total_tickets, booked_tickets, price, status, updated_at, created_at)
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