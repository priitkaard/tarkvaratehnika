<template>
<div>
<div class = "voteUp" v-on:click="voteUp">U</div></br>
<div class = "voteDown" v-on:click="voteDown">D</div>
</div>
</template>
<script>
export default {
    name: 'VoteChoice',
    methods: {
        voteUp: function (event) {
        alert('Up')
        },
        voteDown: function (event) {
            alert('Down')
        }
    },
}
</script>
<style scoped>
    div{
        display:inline-block;
    }
    .voteUp, .voteDown{
    display:inline-block;
    background: yellow;
    }
</style>