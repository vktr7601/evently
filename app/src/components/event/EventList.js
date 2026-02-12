import axios from 'axios';
import { useState, useEffect } from 'react';
import EventListItem from './EventListItem';

const EventList = () => {
    const [events, setEvents] = useState([]);
   
    useEffect(() => {
        axios.get("http://localhost:8082/events")
            .then(res => {
                setEvents(res.data);
            })
            .catch(err => {
                console.error(err);
            });
    }, []);

    return (
        <div className="container mt-4">
            <div className="d-flex justify-content-between align-items-center mb-4 pb-2 border-bottom">
                <h2 className="fw-bold text-dark mb-0">Upcoming Events</h2>
            </div>

            <div className="row">
                {events.map(event => (
                    <div key={event.id} className="col-12 col-md-6 col-lg-4 mb-4">
                        <EventListItem event={event} />
                    </div>
                ))}
            </div>
        </div>
    );
};

export default EventList;