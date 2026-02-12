INSERT INTO artists (name, bio, image_url, updated_at, created_at)
VALUES ('Vasko Vassilev',
        'The world-famous violin virtuoso and concertmaster of the Royal Opera House, known for sold-out arena tours.',
        'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/vesko_vasiliev.jpg', now(), now()),

       ('Azis',
        'A global phenomenon and the most recognizable Bulgarian voice worldwide, blending Balkan sounds with modern pop.',
        'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/azis.jpg', now(), now()),

       ('Miro',
        'A powerhouse vocalist known for his charismatic stage presence and some of the biggest radio hits in Bulgaria.',
        'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/miro.jpg', now(), now()),

       ('Papi Hans',
        'A creative visionary and trendsetter whose music videos and catchy hits dominate the streaming charts.',
        'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/papi_hans.jpg', now(), now()),

       ('Molec',
        'The biggest breakout duo in recent years, merging poetry with modern beats and selling out venues instantly.',
        'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/molec.jpg', now(), now()),

       ('100 Kila',
        'The most iconic figure in Bulgarian hip-hop, famous for his high-energy live shows and multi-award-winning hits.',
        'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/100_kila.jpg', now(), now()),

       ('Medi',
        'The current king of club music, whose hits generate millions of views and dominate the nightlife scene.',
        'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/medi.jpg', now(), now());

INSERT INTO locations (name, description, updated_at, created_at, image_url)
VALUES ('Arena Sofia',
        'The largest multi-purpose indoor arena in Bulgaria, hosting world-class concerts and major sporting events.',
        now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/arena_sofia.jpg'),

       ('Ancient Theatre of Philippopolis',
        'An iconic historical landmark over 2,000 years old, offering a unique atmosphere for performances under the stars.',
        now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/antinchen_teatur.jpg'),

       ('NDK (National Palace of Culture)',
        'The largest convention center in Southeast Europe, located in the heart of the capital city.',
        now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/NDK.jpg'),

       ('Varna Sea Casino',
        'An elegant event space overlooking the Black Sea, ideal for corporate gatherings and sophisticated celebrations.',
        now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/morsko_kino_varna.jpg'),

       ('Inter Expo Center',
        'A modern exhibition hub designed for business forums, technology summits, and industrial trade fairs.',
        now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/inter_esspo.jpg'),
       ('Ancient Theatre of Nessebar',
        'A breathtaking seaside amphitheater located in the UNESCO World Heritage old town of Nessebar.',
        now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/ancient_city_of_nesebar.jpg'),

       ('Ruse State Opera',
        'A majestic neo-baroque building known as "Little Vienna," perfect for high-end classical performances.',
        now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/ruse_opera.jpg'),
       ('Plovdiv International Fair',
        'One of the oldest trade centers in Europe, providing massive exhibition halls for global business summits.',
        now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/Plovdiv_internation_fair.jpg'),
       ('Kapana Creative District Stage',
        'An open-air urban space in the heart of Plovdiv’s art district, ideal for street festivals and crafts.',
        now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/kapana.jpg'),
       ('Bansko Ski Resort Event Center',
        'A premier winter destination offering modern facilities for international sports events and festivals.',
        now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/bansko_ski_resort.jpg');

INSERT INTO categories (name, description, updated_at, created_at)
VALUES ('Concerts', 'Live musical performances ranging from intimate club shows to massive arena tours.', now(), now()),

       ('Nightlife', 'DJ sets, electronic music events, and exclusive club parties.', now(), now()),

       ('Theatre & Arts', 'Stage plays, opera, ballet, and contemporary dance performances.', now(), now()),

       ('Sports', 'Football matches, basketball tournaments, and other high-energy athletic competitions.', now(),
        now()),

       ('Festivals', 'Multi-day outdoor events featuring music, food, and cultural experiences.', now(), now()),

       ('Family & Kids', 'Shows, circuses, and interactive workshops designed for all ages.', now(), now()),

       ('Business & Tech', 'Professional conferences, networking summits, and technology workshops.', now(), now()),

       ('Comedy', 'Stand-up comedy specials and improvisational theatre shows.', now(), now()),

       ('Culture & Tourism', 'Traditional folklore shows, museum exhibitions, and sightseeing events.', now(), now()),

       ('Cinema', 'Exclusive movie premieres, film festivals, and open-air screenings.', now(), now());

INSERT INTO events (id, name, image_url, description, artist_id, updated_at, created_at)
VALUES (1, 'Vasko Vassilev: Cinema Violin',
        'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/vesko_vasiliev.jpg',
        'A world-class performance of legendary movie soundtracks.', 1, now(), now()),
       (2, 'Azis: The Balkan King Live', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/azis.jpg',
        'A massive production featuring the most iconic voice of the Balkans.', 2, now(), now()),
       (3, 'Miro: Soul & Light Tour', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/miro.jpg',
        'An intimate yet powerful pop experience with Bulgaria’s favorite vocalist.', 3, now(), now()),
       (4, 'Molec: Urban Poetry Tour', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/molec.jpg',
        'The breakout duo brings their unique mix of hip-hop and soul to the big stage.', 5, now(), now()),
       (5, '100 Kila: Hip-Hop Summer', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/100_kila.jpg',
        'High-energy rap show with the most iconic figure in Bulgarian hip-hop.', 6, now(), now()),
       (6, 'Papi Hans: Pop-Up Mystery', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/papi_hans.jpg',
        'A creative and flamboyant pop spectacle like no other.', 4, now(), now()),
       (7, 'Medi: The Club King Tour', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/medi.jpg',
        'Experience the ultimate nightlife vibe with the biggest hits of the year.', 7,
        now(), now()),
       (8, 'Vasko Vassilev: Sunset Classics',
        'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/vesko_vasiliev.jpg',
        'An exclusive seaside performance under the stars.', 1, now(), now());

INSERT INTO events_locations (event_id, location_id, date, total_tickets, booked_tickets, price, status, updated_at,
                              created_at)
VALUES (1, 1, '2026-03-15 20:00:00', 15000, 4500, 85.00, 'AVAILABLE', now(), now()),
       (1, 7, '2026-03-22 19:30:00', 600, 600, 120.00, 'SOLD_OUT', now(), now()),
       (2, 1, '2026-04-10 21:00:00', 18000, 16200, 150.00, 'AVAILABLE', now(), now()),
       (3, 2, '2026-05-05 20:00:00', 3000, 1200, 65.00, 'AVAILABLE', now(), now()),
       (4, 9, '2026-05-20 19:00:00', 1500, 1450, 40.00, 'AVAILABLE', now(), now()),
       (4, 3, '2026-05-28 20:00:00', 4000, 2100, 55.00, 'AVAILABLE', now(), now()),
       (5, 4, '2026-07-15 23:00:00', 1000, 850, 45.00, 'AVAILABLE', now(), now()),
       (5, 10, '2026-02-28 22:00:00', 2000, 0, 35.00, 'AVAILABLE', now(), now()),
       (6, 5, '2026-04-18 20:30:00', 5000, 1200, 45.00, 'AVAILABLE', now(), now()),
       (7, 10, '2026-03-05 23:30:00', 2000, 1950, 50.00, 'AVAILABLE', now(), now()),
       (7, 8, '2026-03-12 22:00:00', 8000, 3000, 40.00, 'AVAILABLE', now(), now()),
       (8, 6, '2026-08-20 20:30:00', 1200, 1200, 100.00, 'SOLD_OUT', now(), now());
INSERT INTO events_categories (event_id, category_id, active, updated_at, created_at)
VALUES (1, 1, true, now(), now()),
       (1, 3, true, now(), now()),
       (2, 1, true, now(), now()),
       (2, 2, true, now(), now()),
       (3, 1, true, now(), now()),
       (3, 9, true, now(), now()),
       (4, 5, true, now(), now()),
       (4, 1, true, now(), now()),
       (5, 2, true, now(), now()),
       (5, 5, true, now(), now()),
       (6, 1, true, now(), now()),
       (6, 10, true, now(), now()),
       (7, 2, true, now(), now()),
       (7, 1, true, now(), now()),
       (8, 9, true, now(), now()),
       (8, 3, true, now(), now());