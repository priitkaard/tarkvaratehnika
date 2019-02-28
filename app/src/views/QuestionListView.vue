<template>
    <div class="QuestionListView container">
        <QuestionFilterBar v-on:sort-by-changed="updateSort" />

        <div class="row">
            <div class="col-md-8">
                <QuestionFilterSearch
                        v-on:execute-search="updateQuery"
                        :initialValue="filters.query" />
            </div>

            <div class="col-md-4">
                <QuestionFilterCategory
                        v-on:category-changed="updateCategory" />
            </div>
        </div>

        <div class="row">
            <div class="col-md-8">
                <QuestionsList :filters.sync="filters" />
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
                    query: this.query || '',
                    categoryId: 0,
                    sort: 'score',
                    direction: 'DESC'
                }
            }
        },
        methods: {
            updateSort(sort) {
                this.filters = {
                    ...this.filters,
                    sort
                }
            },
            updateQuery(query) {
                this.filters = {
                    ...this.filters,
                    query
                }
            },
            updateCategory(categoryId) {
                this.filters = {
                    ...this.filters,
                    categoryId
                }
            }
        }
    }
</script>

<style scoped>
    .QuestionListView {
        padding-bottom: 80px;
    }
</style>