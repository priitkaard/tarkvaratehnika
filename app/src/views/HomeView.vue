<template>
    <div class="HomeView" :style="{ backgroundImage: 'url(' + require('../assets/img/hero-image.jpg') + ')' }">
        <div class="HomeView__header">
            <h1>TUTKit</h1>
            <p>Answers to your education questions</p>
        </div>

        <QuestionSearch/>

        <button v-on:click="$router.push({name: 'QuestionsListView'})"
                class="HomeView__browse_button">Browse questions
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
        background-size: cover;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-content: center;
        align-items: center;
        color: white;
        position: absolute;
        top: 0;

        &__header {
            text-align: center;
            h1 {
                font-size: 60px;
            }
            p {
                font-size: 12px;
            }
        }

        &__browse_button {
            height: 40px;
            background-color: rgba(255, 255, 255, 0.9);
            outline-color: transparent;
            border: none;
            padding: 0 20px;
            border-radius: 20px;
            margin-top: 20px;
            transition: background-color 0.4s;

            &:hover {
                background-color: white;
                transition: background-color 0.4s;
            }
        }
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
        border-radius: 20px;
        padding-left: 15px;
        padding-right: 15px;
        font-size: 14px;
        background-color: rgba(255, 255, 255, 0.9);
        &:hover {
            background-color: rgba(255, 255, 255, 1);
        }
    }
</style>
