<template>
    <div class = "commentButton">
        <button type="button" v-on:click="postComment">Comment</button>
    </div>
</template>
<script>
export default {
    methods: {
        postComment: function (event){
            alert(this.id)
        }
    },
    props: ['id']
}
</script>
<style> 
.commentButton
{
    margin-top: 2.5%;
    display: flex;
    justify-content: flex-end;
    margin-right: 7%
   
}
button{
    background: rgb(250, 129, 0);
    width: 165px;
    height: 41px;
    mix-blend-mode: normal;
    color: rgb(255, 255, 255);
    font-size: 14px;
    text-align: center;
    font-weight: bold;
    font-style: normal;
    border-radius: 0px;
    border-width: 0px;
    border-style: solid;
}
</style>