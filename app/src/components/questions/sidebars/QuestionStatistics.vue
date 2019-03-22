<template>
    <div class="QuestionStatistics sidebar">
        <div class="header">
            <div><chart-bar-icon :size="30  " /></div>
            <div>Statistics</div>
        </div>
        <div class="statisticsListing mt-1 mb-2">
            <span>Total users:</span>
            <span>{{statistics.users}}</span>
        </div>
        <div class="statisticsListing">
            <span>Total questions:</span>
            <span>{{statistics.questions}}</span>
        </div>
        <div class="statisticsListing">
            <span>Total answers:</span>
            <span>{{statistics.answers}}</span>
        </div>
        <div class="statisticsListing pb-4">
            <span>Total comments:</span>
            <span>{{statistics.comments}}</span>
        </div>
    </div>
</template>

<script>
    import {mapActions, mapGetters} from "vuex";
    import ChartBarIcon from "vue-material-design-icons/ChartBar";

    export default {
        name: "QuestionStatistics",
        components: {
            ChartBarIcon,
        },
        computed: {
            ...mapGetters('question', ['statistics'])
        },
        methods: {
            ...mapActions('question', ['updateStatistics'])
        },
        created() {
            this.updateStatistics();
        }
    }
</script>

<style lang="scss" scoped>
    .QuestionStatistics {
        text-align: center;
        p {
            padding-bottom: 20px;
            font-weight: bold;
        }

        .statisticsListing{
            display: flex;
            justify-content: space-between;
            margin: 0px 15px 0px 15px;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            align-content: center;
            padding: 10px;
        }
    }
</style>
