import { Link } from "react-router-dom";

const LocationsListItem = ({ location }) => {
    return (
        <div className="card h-100 shadow-sm border-0 transition-hover">
            {/* Standardized 200px Image */}
            <img
                src={location.imageUrl}
                className="card-img-top"
                alt={location.name}
                style={{ height: '200px', objectFit: 'cover' }}
            />

            <div className="card-body d-flex flex-column p-4">
                {/* Title matches Artist/Event scale */}
                <h5 className="card-title fw-bold mb-1">{location.name}</h5>

                {/* Standardized Bio/Description clamping */}
                <p className="text-secondary small mb-3" style={{
                    display: '-webkit-box',
                    WebkitLineClamp: '2',
                    WebkitBoxOrient: 'vertical',
                    overflow: 'hidden',
                    fontSize: '0.9rem'
                }}>
                    {location.description}
                </p>

                {/* Footer Section - Standardized Divider and Spacing */}
                <div className="mt-auto pt-3 border-top d-flex align-items-center justify-content-between">
                    <Link
                        to={`/locations/${location.id}`}
                        className="btn btn-sm btn-primary rounded-pill px-4 shadow-sm"
                    >
                        Explore Events
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default LocationsListItem;