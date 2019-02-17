<template>
    <div class = "commentButton">
        <button type="button" v-on:click="postComment">COMMENT</button>
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
    display: flex;

}
</style>