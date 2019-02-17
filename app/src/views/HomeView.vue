<template>
    <div class="HomeView" :style="{ backgroundImage: 'url(' + require('../assets/img/hero-image.jpg') + ')' }">
        <h1>Q&A Engine</h1>
        <p>Answers to your everyday questions</p>

        <QuestionSearch/>

        <em class="or">or</em>

        <button v-on:click="$router.push({name: 'QuestionsListView'})"
                class="btn btn-outline-light browse-questions-btn">Browse questions
        </button>
    </div>
</template>

<script>
    import QuestionSearch from '../components/questions/QuestionSearch';

    export default {
        name: "HomeView",
        components: {
            QuestionSearch
        }
    }
</script>

<style lang="scss" scoped>
    @import '../assets/styles/_colors.scss';
    @import '../assets/styles/_mixins.scss';

    .HomeView {
        width: 100%;
        height: 100vh;
        // background: url('../assets/img/hero-image.jpg') center;
        background-size: cover;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-content: center;
        align-items: center;
        color: white;
        position: absolute;
        top: 0;
    }

    h1 {
        margin: 0;
    }

    p {
        margin: -10px 0 50px 0;
    }

    h1, p, em.or {
        text-shadow: 0 0 2px rgba(0, 0, 0, 0.2);
    }

    .or {
        font-style: normal;
        margin: 10px 0;
    }

    .browse-questions-btn {
        box-shadow: 0 0 10px 10px rgba(0, 0, 0, 0.2);
    }
</style>
