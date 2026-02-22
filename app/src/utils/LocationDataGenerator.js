const VENUES = [
    {
        name: 'НДК – Национален дворец на културата',
        description: 'Iconic multi-purpose cultural centre in central Sofia with a capacity of over 3,800 seats across 15 halls. Host to international concerts, film festivals, and state ceremonies. Located at pl. Bulgaria 1, Sofia.'
    },
    {
        name: 'Sofia Live Club',
        description: 'Premier indoor music venue in the heart of Sofia with a capacity of 1,500. State-of-the-art acoustics and lighting, regularly hosting international artists across all genres. Located at 4 Arsenal Street, Sofia.'
    },
    {
        name: 'Арена Армеец',
        description: 'Bulgaria\'s largest indoor arena with a capacity of up to 12,000 spectators. Home to major sporting events, concerts, and entertainment shows. Located at 1 Arsenal Street, Sofia.'
    },
    {
        name: 'Античен театър – Пловдив',
        description: 'Ancient Roman theatre dating back to the 2nd century AD, seating approximately 6,000 guests. One of the best-preserved ancient theatres in the world, offering an extraordinary open-air experience in Plovdiv\'s Old Town.'
    },
    {
        name: 'Летен театър – Варна',
        description: 'Open-air summer theatre in the Sea Garden of Varna with a capacity of 3,000. Hosts the prestigious Varna Summer International Theatre Festival and a variety of live performances from May to September.'
    },
    {
        name: 'Зала 1 – НДК',
        description: 'The grand main hall of the National Palace of Culture in Sofia, seating 3,864. Equipped with world-class audio and visual systems, ideal for orchestral concerts, operas, and major cultural events.'
    },
    {
        name: 'Музикален театър – Бургас',
        description: 'Modern performance venue on the Burgas Black Sea coast with a capacity of 800. Hosts ballet, opera, musicals, and stand-up performances. Features an outdoor summer stage overlooking the sea.'
    },
    {
        name: 'Пиаца – Стадион Васил Левски',
        description: 'Outdoor festival ground adjacent to Vasil Levski National Stadium in Sofia, accommodating up to 25,000 attendees. Hosts major open-air concerts and national celebration events.'
    },
    {
        name: 'Club Yalta – Sofia',
        description: 'Underground club venue with an intimate atmosphere, capacity of 600. Known for electronic music nights, underground DJs, and alternative cultural events. Located beneath Sofia\'s city centre.'
    },
    {
        name: 'Зала „Универсиада" – София',
        description: 'Multifunctional sports and events hall in Sofia with a seating capacity of 5,000. Hosts international competitions, concerts, and large-scale corporate events. Located in the Student Town area.'
    },
];

const pick = (arr) => arr[Math.floor(Math.random() * arr.length)];
const randInt = (min, max) => Math.floor(Math.random() * (max - min + 1)) + min;

const futureDate = (daysFromNow) => {
    const d = new Date();
    d.setDate(d.getDate() + daysFromNow);
    d.setHours(randInt(17, 21), 0, 0, 0);
    return d.toISOString().slice(0, 16); // datetime-local format
};

class LocationDataGenerator {
    /**
     * Generate a random Bulgarian venue location.
     */
    static generate() {
        const venue = pick(VENUES);
        return {
            name: venue.name,
            description: venue.description,
        };
    }

    /**
     * Generate a single event assignment row with a future date.
     */
    static generateEventAssignment(eventId = '') {
        return {
            eventId,
            eventDate: futureDate(randInt(7, 180)),
            tickets: randInt(100, 5000),
            price: randInt(15, 150),
        };
    }
}

export default LocationDataGenerator;
