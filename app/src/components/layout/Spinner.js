import '../../assets/Spinner.css';

const Spinner = ({message}) => {
    return (
        <div className="full-page-overlay">
            <div className="spinner-wrapper">
                <svg className="spinner-svg" viewBox="0 0 50 50">
                    <circle
                        className="spinner-path"
                        cx="25"
                        cy="25"
                        r="20"
                        fill="none"
                        stroke="currentColor"
                        strokeWidth="4"
                    />
                </svg>
                {message && <div className="loading-text">{message}</div>}
            </div>
        </div>
    );
};

export default Spinner;