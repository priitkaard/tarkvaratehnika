<template>
    <div class="HomeView">
        <div class="container">
            <QuestionsSortBar />
            <div class="row">
                <div class="col-md-8">
                    <QuestionsList />
                </div>
                <div class="col-md-4">
                    <QuestionStatistics />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import QuestionsSortBar from '../components/questions/QuestionsSortBar';
    import QuestionsList from '../components/questions/QuestionsList';
    import QuestionStatistics from "../components/QuestionStatistics";

    export default {
        name: "HomeView",
        components: {
            QuestionStatistics,
            QuestionsSortBar,
            QuestionsList
        }
    }
</script>

<style scoped>
    .HomeView {
        padding-top: 80px;
        padding-bottom: 80px;
    }
</style>