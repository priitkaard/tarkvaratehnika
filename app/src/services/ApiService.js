import Axios from 'axios';

const API_URL = 'http://localhost:8080/';

const axios = Axios.create({
    baseUrl: API_URL
});

function request(config) {
    const requestConfig = {...config};
    if (!requestConfig.headers) {
        requestConfig.headers = {};
    }

    // For future usage.
    if (localStorage.authToken) {
        requestConfig.headers.authorization = `Bearer ${localStorage.authToken}`;
    }

    return axios.request(requestConfig);
}

export default {
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
    }
}
