<template>
<div>
<div class = "voteUp" v-on:click="voteUp"> <img src="../assets\img\arrowup.png" width="25" height="25"/> </div>
</br>{{score}}
<div class = "voteDown" v-on:click="voteDown"><img src="../assets\img\arrowdown.png" width="25" height="25"/></div>
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
    props: ['id', 'type', 'score']
}
</script>
<style scoped>
    div{
        color:green;
        margin-top: 1%;
        margin-left: 2%;
        display:inline-block;
    }
    .voteUp, .voteDown{
    display:inline-block;
    }
</style>