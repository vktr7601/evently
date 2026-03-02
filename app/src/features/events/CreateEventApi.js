import React, { useState } from 'react';
import axiosClient from '../../api/axiosClient';
import { ROUTES } from '../../constants/routes';
import { useNavigate } from 'react-router-dom';

const CreateEventApi = () => {
    const [jsonInput, setJsonInput] = useState('');
    const [status, setStatus] = useState({ type: '', message: '' });
    const navigate = useNavigate();

    const handlePublish = async () => {
        try {
            // 1. Validate if the string is actually valid JSON
            const parsedData = JSON.parse(jsonInput);
            axiosClient.post(`${ROUTES.EVENTS.ADMIN_CREATE}`, JSON.stringify(parsedData))
                .then(res => {
                    navigate(ROUTES.EVENTS.DETAILS(res.data.id));
                })
                .catch(err => {
                    console.error("Error creating events:", err);
                })
        } catch (err) {
            setStatus({ type: 'danger', message: 'Invalid JSON format. Please correct it and try again.' });
        }
    };

    return (
        <div className="container mt-5">
            <div className="card shadow border-0">
                <div className="card-header bg-dark text-white">
                    <h4 className="mb-0">API Event Creator</h4>
                </div>
                <div className="card-body">
                    <p className="text-muted">Paste your Event JSON array below to bulk-import data.</p>

                    {status.message && (
                        <div className={`alert alert-${status.type} alert-dismissible fade show`}>
                            {status.message}
                        </div>
                    )}

                    <textarea
                        className="form-control mb-3 font-monospace"
                        rows="15"
                        placeholder='[{"eventName": "Rock Fest", "locationId": 1, ...}]'
                        value={jsonInput}
                        onChange={(e) => setJsonInput(e.target.value)}
                        style={{ fontSize: '14px', backgroundColor: '#f8f9fa' }}
                    />

                    <div className="d-flex justify-content-between">
                        <button className="btn btn-outline-secondary" onClick={() => setJsonInput('')}>
                            Clear Text
                        </button>
                        <button className="btn btn-primary px-5" onClick={handlePublish}>
                            Publish Events
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default CreateEventApi;