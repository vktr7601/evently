
import {useEffect, useState} from "react";
import axios from 'axios';
import ArtistListItem from "./ArtistListItem";

const ArtistList = () => {
    const [artist, setArtist] = useState([]);

    useEffect(() => {
        axios.get(`http://localhost:8082/artists`)
            .then(res => {
                setArtist(res.data);
            })
            .catch(err => {
                console.error("Error fetching venue details:", err);
            });
    }, []);


    return(
          <div className="container mt-4">
            <div className="d-flex justify-content-between align-items-center mb-4 pb-2 border-bottom">
                <h2 className="fw-bold text-dark mb-0">Featured Artists</h2>
            </div>

            <div className="row">
                {artist.map(artist => (
                    <div key={artist.id} className="col-12 col-md-6 col-lg-4 mb-4">
                        <ArtistListItem artist={artist} />
                    </div>
                ))}
            </div>
        </div>
    )
};

export default ArtistList;