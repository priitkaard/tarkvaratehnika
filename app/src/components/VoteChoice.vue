<template>
<div>
<div class = "voteUp" v-on:click="voteUp"> <img src="vue-router\src\assets\arrowup.png" width="25" height="25"/> </div></br>
<div class = "voteDown" v-on:click="voteDown"><img src="vue-router\src\assets\arrowdown.png" width="25" height="25"/></div>
</div>
</template>
<script>
export default {
    name: 'VoteChoice',
    methods: {
        voteUp: function (event) {
        alert(this.id)
        },
        voteDown: function (event) {
            alert(this.id)
        }
    },
    props: ['id', 'type']
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