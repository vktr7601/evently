import axios from 'axios';
import { ROUTES } from '../constants/routes';

const axiosClient = axios.create({
  baseURL: ROUTES.BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

axiosClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('jwtToken');
  
  if (token && !config.noAuth) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default axiosClient;