<template>
    <div class="QuestionFilterCategory">
        <UISelect
                v-on:select-changed="onChange"
                :options="categories"
                :value="filters.categoryId" />
    </div>
</template>

<script>
    import UISelect from "../../UISelect";
    import categoryService from '../../../services/CategoryService';
    import {mapGetters, mapActions} from 'vuex';

    export default {
        name: "QuestionFilterCategory",
        components: {UISelect},
        computed: {
            ...mapGetters('question', ['filters'])
        },
        data() {
            return {
                categories: []
            }
        },
        async created() {
            this.categories = [
                {id: 0, name: 'All lectures'},
                ... await categoryService.listCategories()
            ];
        },
        methods: {
            ...mapActions('question', ['updateCategoryId']),
            onChange(category) {
                this.updateCategoryId(category.id);
            }
        }
    }
</script>

<style lang="scss" scoped>
    .QuestionFilterCategory {
        margin-bottom: 10px;
    }
</style>