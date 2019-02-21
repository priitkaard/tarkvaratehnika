<template>
<div class="score">
    <menu-up-icon v-if="this.canVote" class="icon-button" :size="40" v-on:click="voteDown()"/>
    <em v-bind:class="{ positive: score > 0, negative: score < 0 }">{{score}}</em>
    <menu-down-icon v-if="this.canVote" class="icon-button" :size="40" v-on:click="voteUp()"/>
</div>
</template>
<script>
    import MenuUpIcon from "vue-material-design-icons/MenuUp.vue"
    import MenuDownIcon from "vue-material-design-icons/MenuDown.vue"
export default {
    name: 'VoteChoice',
    components:{
        MenuUpIcon,
        MenuDownIcon,
    },
    methods: {
        voteUp: function () {
            alert(this.id)
            this.score--;
            this.canVote = false;
        },
        voteDown: function () {
            alert(this.id)
            this.score++;
            this.canVote = false;
        }
    },
    props: ['id', 'type', 'score', 'canVote']
}
</script>
<style lang="scss" scoped>
    @import '../assets/styles/_colors.scss';

    .score {
            display: inline-flex;
            flex-direction: column;
            vertical-align: middle;
            text-align: center;

            em {
                font-style: normal;
                font-weight: bold;
                width: 40px;
                &.positive {
                    color: $color-green
                }
                &.negative {
                    color: $color-red
                }
            }
        }
</style>