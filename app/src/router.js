import Vue from 'vue'
import Router from 'vue-router'
import HomeView from './views/HomeView.vue'
import QuestionView from './views/QuestionView.vue'

Vue.use(Router);

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            name: 'HomeView',
            component: HomeView
        },
        {
            name: 'QuestionView',
            path: '/question/:id',
            component: QuestionView
        }
    ]
})
