import { Link } from "react-router-dom";

const ArtistListItem = ({ artist }) => {
    return (
        <div className="card h-100 shadow-sm border-0 artist-card-hover">
            <Link 
                to={`/artists/${artist.id}`} 
                className="text-decoration-none text-dark h-100 d-flex flex-column"
            >
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
                </div>
            </Link>
        </div>
    );
};

export default ArtistListItem;