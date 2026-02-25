const NotificationListItem = ({ data }) => {
    const { title, message, isRead, createdAt } = data;

    return (
      <div className={`list-group-item list-group-item-action border-0 border-bottom p-3 ${!isRead ? 'bg-light' : ''}`}>
    <div className="d-flex align-items-center gap-3">
        {/* Icon Column (Remains fixed on the left) */}
        <div className={`rounded-circle d-flex align-items-center justify-content-center flex-shrink-0 ${!isRead ? 'bg-primary text-white' : 'bg-white text-muted border'}`} 
             style={{ width: '40px', height: '40px' }}>
            <i className={`bi ${!isRead ? 'bi-bell-fill' : 'bi-bell'}`} style={{ fontSize: '0.9rem' }}></i>
        </div>

        {/* New Side-by-Side Content Layout */}
        <div className="d-flex flex-grow-1 align-items-center justify-content-between overflow-hidden">
            
            {/* 1. Title Section (Fixed width to keep it aligned) */}
            <div style={{ minWidth: '150px', maxWidth: '200px' }} className="flex-shrink-0">
                <h6 className={`mb-0 text-truncate ${!isRead ? 'fw-black text-dark' : 'fw-bold text-secondary'}`}>
                    {title}
                </h6>
            </div>

            {/* 2. Message Preview (Takes the remaining middle space) */}
            <div className="flex-grow-1 px-3 overflow-hidden">
                <div 
                    className="text-muted small text-truncate notification-content-inline"
                    dangerouslySetInnerHTML={{ __html: message }}
                />
            </div>

            {/* 3. Date Section (Fixed on the right) */}
            <div className="flex-shrink-0 ms-auto">
                <small className="text-muted" style={{ fontSize: '0.7rem' }}>
                    {new Date(createdAt).toLocaleDateString()}
                </small>
            </div>
        </div>
    </div>
</div>
    );
};

export default NotificationListItem;