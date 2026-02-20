import NotificationList from '../../components/notification/NotificationList';
import Hero from '../../components/system/Hero';

const NotificationsPage = () => {
    return (
        <div className="bg-light min-vh-100">
            <Hero
                badge="🔔 Alert Center"
                title="Your"
                highlight="Notifications"
                subtitle="Stay up to date with your ticket purchases and favorite artists."
                // Add these two lines to prevent the "undefined" error
                primaryAction={{ text: "Back to Home", link: "/" }}
                secondaryAction={{ text: "Settings", link: "/settings" }}
            />

            <div className="container" style={{ marginTop: "-60px", position: "relative", zIndex: "10" }}>
                <div className="row justify-content-center">
                    <div className="col-12 col-lg-9">
                        <div className="card shadow-lg border-0 rounded-4 overflow-hidden">
                            <div className="card-header bg-white py-3 px-4 border-bottom">
                                <h5 className="fw-black mb-0">Recent Updates</h5>
                            </div>
                            <NotificationList />
                        </div>
                    </div>
                </div>
            </div>
            <div className="py-5"></div>
        </div>
    );
};

export default NotificationsPage;
