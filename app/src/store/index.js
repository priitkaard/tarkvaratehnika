import Vue from 'vue';
import Vuex from 'vuex';
import question from './modules/question';
import navigation from './modules/navigation';
import modal from './modules/modal';
import auth from './modules/auth';
import user from './modules/user';

Vue.use(Vuex);

const debug = process.env.NODE_ENV !== 'production';

export default new Vuex.Store({
    modules: {
        question,
        navigation,
        modal,
        auth,
        user,
    },
    strict: debug,
});
