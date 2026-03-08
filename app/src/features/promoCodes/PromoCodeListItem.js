const PromoCodeListItem = ({ promoCode }) => {
    const isActive = promoCode.active;

    const statusBadgeClass = isActive
        ? 'bg-success-subtle text-success'
        : 'bg-danger-subtle text-danger';

    const discountDisplay = promoCode.discountType === 'Percentage'
        ? `${promoCode.discountPercentage}%`
        : `$${promoCode.discountPercentage}`;

    return (
        <div className={`list-group-item border-0 border-bottom px-3 py-2 ${isActive ? 'bg-white' : 'bg-light'}`}>
            <div className="d-flex align-items-center justify-content-between">
                <div className="d-flex align-items-center gap-2 overflow-hidden">
                    <span className={`badge rounded-pill ${statusBadgeClass}`}>
                        {promoCode.status}
                    </span>
                    <span className={`text-truncate ${isActive ? 'text-dark' : 'text-secondary'}`}>
                        {promoCode.promoCode}
                    </span>
                </div>
                <span className={`fw-bold ms-3 flex-shrink-0 ${isActive ? 'text-dark' : 'text-muted'}`}>
                    {discountDisplay} off
                </span>
            </div>
        </div>
    );
};

export default PromoCodeListItem;
