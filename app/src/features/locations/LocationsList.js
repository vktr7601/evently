import axios from 'axios';
import { useState, useEffect } from 'react';
import LocationsListItem from './LocationsListItem';
import Spinner from '../../components/layout/Spinner';
import axiosClient from '../../api/axiosClient';
import { ROUTES } from '../../constants/routes';

const LocationsList = () => {
    const [locations, setLocations] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    useEffect(() => {
        axiosClient.get(ROUTES.LOCATIONS.BASE)
            .then(res => {
                console.log(res.data);
                setLocations(res.data);
                setIsLoading(false);
            })
            .catch(err => {
                console.error(err);
                setIsLoading(false);
            });
    }, []);

    if (isLoading) {
        return <Spinner message="Loading locations..." />;
    }

    return (
        <div className="container mt-4">
            <div className="row">
                {locations.map(loc => (
                    <div key={loc.id} className="col-12 col-md-6 col-lg-4 mb-4">
                        <LocationsListItem location={loc} />
                    </div>
                ))}
            </div>
        </div>
    );
};

export default LocationsList;