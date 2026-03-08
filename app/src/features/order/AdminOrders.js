import {useEffect, useState} from "react";
import axiosClient from "../../api/axiosClient";
import {ROUTES} from "../../constants/routes";
import Spinner from "../../components/layout/Spinner";
import OrderListItem from "./OrderListItem";

const AdminOrders = () => {
    const [isAdmin] = useState(localStorage.getItem("userRole") === "ADMIN");

    const [orders, setOrders] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    useEffect(() => {
        axiosClient.get(`${ROUTES.ORDERS.ADMIN}`)
            .then(res => {
                setOrders(res.data);
                setIsLoading(false);
            })
            .catch(err => {
                setIsLoading(false);
            });
    }, []);

    if (isLoading) {
        return <Spinner message="Loading orders..."/>;
    }
    return (
        <div className="row">
            {orders.map(order => (
                <div key={order.number} className="col-12 mb-3">
                    <OrderListItem order={order}/>
                </div>
            ))}
        </div>
    );
}


export default AdminOrders;