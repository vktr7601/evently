const FIRST_NAMES = ['James', 'Emma', 'Liam', 'Olivia', 'Noah', 'Ava', 'William', 'Sophia', 'Benjamin', 'Isabella'];
const LAST_NAMES  = ['Smith', 'Johnson', 'Williams', 'Brown', 'Jones', 'Garcia', 'Miller', 'Davis', 'Wilson', 'Moore'];
const DOMAINS     = ['gmail.com', 'yahoo.com', 'outlook.com', 'hotmail.com'];

const pick = (arr) => arr[Math.floor(Math.random() * arr.length)];
const pickMany = (arr, count) => [...arr].sort(() => 0.5 - Math.random()).slice(0, count);
const randInt = (min, max) => Math.floor(Math.random() * (max - min + 1)) + min;

class RegisterDataGenerator {
    /**
     * Generate a fake regular user payload.
     * @param {Array} categories - category objects from the API (optional)
     * @param {Array} locations  - location objects from the API (optional)
     */
    static generateUser(categories = [], locations = []) {
        const firstName = pick(FIRST_NAMES);
        const lastName  = pick(LAST_NAMES);
        const password  = `Pass${randInt(1000, 9999)}!`;

        return {
            firstName,
            lastName,
            age: String(randInt(18, 60)),
            email: `${firstName.toLowerCase()}.${lastName.toLowerCase()}${randInt(1, 99)}@${pick(DOMAINS)}`,
            password,
            confirmPassword: password,
            eventsCategories: categories.length ? pickMany(categories, randInt(1, Math.min(3, categories.length))) : [],
            locations:        locations.length  ? pickMany(locations,  randInt(1, Math.min(3, locations.length)))  : [],
            subscribeNewsletter: Math.random() > 0.5,
        };
    }

    /**
     * Generate a fake admin payload.
     * Email is forced to the @admin.evently.com domain.
     */
    static generateAdmin() {
        const firstName = pick(FIRST_NAMES);
        const lastName  = pick(LAST_NAMES);
        const password  = `Admin${randInt(1000, 9999)}!`;

        return {
            firstName,
            lastName,
            age: String(randInt(25, 55)),
            email: `${firstName.toLowerCase()}.${lastName.toLowerCase()}@admin.evently.com`,
            password,
            confirmPassword: password,
            eventsCategories: [],
            locations: [],
            subscribeNewsletter: false,
        };
    }
}

export default RegisterDataGenerator;
