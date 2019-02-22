<template>
    <div class="QuestionListView container">
        <QuestionFilterBar />

        <div class="row">
            <div class="col-md-8">
                <QuestionFilterSearch />
                <QuestionsList/>
            </div>
            <div class="col-md-4">
                <QuestionFilterCategory/>
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
    import QuestionFilterCategory from "../components/questions/filters/QuestionFilterCategory";

    export default {
        name: "QuestionListView",
        components: {
            QuestionFilterCategory,
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