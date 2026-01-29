-- =====================================================
-- Event Classifications (Categories)
-- =====================================================
INSERT INTO classifications (name, created_at, updated_at)
VALUES ('Theater', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Sports', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Concert', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Comedy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Festival', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Opera', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Dance', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Exhibition', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- =====================================================
-- Locations (Venues)
-- =====================================================
INSERT INTO locations (name, created_at, updated_at)
VALUES ('Madison Square Garden', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Royal Opera House', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Red Rocks Amphitheatre', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Broadway Theater', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Wembley Stadium', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Comedy Store', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Central Park', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('O2 Arena', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- =====================================================
-- Events
-- =====================================================
INSERT INTO events (name, description, created_at, updated_at)
VALUES ('The Lion King',
        'Broadway musical adaptation of Disney classic featuring stunning costumes and music by Elton John',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('NBA Finals Game 7', 'Championship basketball game deciding the season winner', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       ('Rock Legends Tour 2026', 'Multi-city rock concert tour featuring classic hits and new material',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Stand-Up Comedy Night', 'Evening of laughs with top comedians from around the world', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       ('Summer Music Festival', 'Three-day outdoor festival with multiple stages and diverse artists',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('La Traviata', 'Giuseppe Verdi opera performed by world-class singers and orchestra', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       ('Swan Lake Ballet', 'Classical ballet performance by renowned dance company', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       ('FIFA World Cup Quarter Final', 'International football tournament knockout stage match', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       ('Modern Art Exhibition', 'Contemporary art showcase featuring emerging and established artists',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Jazz Under the Stars', 'Outdoor jazz concert series in the park', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- =====================================================
-- Events to Classifications Mapping (Many-to-Many)
-- =====================================================
-- The Lion King: Theater
INSERT INTO events_classifications (event_id, classification_id, created_at, updated_at)
SELECT e.id, c.id, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM events e, classifications c
WHERE e.name = 'The Lion King' AND c.name = 'Theater';

-- NBA Finals: Sports
INSERT INTO events_classifications (event_id, classification_id, created_at, updated_at)
SELECT e.id, c.id, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM events e, classifications c
WHERE e.name = 'NBA Finals Game 7' AND c.name = 'Sports';

-- Rock Legends Tour: Concert
INSERT INTO events_classifications (event_id, classification_id, created_at, updated_at)
SELECT e.id, c.id, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM events e, classifications c
WHERE e.name = 'Rock Legends Tour 2026' AND c.name = 'Concert';

-- Stand-Up Comedy Night: Comedy
INSERT INTO events_classifications (event_id, classification_id, created_at, updated_at)
SELECT e.id, c.id, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM events e, classifications c
WHERE e.name = 'Stand-Up Comedy Night' AND c.name = 'Comedy';

-- Summer Music Festival: Festival + Concert
INSERT INTO events_classifications (event_id, classification_id, created_at, updated_at)
SELECT e.id, c.id, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM events e, classifications c
WHERE e.name = 'Summer Music Festival' AND c.name IN ('Festival', 'Concert');

-- La Traviata: Opera + Theater
INSERT INTO events_classifications (event_id, classification_id, created_at, updated_at)
SELECT e.id, c.id, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM events e, classifications c
WHERE e.name = 'La Traviata' AND c.name IN ('Opera', 'Theater');

-- Swan Lake: Dance + Theater
INSERT INTO events_classifications (event_id, classification_id, created_at, updated_at)
SELECT e.id, c.id, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM events e, classifications c
WHERE e.name = 'Swan Lake Ballet' AND c.name IN ('Dance', 'Theater');

-- FIFA World Cup: Sports
INSERT INTO events_classifications (event_id, classification_id, created_at, updated_at)
SELECT e.id, c.id, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM events e, classifications c
WHERE e.name = 'FIFA World Cup Quarter Final' AND c.name = 'Sports';

-- Modern Art Exhibition: Exhibition
INSERT INTO events_classifications (event_id, classification_id, created_at, updated_at)
SELECT e.id, c.id, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM events e, classifications c
WHERE e.name = 'Modern Art Exhibition' AND c.name = 'Exhibition';

-- Jazz Under the Stars: Concert + Festival
INSERT INTO events_classifications (event_id, classification_id, created_at, updated_at)
SELECT e.id, c.id, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM events e, classifications c
WHERE e.name = 'Jazz Under the Stars' AND c.name IN ('Concert', 'Festival');

-- =====================================================
-- Events Locations (Event Instances at Specific Venues)
-- =====================================================
INSERT INTO events_locations (id, event_id, location_id, date, number, total_tickets, booked_tickets, price, created_at,
                              updated_at)
VALUES
-- The Lion King at Broadway Theater
(1, 1, 4, '2026-03-15 19:30:00', 1001, 1500, 0, 125.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 4, '2026-03-16 19:30:00', 1002, 1500, 0, 125.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 4, '2026-03-17 14:00:00', 1003, 1500, 0, 99.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- NBA Finals at Madison Square Garden
(4, 2, 1, '2026-06-20 20:00:00', 2001, 20000, 0, 350.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Rock Legends Tour at multiple venues
(5, 3, 1, '2026-07-10 20:00:00', 3001, 18000, 0, 89.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 3, 3, '2026-07-15 19:00:00', 3002, 9500, 0, 75.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 3, 8, '2026-07-22 20:00:00', 3003, 20000, 0, 95.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Stand-Up Comedy at Comedy Store
(8, 4, 6, '2026-04-05 21:00:00', 4001, 300, 0, 45.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 4, 6, '2026-04-06 21:00:00', 4002, 300, 0, 45.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Summer Music Festival at Central Park
(10, 5, 7, '2026-08-01 12:00:00', 5001, 50000, 0, 199.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 5, 7, '2026-08-02 12:00:00', 5002, 50000, 0, 199.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 5, 7, '2026-08-03 12:00:00', 5003, 50000, 0, 199.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- La Traviata at Royal Opera House
(13, 6, 2, '2026-05-10 19:00:00', 6001, 2200, 0, 180.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 6, 2, '2026-05-11 19:00:00', 6002, 2200, 0, 180.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Swan Lake at Royal Opera House
(15, 7, 2, '2026-09-20 19:30:00', 7001, 2200, 0, 150.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 7, 2, '2026-09-21 19:30:00', 7002, 2200, 0, 150.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- FIFA World Cup at Wembley Stadium
(17, 8, 5, '2026-07-08 18:00:00', 8001, 90000, 0, 250.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Modern Art Exhibition at O2 Arena
(18, 9, 8, '2026-10-01 10:00:00', 9001, 5000, 0, 25.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Jazz Under the Stars at Central Park
(19, 10, 7, '2026-06-15 20:00:00', 10001, 10000, 0, 35.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 10, 7, '2026-06-22 20:00:00', 10002, 10000, 0, 35.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);