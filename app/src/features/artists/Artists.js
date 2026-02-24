import ArtistList from "./ArtistList";
import Hero from "../../components/layout/Hero";
const Artists = () => {
    return (
        <div>
            <Hero
                badge="🎸 Meet the Talent"
                title="World Class"
                highlight="Performers."
                subtitle={<>From rising indie stars to <span className="text-dark fw-medium">legendary headliners</span>. Discover the artists making waves in the industry today.</>}
                primaryAction={{ text: "View All Artists", link: "/performers" }}
                secondaryAction={{ text: "Join as Artist", link: "/contact" }}
            />
            <ArtistList />
        </div>
    )
};

export default Artists;