import Vue from 'vue';
import Vuex from 'vuex';
import question from './modules/question';

Vue.use(Vuex);

const debug = process.env.NODE_ENV !== 'production';

export default new Vuex.Store({
    modules: {
        question
    },
    strict: debug
});
