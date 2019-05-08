import Vue from 'vue';
import Router from 'vue-router';
import HomeView from './views/HomeView.vue';
import QuestionDetailView from './views/QuestionDetailView.vue';
import QuestionListView from './views/QuestionListView.vue';
import AddQuestionView from './views/AddQuestionView.vue';
import { LAYOUT } from './services/LayoutService';

Vue.use(Router);

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            name: 'HomeView',
            component: HomeView,
            meta: {
                layout: LAYOUT.FULL,
            },
        },
        {
            path: '/questions/create',
            name: 'AddQuestionView',
            component: AddQuestionView,
            props: true,
        },
        {
            name: 'QuestionListView',
            path: '/questions',
            component: QuestionListView,
        },
        {
            name: 'QuestionDetailView',
            path: '/question/:id',
            component: QuestionDetailView,
        },
    ],
});
