import NotificationListItem from "./NotificationListItem";

const NotificationList = () => {
    const notifications = [
        {
            "id": 1,
            "title": "Welcome!",
            "message": "<h1>Welcome to Evently!</h1><p>We are glad to have you here.</p>",
            "isRead": false,
            "createdAt": "2026-02-12T14:27:39.178044Z"
        },
        {
            "id": 2,
            "title": "New Event",
            "message": "<p>A new <b>Art Gallery</b> event has been posted in your area.</p>",
            "isRead": false,
            "createdAt": "2026-02-12T14:27:39.178044Z"
        },
        {
            "id": 3,
            "title": "Ticket Confirmed",
            "message": "<p>Your ticket for the <b>Jazz Night</b> has been confirmed.</p>",
            "isRead": true,
            "createdAt": "2026-02-12T14:27:39.178044Z"
        }
    ];

    return (
        <div className="list-group list-group-flush">
            {notifications.map(item => (
                <NotificationListItem key={item.id} data={item} />
            ))}
        </div>
    );
};

export default NotificationList;