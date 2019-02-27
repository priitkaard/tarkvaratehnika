<template>
    <div class="QuestionListView container">
        <QuestionFilterBar v-on:sort-by-changed="updateSortBy" />

        <div class="row">
            <div class="col-md-8">
                <QuestionFilterSearch
                        v-on:execute-search="updateQuery"
                        :initialValue="filters.query" />
            </div>
            <div class="col-md-4">
                <QuestionFilterCategory
                        v-on:category-changed="updateCategory"
                        :initialValue="filters.categoryId" />
            </div>
        </div>

        <div class="row">
            <div class="col-md-8">
                <QuestionsList :filters="filters" />
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
    import QuestionFilterCategory from "../components/questions/filters/QuestionFilterCategory";

    export default {
        name: "QuestionListView",
        props: ['query'],
        components: {
            QuestionFilterCategory,
            QuestionStatistics,
            QuestionFilterBar,
            QuestionsList,
            QuestionFilterSearch
        },
        data() {
            return {
                filters: {
                    query: this.query,
                    categoryId: 3,
                    sortBy: 'score' // score DESC, count(answers) DESC, max(answer_created) DESC, views DESC, max(view_created) DESC,
                    // where count(answers) == 0 and created DESC
                }
            }
        },
        methods: {
            updateSortBy(sortBy) {
                this.filters.sortBy = sortBy;
            },
            updateQuery(query) {
                this.filters.query = query;
            },
            updateCategory(categoryId) {
                this.filters.categoryId = categoryId;
            }
        }
    }
</script>

<style scoped>
    .QuestionListView {
        padding-bottom: 80px;
    }
</style>