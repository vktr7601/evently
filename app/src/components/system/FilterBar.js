
const FilterBar = ({ 
    filters, 
    onFilterChange, 
    onReset,
    // Configuration props (defaults to false)
    showName = false,
    showCategory = false,
    showLocation = false,
    showDateRange = false,
    categories = [] // Pass categories as an array
}) => {

    const styles = {
        miniLabel: {
            fontSize: '0.75rem',
            fontWeight: '700',
            textTransform: 'uppercase',
            color: '#6c757d',
            marginBottom: '5px',
            display: 'block'
        },
        input: {
            height: '45px',
            borderRadius: '8px',
            border: '1px solid #dee2e6'
        },
        button: {
            height: '45px',
            borderRadius: '8px',
            fontWeight: '600',
            padding: '0 25px'
        }
    };

    return (
        <div className="container my-4 p-4 bg-white rounded shadow-sm border">
            <form className="row g-3 align-items-end">
                
                {/* Conditional Name Filter */}
                {showName && (
                    <div className="col-md">
                        <label style={styles.miniLabel}>Search</label>
                        <input
                            type="text"
                            name="name"
                            className="form-control"
                            style={styles.input}
                            placeholder="Search by name..."
                            value={filters.name}
                            onChange={(e) => onFilterChange('name', e.target.value)}
                        />
                    </div>
                )}

                {/* Conditional Category Filter */}
                {showCategory && (
                    <div className="col-md">
                        <label style={styles.miniLabel}>Category</label>
                        <select
                            name="category"
                            className="form-select"
                            style={styles.input}
                            value={filters.category}
                            onChange={(e) => onFilterChange('category', e.target.value)}
                        >
                            <option value="">All Genres</option>
                            {categories.map(cat => (
                                <option key={cat.id || cat} value={cat.id || cat}>
                                    {cat.name || cat}
                                </option>
                            ))}
                        </select>
                    </div>
                )}

                {/* Conditional Location Filter */}
                {showLocation && (
                    <div className="col-md">
                        <label style={styles.miniLabel}>Location</label>
                        <input
                            type="text"
                            name="location"
                            className="form-control"
                            style={styles.input}
                            placeholder="City or Venue"
                            value={filters.location}
                            onChange={(e) => onFilterChange('location', e.target.value)}
                        />
                    </div>
                )}

                {/* Conditional Date Range Filter */}
                {showDateRange && (
                    <>
                        <div className="col-md">
                            <label style={styles.miniLabel}>From</label>
                            <input
                                type="date"
                                className="form-control"
                                style={styles.input}
                                value={filters.startDate}
                                onChange={(e) => onFilterChange('startDate', e.target.value)}
                            />
                        </div>
                        <div className="col-md">
                            <label style={styles.miniLabel}>To</label>
                            <input
                                type="date"
                                className="form-control"
                                style={styles.input}
                                value={filters.endDate}
                                onChange={(e) => onFilterChange('endDate', e.target.value)}
                            />
                        </div>
                    </>
                )}

                {/* Reset Button (Always visible or optional) */}
                <div className="col-md-auto">
                    <button
                        type="button"
                        className="btn btn-outline-secondary"
                        style={styles.button}
                      //  onClick={onReset}
                    >
                        Reset
                    </button>
                </div>
            </form>
        </div>
    );
};

export default FilterBar;