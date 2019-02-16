<template>
    <div class="Navigation">
        <QuestionSearch/>
    </div>
</template>

<script>
    import QuestionSearch from "../Searchbar";

    export default {
        name: "Navigation",
        components: {QuestionSearch}
    }
</script>

<style scoped>
    .Navigation {
        display: flex;
        width: 100%;
        height: 50px;
        background-color: black;
        justify-content: center;
        align-items: center;

        position: fixed;
        top: 0;
        z-index: 5;
    }
</style>