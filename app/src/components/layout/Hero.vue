<template>
    <div class="Hero">
        <h1>Q&A Engine</h1>
        <p>Answers to your everyday questions</p>
        <QuestionSearch />
    </div>
</template>

<script>
    import QuestionSearch from '../Searchbar';

    export default {
        name: "Hero",
        components: {
            QuestionSearch
        }
    }
</script>

<style lang="scss" scoped>
    @import '../../assets/styles/_mixins.scss';
    .Hero {
        width: 100%;
        height: 100vh;
        background: url('../../assets/img/hero-image.jpg') center;
        background-size: cover;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-content: center;
        align-items: center;
        color: white;

        h1 {
            margin: 0;
        }
        p {
            margin: -10px 0 50px 0;
        }
        h1, p {
            @include shadow-box;
        }
    }
</style>