import { Link } from "react-router-dom";

 const EventListItem = ({ event }) => {
    return (
        <div className="card h-100 shadow-sm border-0 transition-hover">
            <img
                src={event.imageUrl}
                className="card-img-top"
                alt={event.name}
                style={{ height: '180px', objectFit: 'cover' }}
            />

            <div className="card-body d-flex flex-column p-4">
                <div className="d-flex flex-wrap gap-2 mb-3">
                    {event.categories?.map((x) => (
                        <Link
                            key={x.id}
                            to={`/events?category=${encodeURIComponent(x.name)}`}
                            className="badge bg-light text-primary border border-primary-subtle text-decoration-none"
                            style={{ fontSize: '0.75rem' }}
                        >
                            {x.name}
                        </Link>
                    ))}
                </div>

                <h5 className="card-title fw-bold mb-1">{event.name}</h5>
                <p className="text-secondary small text-truncate-2">
                    {event.description}
                </p>

                <div className="mt-auto pt-3 border-top d-flex align-items-center justify-content-between">
                    <span className="text-dark fw-bold small">
                        <i className="bi bi-person me-1"></i>
                        <Link
                            to={`/artists/${event.artist.id}`}
                            className="text-decoration-none text-primary"
                            style={{ fontSize: '0.85rem' }}
                        >
                           Learn About The Artist
                        </Link>
                    </span>
                    <Link
                        to={`/events/${event.id}`}
                        className="btn btn-sm btn-primary rounded-pill px-4"
                    >
                        Event Info
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default EventListItem;