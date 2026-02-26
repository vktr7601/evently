export const ROUTES = {
  BASE_URL: 'http://localhost:9000',
  EVENTS: {
    BASE: '/events',
    ADMIN_CREATE: '/admin/events',
    ADMIN_EDIT: (id) => `/admin/events/${id}/edit`,
    DETAILS: (id) => `/events/${id}`,
    EVENT_LOCATIONS_DETAILS: (id) => `/event-details/${id}`,
    EVENT_LOCATION: (id) => `/events/${id}/location`
  },
  TICKETS: {
    AVAILABILITY: '/tickets/availability',
  },
  ORDERS: {
    BASE: '/orders',
    ORDERS_CONFIRM: '/orders/confirm',
    ACTIVE: '/orders/active',
    ADMIN: '/admin/orders',
    CANCEL: '/orders/cancel',
    DETAILS: (number) => `/orders/details/${number}`,
  },
  NOTIFICATIONS:{
    BASE: '/notifications',
  }, 
  AUTH: {
    REGISTER: '/register',
    USER_REGISTER: '/user/register',
    LOGIN: '/login',
    LOGOUT: '/logout',
    PROFILE: '/user/profile',
  },
  LOCATIONS: {
    BASE: '/locations',
    ADMIN_CREATE: '/admin/location/create',
    DETAILS: (id) => `/locations/${id}`,
  },
  CATEGORIES: {
    BASE: '/categories',
    // ADMIN_CREATE: '/admin/category/create',
    // DETAILS: (id) => `/categories/${id}`,
  },
  ARTISTS: {
    BASE: '/artists',
    DETAILS: (id) => `/artists/${id}`,
    FOLLOW: (id) => `/follows/artist/${id}`,
    STATUS: (id) => `/follows/artist/${id}/status`,
  },
};