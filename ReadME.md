1. GET /venues — List All Venues

Purpose: Returns a summary list of all venues.

    Design Decision: Returns only essential metadata (ID, Name, City).

    Benefit: Lightweight payloads optimized for search and gallery views.

    Scalability: Supports future pagination (e.g., ?page=1&size=10).

2. GET /venues/:id — Venue Details & Events

Purpose: Returns full details of one location + its upcoming schedule.

    Design Decision: Embeds incoming_events directly inside the response.

    Benefit: Eliminates the "N+1 problem"—the frontend gets everything in one request instead of two.

    Logic: The server automatically filters out past events, returning only those where timestamp≥now.