import axios from 'axios';

export const instance = axios.create({
    baseURL: 'http://127.0.0.1:3000',
    timeout: 1000,
});

instance.interceptors.request.use(

)

instance.interceptors.response.use(

)