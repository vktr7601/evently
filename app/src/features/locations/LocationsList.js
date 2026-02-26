import axios from 'axios';
import { useState, useEffect } from 'react';
import LocationsListItem from './LocationsListItem';
import Spinner from '../../components/layout/Spinner';

const LocationsList = () => {
    const [location, setLocations] = useState([]);
    const [loading, setLoading] = useState(true);
    useEffect(() => {
        axios.get("http://localhost:9000/locations")
            .then(res => {
                console.log(res.data);
                setLocations(res.data);
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                setLoading(false);
            });
    }, []);

    if (loading) {
        return <Spinner message="Loading locations..." />;
    }

    return (
        <div className="container mt-4">
            <div className="d-flex justify-content-between align-items-center mb-4 pb-2 border-bottom">
                <h2 className="fw-bold text-dark mb-0">Upcoming Events</h2>
            </div>

            <div className="row">
                {location.map(loc => (
                    <div key={loc.id} className="col-12 col-md-6 col-lg-4 mb-4">
                        <LocationsListItem location={loc} />
                    </div>
                ))}
            </div>
        </div>
    );
};

export default LocationsList;