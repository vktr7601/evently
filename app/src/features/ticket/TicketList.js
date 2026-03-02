import { useState, useEffect } from 'react';
import axiosClient from '../../api/axiosClient';
import { ROUTES } from '../../constants/routes';
import Spinner from '../../components/layout/Spinner';
import TicketListItem from './TicketListItem';

const TicketList = () => {
    const [tickets, setTickets] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        axiosClient.get(ROUTES.TICKETS.USER)
            .then(res => {
                console.log("Fetched tickets:", res.data);
                setTickets(res.data);
            })
            .catch(err => {
                console.error("Error fetching tickets:", err);
            })
            .finally(() => {
                setIsLoading(false);
            });
    }, []);

    if (isLoading) {
        return <Spinner message="Loading tickets..." />;
    }

    return (
        <div>
            <h1>My Tickets</h1>
            {tickets.length === 0 ? (
                <p>You have no tickets yet.</p>
            ) : (
                <div className="row">
                    {tickets.map(ticket => (
                       <TicketListItem key={ticket.id} ticket={ticket} />
                    ))}
                </div>
            )}
        </div>
    );

};

export default TicketList;