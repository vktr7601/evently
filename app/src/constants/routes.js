export const ROUTES = {
  BASE_URL: 'http://localhost:9000',
  EVENTS: {
    BASE: '/events',
    ADMIN_CREATE: '/admin/events',
    ADMIN_EDIT: (id) => `/admin/events/${id}/edit`,
    DETAILS: (id) => `/events/${id}`,
    EVENT_LOCATIONS_DETAILS: (id) => `/event-details/${id}`,
  },
  ORDERS: {
    BASE: '/orders',
    ADMIN: '/admin/orders',
    CANCEL: '/orders/cancel',
  },
  NOTIFICATIONS:{
    BASE: '/notifications',
  }, 
  AUTH: {
    REGISTER: '/register',
    LOGIN: '/login',
    LOGOUT: '/logout',
    PROFILE: '/profile',
  },
  LOCATIONS: {
    BASE: '/locations',
    ADMIN_CREATE: '/admin/location/create',
    DETAILS: (id) => `/locations/${id}`,
  },
  ARTISTS: {
    BASE: '/artists',
    DETAILS: (id) => `/artists/${id}`,
  },
};