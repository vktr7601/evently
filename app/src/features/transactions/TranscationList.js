import { useEffect, useState } from "react"
import axiosClient from "../../api/axiosClient"
import { ROUTES } from "../../constants/routes"
import TranscationListItem from "./TranscationListItem"

const TransactionList = () => {
    const [transactions, setTransactions] = useState([]);
    useEffect(() => {
        axiosClient.get(`${ROUTES.PAYMENTS.PAYMENT_TRANSACTIONS}`)
            .then(res => {
                const prettyJson = JSON.stringify(res.data, null, 2);
                console.log("Fetched transactions:", prettyJson);
                setTransactions(res.data);
            })
            .catch(err => {
                console.error("Error fetching transactions:", err);
            });
    }, [])
    return (
        <div className="bg-light min-vh-100">
            <div className="container py-5">
                <div className="d-flex justify-content-between align-items-center mb-4 pb-2 border-bottom">
                    <h2 className="fw-bold text-dark mb-0">My Transactions</h2>
                </div>
                {transactions.length === 0 ? (
                    <div className="text-center py-5">
                        <p className="text-muted fs-5">No transactions found.</p>
                    </div>
                ) : (
                    <div className="list-group list-group-flush shadow-sm rounded-4 overflow-hidden">
                        {transactions.map(tx => (
                            <TranscationListItem key={tx.id} transaction={tx} />
                        ))}
                    </div>
                )}
            </div>
        </div>
    )
}

export default TransactionList;
