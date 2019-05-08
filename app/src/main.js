import Vue from 'vue';

import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './assets/styles/master.scss';
import './assets/styles/transitions.scss';
import CKEditor from '@ckeditor/ckeditor5-vue';

import App from './App';
import router from './router';
import store from './store';

Vue.config.productionTip = false;

Vue.use(CKEditor);

new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app');
