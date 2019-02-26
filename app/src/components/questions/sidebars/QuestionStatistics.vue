<template>
    <div class="QuestionStatistics sidebar">
        <header>Statistics</header>

        <wrench-outline-icon :size="50" />
        <p>Under development</p>
    </div>
</template>

<script>
    import WrenchOutlineIcon from "vue-material-design-icons/WrenchOutline";
    export default {
        name: "QuestionStatistics",
        components: {WrenchOutlineIcon}
    }
</script>

<style lang="scss" scoped>
    .QuestionStatistics {
        text-align: center;
        p {
            padding-bottom: 20px;
            font-weight: bold;
        }
    }
</style>