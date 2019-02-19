import Vue from 'vue'

import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './assets/styles/master.scss';

import App from './App';
import router from './router'

Vue.config.productionTip = false;

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
