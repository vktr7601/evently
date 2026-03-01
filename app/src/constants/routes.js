export const ROUTES = {
  BASE_URL: 'http://localhost:9000',
  EVENTS: {
    BASE: '/events',
    ADMIN_CREATE: '/admin/events',
    ADMIN_BULK_CREATE: '/admin/events/bulk',
    ADMIN_EDIT_EXISTING: (id) => `/admin/events/${id}`,
    ADMIN_EDIT: (id) => `/admin/events/${id}/edit`,
    DETAILS: (id) => `/events/${id}`,
    EVENT_LOCATIONS_DETAILS: (id) => `/event-details/${id}`,
    EVENT_LOCATION: (id) => `/events/${id}/location`
  },
  TICKETS: {
    AVAILABILITY: '/tickets/availability',
    VIEW: (id) => `/tickets/view/${id}`,
    PDF: (id) => `/tickets/view/${id}/pdf`,
    USER: '/tickets',
  },
  REFUNDS: {
    TICKET_ELIGIBILITY: (id) => `/refunds/tickets/${id}/eligible`,
    TICKET_REFUND: (id) => `/refunds/tickets/${id}`,
    ORDER_ELIGIBILITY: (id) => `/refunds/orders/${id}/eligible`,
    ORDER_REFUND: (id) => `/refunds/orders/${id}`,
  },
  ORDERS: {
    BASE: '/orders',
    ORDERS_CONFIRM: '/orders/complete',
    ACTIVE: '/orders/active',
    ADMIN: '/admin/orders',
    CANCEL: '/orders/cancel',
    DETAILS: (number) => `/orders/details/${number}`,
  },
  NOTIFICATIONS: {
    BASE: '/notifications',
    SPECIFIC: (id) => `/notifications/${id}`,
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
    ADMIN_CREATE: '/admin/location',
    DETAILS: (id) => `/locations/${id}`,
  },
  CATEGORIES: {
    BASE: '/categories',
    // ADMIN_CREATE: '/admin/category/create',
    // DETAILS: (id) => `/categories/${id}`,
  },
  ARTISTS: {
    BASE: '/artists',
    ADMIN_CREATE: '/admin/artist',
    DETAILS: (id) => `/artists/${id}`,
    FOLLOW: (id) => `/follows/artist/${id}`,
    STATUS: (id) => `/follows/artist/${id}/status`,
  },
};