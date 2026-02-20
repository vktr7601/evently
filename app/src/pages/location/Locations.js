import Hero from '../../components/system/Hero';
import LocationsList from '../../components/locations/LocationsList';

const Locations = () => {
    return (
        <div>
            <Hero
                badge="📍 Explore the Map"
                title="Iconic Locations &"
                highlight="Hidden Gems."
                subtitle={<>From <span className="text-dark fw-medium">underground clubs</span> to <span className="text-dark fw-medium">open-air stadiums</span>. Find the perfect atmosphere for your next outing.</>}
                primaryAction={{ text: "Search Venues", link: "/locations" }}
                secondaryAction={{ text: "Add a Venue", link: "/contact" }}
            />

            <LocationsList />
        </div>
    );
};

export default Locations;