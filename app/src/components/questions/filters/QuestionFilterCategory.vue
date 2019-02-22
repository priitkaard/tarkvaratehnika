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
                categories: [
                    'All lectures'
                ]
            }
        },
        async created() {
            this.categories = await questionService.getQuestionCategories();
        }
    }
</script>

<style lang="scss" scoped>
    .QuestionFilterCategory {
        margin-bottom: 10px;
    }
</style>