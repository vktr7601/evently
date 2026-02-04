import axios from 'axios';
import {useState, useEffect} from 'react';

const EventsCard = (props) => {
    const [events, setEvents] = useState([]);
    const [loading, setLoading] = useState(true);

    // useEffect(() => {
    //     axios.get("http://localhost:9000/events")
    //         .then(res => {
    //             console.log("Full Data:", res.data); // Look at this in the browser console!
    //
    //             // If your Spring backend uses Pagination, it might be res.data.content
    //             if (Array.isArray(res.data)) {
    //                 setEvents(res.data);
    //             } else if (res.data && Array.isArray(res.data.content)) {
    //                 setEvents(res.data.content);
    //             } else {
    //                 console.error("Expected an array but got:", typeof res.data);
    //             }
    //         })
    // }, []);

    return (
        <div>
            <h2>Event List</h2>
            {events.map(event => (
                <div key={event.id}>
                    <h3>{event.name}</h3>
                </div>
            ))}
        </div>
    );
}

export default EventsCard;