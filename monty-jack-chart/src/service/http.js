import axios from 'axios'

// Interceptor for responses
const responseInterceptor = response => {
    switch (response.status) {
        case 200:
            break;
        default:
    }

    return response;
};


const baseConfig = async (config) => {
    config.baseURL = "http://localhost:8082/";
    return config;
};
const httpClient = axios.create({
    baseURL: "http://localhost:8082/",
    headers: {
        "Content-Type": "application/json",
    },
});

httpClient.interceptors.request.use(baseConfig);
httpClient.interceptors.response.use(responseInterceptor);

export default httpClient;
