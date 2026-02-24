import { useState } from "react";
import EventList from "./EventList";
import Hero from "../../components/layout/Hero";

export const Events = () => {
    const [filters, setFilters] = useState({
        name: "",
        category: "",
        startDate: "",
        endDate: "",
        location: ""
    });

    const handleFilterChange = (e) => {
        const { name, value } = e.target;
        setFilters(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const resetFilters = () => {
        setFilters({
            name: "",
            category: "",
            startDate: "",
            endDate: "",
            location: ""
        });
    };

    return (
        <div>
            {/* ONLY ONE HERO COMPONENT */}
            <Hero
                badge="📅 Don't Miss Out"
                title="Epic Moments"
                highlight="Happening Now."
                subtitle={
                    <>
                        From sold-out <span className="text-dark fw-medium">stadium concerts</span> to exclusive
                        <span className="text-dark fw-medium"> underground sets</span>. Secure your spot at the
                        most anticipated events of the season.
                    </>
                }
                primaryAction={{ text: "Explore All Events", link: "/events" }}
                secondaryAction={{ text: "Calendar View", link: "/calendar" }}
            />

            <div className="container my-5 p-4 bg-light rounded shadow-sm">
                <form className="row g-3 align-items-end">
                    <div className="col-md-3">
                        <label className="form-label fw-bold small">Search Event</label>
                        <input
                            type="text"
                            name="name"
                            className="form-control"
                            placeholder="Artist or event name..."
                            value={filters.name}
                            onChange={handleFilterChange}
                        />
                    </div>

                    <div className="col-md-2">
                        <label className="form-label fw-bold small">Category</label>
                        <select
                            name="category"
                            className="form-select"
                            value={filters.category}
                            onChange={handleFilterChange}
                        >
                            <option value="">All Categories</option>
                            <option value="concert">Concerts</option>
                            <option value="sports">Sports</option>
                            <option value="theater">Theater</option>
                        </select>
                    </div>

                    <div className="col-md-2">
                        <label className="form-label fw-bold small">Location</label>
                        <input
                            type="text"
                            name="location"
                            className="form-control"
                            placeholder="City or Venue"
                            value={filters.location}
                            onChange={handleFilterChange}
                        />
                    </div>

                    <div className="col-md-3">
                        <label className="form-label fw-bold small">Date Range</label>
                        <div className="input-group">
                            <input
                                type="date"
                                name="startDate"
                                className="form-control"
                                value={filters.startDate}
                                onChange={handleFilterChange}
                            />
                            <input
                                type="date"
                                name="endDate"
                                className="form-control"
                                value={filters.endDate}
                                onChange={handleFilterChange}
                            />
                        </div>
                    </div>

                    {/* Buttons wrapped in a column to stay in the grid */}
                    <div className="col-md-2">
                        <div style={styles.buttonGroup}>
                            <button
                                type="button"
                                style={styles.secondaryButton}
                                onClick={resetFilters}
                            >
                                Reset
                            </button>
                               <button
                                type="button"
                                style={styles.secondaryButton}
                               // onClick={resetFilters}
                            >
                                Apply
                            </button>
                        </div>
                    </div>
                </form>
            </div>

            {/* CRITICAL: Pass the filters prop to your list */}
            <EventList/>
        </div>
    );
}

const styles = {
    primaryButton: {
        height: '38px', // Bootstrap default height is roughly 38px
        backgroundColor: '#026cdf',
        color: 'white',
        border: 'none',
        borderRadius: '6px',
        fontWeight: '600',
        padding: '0 15px',
        cursor: 'pointer',
        width: '100%'
    },
    secondaryButton: {
        height: '38px',
        backgroundColor: '#f8f9fa',
        color: '#6c757d',
        border: '1px solid #dee2e6',
        borderRadius: '6px',
        fontWeight: '500',
        padding: '0 15px',
        cursor: 'pointer',
        width: '100%'
    },
    buttonGroup: {
        display: 'flex',
        gap: '5px',
    }
};

export default Events;