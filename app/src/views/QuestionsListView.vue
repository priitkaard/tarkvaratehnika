<template>
    <div class="QuestionsListView container">
        <QuestionsSortBar/>
        <div class="row">
            <div class="col-md-8">
                <QuestionsList/>
            </div>
            <div class="col-md-4">
                <QuestionStatistics/>
            </div>
        </div>
    </div>
</template>

<script>
    import QuestionsSortBar from '../components/questions/QuestionsSortBar';
    import QuestionsList from '../components/questions/QuestionsList';
    import QuestionStatistics from "../components/questions/QuestionStatistics";

    export default {
        name: "QuestionsListView",
        components: {
            QuestionStatistics,
            QuestionsSortBar,
            QuestionsList
        }
    }
</script>

<style scoped>
    .QuestionsListView {
        padding-bottom: 80px;
    }
</style>