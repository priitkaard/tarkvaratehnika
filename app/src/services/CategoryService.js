import apiService from './ApiService';

export default {
    listCategories() {
        return apiService.get('category/list');
    },
};
