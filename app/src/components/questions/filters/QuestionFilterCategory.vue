<template>
    <div class="QuestionFilterCategory">
        <UISelect
                @onChange="onChange"
                :options="categories"
                :value="filters.category"
                full />
    </div>
</template>

<script>
    import UISelect from '../../common/UISelect';
    import categoryService from '../../../services/CategoryService';
    import {mapGetters, mapActions} from 'vuex';

    export default {
        name: 'QuestionFilterCategory',
        components: {UISelect},
        computed: {
            ...mapGetters('question', ['filters']),
        },
        data() {
            return {
                categories: [],
            };
        },
        async created() {
            this.categories = [
                {id: 0, name: 'All lectures'},
                ... await categoryService.listCategories(),
            ];
        },
        methods: {
            ...mapActions('question', ['updateCategory', 'updateQuestionList']),
            onChange(category) {
                this.updateCategory(category.id === 0 ? null : category);
            },
        },
    };
</script>

<style lang="scss" scoped>
    .QuestionFilterCategory {
        margin-bottom: 10px;
    }
</style>
