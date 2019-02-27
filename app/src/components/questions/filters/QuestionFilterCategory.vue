<template>
    <div class="QuestionFilterCategory">
        <UISelect
                v-on:selected-changed="onChange"
                :options="categories" />
    </div>
</template>

<script>
    import UISelect from "../../UISelect";
    import questionService from '../../../services/QuestionsService';

    export default {
        name: "QuestionFilterCategory",
        components: {UISelect},
        data() {
            return {
                currentCategory: 0,
                categories: []
            }
        },
        async created() {
            this.categories = [
                {id: 0, name: 'All lectures'},
                ... await questionService.getQuestionCategories()
            ]
        },
        methods: {
            onChange(category) {
                if (this.currentCategory !== category.id) {
                    this.$emit('category-changed', category.id);
                    this.currentCategory = category.id;
                }
            }
        }
    }
</script>

<style lang="scss" scoped>
    .QuestionFilterCategory {
        margin-bottom: 10px;
    }
</style>