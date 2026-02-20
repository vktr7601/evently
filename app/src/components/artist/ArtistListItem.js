import { Link } from "react-router-dom";

const ArtistListItem = ({ artist }) => {
    return (
        <div className="card h-100 shadow-sm border-0 transition-hover">

            <img
                src={artist.imageUrl}
                className="card-img-top"
                alt={artist.name}
                style={{ height: '200px', objectFit: 'cover' }}
            />


            <div className="card-body d-flex flex-column p-4">

                <h5 className="card-title fw-bold mb-1">{artist.name}</h5>

                <p className="text-secondary small mb-3" style={{
                    display: '-webkit-box',
                    WebkitLineClamp: '2',
                    WebkitBoxOrient: 'vertical',
                    overflow: 'hidden',
                    fontSize: '0.9rem'
                }}>
                    {artist.bio}
                </p>

                <div className="mt-auto pt-3 border-top d-flex align-items-center justify-content-between">
                     <span className="text-dark fw-bold small">
                        <i className="bi bi-person me-1"></i>
                        <Link
                            to={`/artists/${artist.id}`} 
                             className="btn btn-sm btn-primary rounded-pill px-4 center"
                        >
                           About the Artist
                        </Link>
                    </span>
                </div>
            </div>
        </div>
    );
};

export default ArtistListItem;