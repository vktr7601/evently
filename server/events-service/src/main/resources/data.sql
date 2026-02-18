-- 1. Clear existing data in correct order
TRUNCATE TABLE events_categories, events_locations, events, artists, locations, categories CASCADE;

-- 2. Artists
INSERT INTO artists (id, name, bio, image_url, updated_at, created_at)
VALUES (1, 'Vasko Vassilev', 'The world-famous violin virtuoso and concertmaster of the Royal Opera House, known for sold-out arena tours.', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/vesko_vasiliev.jpg', now(), now()),
       (2, 'Azis', 'A global phenomenon and the most recognizable Bulgarian voice worldwide, blending Balkan sounds with modern pop.', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/azis.jpg', now(), now()),
       (3, 'Miro', 'A powerhouse vocalist known for his charismatic stage presence and some of the biggest radio hits in Bulgaria.', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/miro.jpg', now(), now()),
       (4, 'Papi Hans', 'A creative visionary and trendsetter whose music videos and catchy hits dominate the streaming charts.', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/papi_hans.jpg', now(), now()),
       (5, 'Molec', 'The biggest breakout duo in recent years, merging poetry with modern beats and selling out venues instantly.', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/molec.jpg', now(), now()),
       (6, '100 Kila', 'The most iconic figure in Bulgarian hip-hop, famous for his high-energy live shows and multi-award-winning hits.', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/100_kila.jpg', now(), now()),
       (7, 'Medi', 'The current king of club music, whose hits generate millions of views and dominate the nightlife scene.', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/medi.jpg', now(), now())
  ON CONFLICT (id) DO NOTHING;

-- 3. Locations
INSERT INTO locations (id, name, description, updated_at, created_at, image_url)
VALUES (1, 'Arena Sofia', 'The largest multi-purpose indoor arena in Bulgaria, hosting world-class concerts and major sporting events.', now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/arena_sofia.jpg'),
       (2, 'Ancient Theatre of Philippopolis', 'An iconic historical landmark over 2,000 years old, offering a unique atmosphere for performances under the stars.', now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/antinchen_teatur.jpg'),
       (3, 'NDK (National Palace of Culture)', 'The largest convention center in Southeast Europe, located in the heart of the capital city.', now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/NDK.jpg'),
       (4, 'Varna Sea Casino', 'An elegant event space overlooking the Black Sea, ideal for corporate gatherings and sophisticated celebrations.', now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/morsko_kino_varna.jpg'),
       (5, 'Inter Expo Center', 'A modern exhibition hub designed for business forums, technology summits, and industrial trade fairs.', now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/inter_esspo.jpg'),
       (6, 'Ancient Theatre of Nessebar', 'A breathtaking seaside amphitheater located in the UNESCO World Heritage old town of Nessebar.', now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/ancient_city_of_nesebar.jpg'),
       (7, 'Ruse State Opera', 'A majestic neo-baroque building known as "Little Vienna," perfect for high-end classical performances.', now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/ruse_opera.jpg'),
       (8, 'Plovdiv International Fair', 'One of the oldest trade centers in Europe, providing massive exhibition halls for global business summits.', now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/Plovdiv_internation_fair.jpg'),
       (9, 'Kapana Creative District Stage', 'An open-air urban space in the heart of Plovdiv’s art district, ideal for street festivals and crafts.', now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/kapana.jpg'),
       (10, 'Bansko Ski Resort Event Center', 'A premier winter destination offering modern facilities for international sports events and festivals.', now(), now(), 'https://evently-spring.s3.eu-north-1.amazonaws.com/venues/bansko_ski_resort.jpg')
  ON CONFLICT (id) DO NOTHING;

-- 4. Categories
INSERT INTO categories (id, name, description, updated_at, created_at)
VALUES (1, 'Concerts', 'Live musical performances ranging from intimate club shows to massive arena tours.', now(), now()),
       (2, 'Nightlife', 'DJ sets, electronic music events, and exclusive club parties.', now(), now()),
       (3, 'Theatre & Arts', 'Stage plays, opera, ballet, and contemporary dance performances.', now(), now()),
       (4, 'Sports', 'Football matches, basketball tournaments, and other high-energy athletic competitions.', now(), now()),
       (5, 'Festivals', 'Multi-day outdoor events featuring music, food, and cultural experiences.', now(), now()),
       (6, 'Family & Kids', 'Shows, circuses, and interactive workshops designed for all ages.', now(), now()),
       (7, 'Business & Tech', 'Professional conferences, networking summits, and technology workshops.', now(), now()),
       (8, 'Comedy', 'Stand-up comedy specials and improvisational theatre shows.', now(), now()),
       (9, 'Culture & Tourism', 'Traditional folklore shows, museum exhibitions, and sightseeing events.', now(), now()),
       (10, 'Cinema', 'Exclusive movie premieres, film festivals, and open-air screenings.', now(), now())
  ON CONFLICT (id) DO NOTHING;

-- 5. Events
INSERT INTO events (id, name, image_url, description, artist_id, updated_at, created_at, active)
VALUES (1, 'Vasko Vassilev: Cinema Violin', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/vesko_vasiliev.jpg', 'A world-class performance of legendary movie soundtracks.', 1, now(), now(), true),
       (2, 'Azis: The Balkan King Live', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/azis.jpg', 'A massive production featuring the most iconic voice of the Balkans.', 2, now(), now(), true),
       (3, 'Miro: Soul & Light Tour', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/miro.jpg', 'An intimate yet powerful pop experience with Bulgaria’s favorite vocalist.', 3, now(), now(), true),
       (4, 'Molec: Urban Poetry Tour', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/molec.jpg', 'The breakout duo brings their unique mix of hip-hop and soul to the big stage.', 5, now(), now(), true),
       (5, '100 Kila: Hip-Hop Summer', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/100_kila.jpg', 'High-energy rap show with the most iconic figure in Bulgarian hip-hop.', 6, now(), now(), true),
       (6, 'Papi Hans: Pop-Up Mystery', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/papi_hans.jpg', 'A creative and flamboyant pop spectacle like no other.', 4, now(), now(), true),
       (7, 'Medi: The Club King Tour', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/medi.jpg', 'Experience the ultimate nightlife vibe with the biggest hits of the year.', 7, now(), now(), true),
       (8, 'Vasko Vassilev: Sunset Classics', 'https://evently-spring.s3.eu-north-1.amazonaws.com/artists/vesko_vasiliev.jpg', 'An exclusive seaside performance under the stars.', 1, now(), now(), true)
  ON CONFLICT (id) DO NOTHING;
INSERT INTO events_categories (event_id, category_id)
VALUES (1, 1), (1, 9), -- Vasko: Concerts, Culture
       (2, 1), (2, 2), -- Azis: Concerts, Nightlife
       (3, 1),         -- Miro: Concerts
       (4, 1), (4, 2), -- Molec: Concerts, Nightlife
       (5, 1), (5, 2), -- 100 Kila: Concerts, Nightlife
       (6, 1), (6, 10),-- Papi Hans: Concerts, Cinema
       (7, 2),         -- Medi: Nightlife
       (8, 1), (8, 9)  -- Vasko Sunset: Concerts, Culture
  ON CONFLICT DO NOTHING;
-- 6. Events Locations
INSERT INTO events_locations (event_id, location_id, date, total_tickets, price, status, updated_at, created_at)
VALUES (1, 1, '2026-03-15 20:00:00', 15000, 85.00, 'AVAILABLE', now(), now()),
       (1, 7, '2026-03-22 19:30:00', 600, 120.00, 'SOLD_OUT', now(), now()),
       (2, 1, '2026-04-10 21:00:00', 18000, 150.00, 'AVAILABLE', now(), now()),
       (3, 2, '2026-05-05 20:00:00', 3000, 65.00, 'AVAILABLE', now(), now()),
       (4, 9, '2026-05-20 19:00:00', 1500, 40.00, 'AVAILABLE', now(), now()),
       (4, 3, '2026-05-28 20:00:00', 4000, 55.00, 'AVAILABLE', now(), now()),
       (5, 4, '2026-07-15 23:00:00', 1000, 45.00, 'AVAILABLE', now(), now()),
       (5, 10, '2026-02-28 22:00:00', 2000, 35.00, 'AVAILABLE', now(), now()),
       (6, 5, '2026-04-18 20:30:00', 5000, 45.00, 'AVAILABLE', now(), now()),
       (7, 10, '2026-03-05 23:30:00', 2000, 50.00, 'AVAILABLE', now(), now()),
       (7, 8, '2026-03-12 22:00:00', 8000, 40.00, 'AVAILABLE', now(), now()),
       (8, 6, '2026-08-20 20:30:00', 1200, 100.00, 'SOLD_OUT', now(), now());

-- 7. Corrected Sequence Syncs
SELECT setval(pg_get_serial_sequence('artists', 'id'), (SELECT MAX(id) FROM artists));
SELECT setval(pg_get_serial_sequence('locations', 'id'), (SELECT MAX(id) FROM locations));
SELECT setval(pg_get_serial_sequence('categories', 'id'), (SELECT MAX(id) FROM categories));
SELECT setval(pg_get_serial_sequence('events', 'id'), (SELECT MAX(id) FROM events));
SELECT setval(pg_get_serial_sequence('events_locations', 'id'), (SELECT MAX(id) FROM events_locations));