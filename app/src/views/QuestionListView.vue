<template>
    <div class="QuestionListView container">
        <QuestionFilterBar />

        <div class="row">
            <div class="col-md-8">
                <QuestionFilterSearch />
                <QuestionsList/>
            </div>
            <div class="col-md-4">
                <QuestionStatistics />
            </div>
        </div>
    </div>
</template>

<script>
    import QuestionFilterBar from '../components/questions/filters/QuestionFilterBar';
    import QuestionsList from '../components/questions/list/QuestionsList';
    import QuestionStatistics from "../components/questions/sidebars/QuestionStatistics";
    import QuestionFilterSearch from "../components/questions/filters/QuestionFilterSearch";

    export default {
        name: "QuestionListView",
        components: {
            QuestionStatistics,
            QuestionFilterBar,
            QuestionsList,
            QuestionFilterSearch
        }
    }
</script>

<style scoped>
    .QuestionListView {
        padding-bottom: 80px;
    }
</style>