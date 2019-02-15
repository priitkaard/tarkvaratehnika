<template>
    <div class="HomePage">
        <div class="container">
            <QuestionsSortBar />
            <div class="row">
                <div class="col-md-8">
                    <QuestionsList />
                </div>
                <div class="col-md-4">
                    test2
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import QuestionsSortBar from '../components/questions/QuestionsSortBar';
    import QuestionsList from '../components/questions/QuestionsList';

    export default {
        name: "HomePage",
        components: {
            QuestionsSortBar,
            QuestionsList
        }
    }
</script>

<style scoped>
    .HomePage {
        padding-top: 80px;
        padding-bottom: 80px;
    }
</style>