<template>
    <div class="QuestionFilterCategory">
        <UISelect :options="categories" />
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
                categories: []
            }
        },
        async created() {
            this.categories = [
                {id: 0, name: 'All lectures'},
                ... await questionService.getQuestionCategories()
            ]
        }
    }
</script>

<style lang="scss" scoped>
    .QuestionFilterCategory {
        margin-bottom: 10px;
    }
</style>