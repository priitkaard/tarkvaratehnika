import Axios from 'axios';
import { LOCAL_STORAGE, storageGet } from './StorageService';

const API_URL =
    process.env.NODE_ENV === 'production'
        ? 'http://3.82.117.200:8080/'
        : 'http://127.0.0.1:8080/';

const axios = Axios.create({
    baseURL: API_URL,
});

async function request(config) {
    const requestConfig = { ...config };
    if (!requestConfig.headers) {
        requestConfig.headers = {};
    }

    const authToken = storageGet(LOCAL_STORAGE.AUTH_TOKEN);
    if (authToken) {
        requestConfig.headers.authorization = authToken;
    }

    try {
        const response = await axios.request(requestConfig);
        return response.data;
    } catch (err) {
        handleErrors(err);
    }
}

export function handleErrors(err) {
    // TODO: Log error to sentry
    if (err.response.status === 400) {
        const responseBody = err.response.data;
        if (!responseBody) {
            throw { error: 'Request failed' };
        }
        if (responseBody.errors && responseBody.errors.length) {
            throw { error: responseBody.errors[0].defaultMessage };
        } else if (responseBody.message) {
            throw { error: responseBody.message };
        }
    }
    throw { error: 'Technical error' };
}

export default {
    API_URL,

    get(url, config) {
        return request({
            ...config,
            method: 'GET',
            url,
        });
    },

    post(url, data, config) {
        return request({
            ...config,
            method: 'POST',
            url,
            data,
        });
    },

    put(url, data, config) {
        return request({
            ...config,
            method: 'PUT',
            url,
            data,
        });
    },
};
