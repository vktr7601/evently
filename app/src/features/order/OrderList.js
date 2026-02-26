import axios from 'axios';
import { useState, useEffect } from 'react';
import OrderListItem from './OrderListItem';
const OrderList = () => {
    const [orders, setOrders] = useState([]);
    useEffect(() => {
        axios.get(`http://localhost:9000/orders`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("jwtToken")}`,
            }
        })
            .then(res => {
                console.log("Fetched orders:", res.data);
                setOrders(res.data);
            })
            .catch(err => {
                console.error("Error fetching orders:", err);
            });
    }, []);

    return (
        <div>
            <h1>My Orders</h1>
            {orders.length === 0 ? (
                <p>You have no orders yet.</p>
            ) : (
                <div className="row">
                    {orders.map(order => (
                        <div key={order.number} className="col-12 mb-3">
                            <OrderListItem order={order} />
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

export default OrderList;