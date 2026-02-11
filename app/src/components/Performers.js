import {useEffect, useState} from "react";
import axios from 'axios';
import {Link} from "react-router-dom";

const Performers = () => {
    const [performers, setPerformers] = useState([]);

    useEffect(() => {
        axios.get(`http://localhost:8082/artists`)
            .then(res => {
                const data = Array.isArray(res.data) ? res.data : res.data?.content || [];
                console.log(data);
                setPerformers(data);
            })
            .catch(err => {
                console.error("Error fetching venue details:", err);
            });
    }, []);


    return (
        <div>
            <div className="row">
                {performers.map(performers => (
                    <div key={performers.id} className="col-12 col-md-6 col-lg-4 mb-4">
                        <PerformerItem performer={performers}/>
                    </div>
                ))}
            </div>
        </div>
    );
}

const PerformerItem = ({performer}) => {
    const hasImage = performer.imageUrl && performer.imageUrl.trim() !== "";

    return (
        <div className="card h-100 shadow-sm border-0 transition-hover">
            {hasImage ? (
                <img
                    src={performer.imageUrl}
                    className="card-img-top"
                    alt={performer.name}
                    style={{height: '260px', objectFit: 'cover'}}
                    onError={(e) => {
                        e.target.onerror = null;
                        e.target.src = "https://placehold.co/600x800?text=Artist+Image";
                    }}
                />
            ) : (
                <div
                    className="card-img-top"
                    style={{
                        height: '260px',
                        background: 'linear-gradient(135deg, #212529, #343a40)',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center'
                    }}
                >
                    <i className="bi bi-mic-fill text-white" style={{fontSize: '4rem'}}></i>
                </div>
            )}

            <div className="card-body d-flex flex-column p-4">
                <h5 className="card-title fw-bold mb-2">{performer.name}</h5>

                <p className="text-muted small mb-3 line-clamp-2" style={{
                    display: '-webkit-box',
                    WebkitLineClamp: '2',
                    WebkitBoxOrient: 'vertical',
                    overflow: 'hidden'
                }}>
                    {performer.bio || 'No biography available for this performer.'}
                </p>

                <div className="mt-auto pt-3 border-top d-flex align-items-center justify-content-end">
                    <Link
                        to={`/performers/${performer.id}`}
                        className="btn btn-sm btn-dark rounded-pill px-4"
                    >
                        View Profile
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default Performers;