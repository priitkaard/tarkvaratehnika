import apiService from './ApiService';
import store from '../store';

initializeUser();

export async function initializeUser() {
    await loadPoints();
}

async function loadPoints() {
    const username = store.state.auth.username;
    const points = await apiService.get(`/user/points/${username}`);
    store.commit('user/setPoints', points);
}
