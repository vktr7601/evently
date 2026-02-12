import EventList from "../../components/event/EventList";
import Hero from "../../components/system/Hero";

export const Events = () => {
    return (
        <div>
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
            <EventList />
        </div>
    );
}

export default Events;