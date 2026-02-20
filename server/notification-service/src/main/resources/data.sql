-- 1. Insert Notification Content first (the messages)
INSERT INTO notification_content (title, html_body, created_at, updated_at) VALUES
                                                                              ('Welcome!', '<h1>Welcome to Evently!</h1><p>We are glad to have you here.</p>', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                              ('New Event', '<p>A new <b>Art Gallery</b> event has been posted in your area.</p>', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                              ('Ticket Confirmed', '<p>Your ticket for the <b>Jazz Night</b> has been confirmed.</p>', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- -- 2. Insert Notifications for a specific User
-- -- Assuming the IDs generated above are 1, 2, and 3
-- -- Note: 'notification_content' matches your @JoinColumn(name = "notification_content")
INSERT INTO notifications (user_id, content_id, is_read, is_deleted, created_at, updated_at) VALUES
                                                                                                       (2, 1, false, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                       (2, 2, false, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                       (2, 3, true,  false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);