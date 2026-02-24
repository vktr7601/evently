
import { useEffect, useState } from "react";
import ArtistListItem from "./ArtistListItem";
import Spinner from "../../components/layout/Spinner";
import axios from "axios";

const ArtistList = () => {
    const [artists, setArtists] = useState([]);
    const [isloading, setIsLoading] = useState(true);

    useEffect(() => {
        axios.get(`http://localhost:8082/artists`)
            .then(res => {
                setArtists(res.data);
                setIsLoading(false);

            })
            .catch(err => {
                console.error("Error fetching artists:", err);
                setIsLoading(false);
            });
    }, []);

    if (isloading) {
        return <Spinner message="Loading artists..." />;
    }
    return (
        <div className="container mt-4">
            <div className="d-flex justify-content-between align-items-center mb-4 pb-2 border-bottom">
                <h2 className="fw-bold text-dark mb-0">Featured Artists</h2>
            </div>
            {artists.length === 0 ? (
                <div className="text-center py-5 mt-5">
                    <p className="text-muted">No artists found.</p>
                </div>
            ) : (
                <div className="row">
                    {artists.map(artist => (
                        <div key={artist.id} className="col-12 col-md-6 col-lg-4 mb-4">
                            <ArtistListItem artist={artist} />
                        </div>
                    ))}
                </div>)}
        </div>
    )
};

export default ArtistList;