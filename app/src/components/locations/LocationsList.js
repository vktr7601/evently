import axios from 'axios';
import { useState, useEffect } from 'react';
import LocationsListItem from './LocationsListItem';
const LocationsList = () => {
    const [locations, setEvents] = useState([]);
   
    useEffect(() => {
        axios.get("http://localhost:8082/locations")
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
                {locations.map(location => (
                    <div key={location.id} className="col-12 col-md-6 col-lg-4 mb-4">
                        <LocationsListItem location={location} />
                    </div>
                ))}
            </div>
        </div>
    );
};

export default LocationsList;