<template>
<div class="score">
    <menu-up-icon v-if="this.canVote" class="icon-button" :size="40" v-on:click="voteUp()"/>
    <em v-bind:class="{ positive: score > 0, negative: score < 0 }">{{score}}</em>
    <menu-down-icon v-if="this.canVote" class="icon-button" :size="40" v-on:click="voteDown()"/>
</div>
</template>
<script>
    import apiService from '../services/ApiService.js'
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
            if(this.type === 'post'){
                apiService.put("answers/"+this.id+"/upvote")
            }
            else{
                apiService.put("questions/"+this.id+"/upvote")
            }
            this.score++;
            this.canVote = false;
            //questionService.vote(this.id, 'UP');
        },
        voteDown: function () {
            if(this.type === 'post'){
                apiService.put("answers/"+this.id+"/downvote")
            }
            else{
                apiService.put("questions/"+this.id+"/downvote")
            }
            this.score--;
            this.canVote = false;
            //questionService.vote(this.id, 'DOWN')
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