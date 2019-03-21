import Axios from 'axios';

const API_URL = 'http://localhost:8080/';

const axios = Axios.create({
    baseURL: API_URL
});

async function request(config) {
    const requestConfig = {...config};
    if (!requestConfig.headers) {
        requestConfig.headers = {};
    }

    // For future usage.
    if (localStorage.authToken) {
        requestConfig.headers.authorization = localStorage.authToken;
    }

    try {
        const response = await axios.request(requestConfig);
        return response.data;
    } catch (err) {
        // TODO: Log error to sentry
        throw {
            error: 'Request failed',
            message: err
        }
    }
}

export default {
    API_URL,

    get(url, config) {
        return request({
            ...config,
            method: 'GET',
            url
        });
    },

    post(url, data, config) {
        return request({
            ...config,
            method: 'POST',
            url,
            data
        });
    },

    put(url, data, config) {
        return request({
            ...config,
            method: 'PUT',
            url,
            data
        });
    },
}
