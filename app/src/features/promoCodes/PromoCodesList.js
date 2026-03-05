
import React, { useEffect, useState } from 'react';
import axiosClient from '../../api/axiosClient';
import { ROUTES } from '../../constants/routes';
import Spinner from '../../components/layout/Spinner';
import PromoCodeListItem from './PromoCodeListItem';
const PromoCodesList = () => {
    const [promoCodes, setPromoCodes] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        axiosClient.get(ROUTES.PROMO_CODES.USER)
            .then(res => {
                console.log("Fetched promo codes:", res.data);
                setPromoCodes(res.data);
            })
            .catch(err => {
                console.error("Error fetching promo codes:", err);
            })
            .finally(() => {
                setIsLoading(false);
            });
    }, []);

    if (isLoading) {
        return <Spinner message="Loading promo codes..." />;
    }

    return (
        <div>
            <h1>My Promo Codes</h1>
            {promoCodes.length === 0 ? (
                <p>You have no promo codes yet.</p>
            ) : (
                <div className="row">
                    {promoCodes.map(promoCode => (
                       <PromoCodeListItem key={promoCode.id} promoCode={promoCode} />
                    ))}
                </div>
            )}
        </div>
    );

};

export default PromoCodesList;