import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import { ROUTES } from '../../constants/routes';
import axiosClient from '../../api/axiosClient';
import Spinner from '../../components/layout/Spinner';
import EventLocationListItem from '../events/EventLocationListItem';
const LocationDetails = () => {
    const { id } = useParams();
    const [locations, setLocations] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        axiosClient.get(`${ROUTES.LOCATIONS.DETAILS(id)}`, { noAuth: true })
            .then(res => {
                console.log('Fetched Location Details:', res.data);
                setLocations(res.data);
                setIsLoading(false);
            })
            .catch(err => {
                setIsLoading(false);
            });
    }, [id]);

    if (isLoading) {
        return <Spinner message="Loading locations..." />;
    }

    return (
        <div className="bg-white min-vh-100">
            <section className="py-5 bg-light border-bottom">
                <div className="container">
                    <div className="row align-items-center">
                        <div className="col-lg-5 mb-4 mb-lg-0">
                            <img
                                src={locations.imageUrl}
                                alt={locations.name}
                                className="img-fluid rounded-4 shadow-lg w-100"
                                style={{ objectFit: 'cover', height: '400px' }}
                            />
                        </div>
                        <div className="col-lg-7 ps-lg-5">
                            <h6 className="text-primary fw-bold text-uppercase tracking-wider">
                                <i className="bi bi-geo-alt-fill me-2"></i>Premier Venue
                            </h6>
                            <h1 className="display-4 fw-black text-dark mb-3">{locations.name}</h1>
                            <p className="fs-5 text-secondary mb-4" style={{ lineHeight: '1.8' }}>
                                {locations.description}
                            </p>
                            <div className="d-flex gap-3">
                                <a href="#schedule" className="btn btn-primary btn-lg rounded-pill px-5">View Full Schedule</a>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section id="schedule" className="py-5">
                <div className="container">
                    <div className="mb-5 text-center">
                        <h2 className="fw-bold h1">Events at this Venue</h2>
                        <div className="bg-primary rounded mx-auto" style={{ height: '4px', width: '60px' }}></div>
                    </div>

                    <div className="row g-4">
                        {locations?.eventLocations?.length > 0 ? (
                            locations.eventLocations.map((loc) => (
                                <div key={loc.id || loc.eventLocationId} className="col-12 mb-4">
                                    <EventLocationListItem loc={loc} />
                                </div>
                            ))
                        ) : (
                            <div className="col-12 text-center py-5">
                                <div className="bg-light rounded-4 py-5 border">
                                    <i className="bi bi-calendar-x fs-1 text-muted mb-3 d-block"></i>
                                    <h4 className="fw-bold text-secondary">No Events Found</h4>
                                    <p className="text-muted fs-6 mb-4">
                                        There are currently no scheduled events for this location.
                                    </p>
                                    <Link
                                        to={ROUTES.EVENTS.LIST}
                                        className="btn btn-primary btn-lg rounded-pill px-5 shadow-sm"
                                    >
                                        Browse All Events
                                    </Link>
                                </div>
                            </div>
                        )}
                    </div>
                </div>
            </section>
        </div>
    );
};

export default LocationDetails;