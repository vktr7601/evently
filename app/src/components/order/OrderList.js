import axios from 'axios';
import { useState, useEffect } from 'react';
import OrderListItem from './OrderListItem';
const OrderList = () => {
    const [orders, setOrders] = useState([]);
    useEffect(() => {
        const headers = { "X-User-Id": 1 };
        axios.get(`http://localhost:8081/orders`, { headers })
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
                        // col-12 ensures each order takes up the full width, stacking vertically
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