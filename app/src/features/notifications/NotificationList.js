import  { useState, useEffect } from 'react';
import NotificationListItem from './NotificationListItem';
import { ROUTES } from '../../constants/routes';
import axiosClient from '../../api/axiosClient';
import Spinner from '../../components/layout/Spinner';

const NotificationList = ({ onSelectNote, activeId }) => {
    const [notifications, setNotifications] = useState([]);
    const[isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const fetchMails = async () => {
            try {
                axiosClient.get(ROUTES.NOTIFICATIONS.BASE)
                    .then(res => {
                        console.log("Fetched notifications:", res.data);
                        setNotifications(res.data);
                        setIsLoading(false);
                    })
                    .catch(err => {
                        console.error("Error fetching notifications:", err);
                        setIsLoading(false);
                    });
            } catch (err) {
                console.error("Fetch error:", err);
            } finally {
                setIsLoading(false);
            }
        };
        fetchMails();
    }, []);

   if (isLoading) {
        return <Spinner message="Loading orders..." />;
    }

    return (
        <div className="list-group list-group-flush">
            <div className="p-3 bg-white border-bottom fw-bold text-uppercase small text-secondary">
                Inbox ({notifications.length})
            </div>
            {notifications.map(note => (
                <div key={note.id} onClick={() => onSelectNote(note)}>
                    <NotificationListItem 
                        data={note} 
                        isActive={activeId === note.id} 
                    />
                </div>
            ))}
        </div>
    );
};

export default NotificationList;