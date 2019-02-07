import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";
import config from './config';

export const ApiService = {
    init() {
        Vue.use(VueAxios, axios);
        Vue.axios.defaults.baseURL = config.API_URL;
    }
};