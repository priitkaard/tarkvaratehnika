<template>
    <div>
        <div class = "commentButton"><button type="button" @click = "openComment()">Comment</button></div>
        <div class = "formArea" v-if="this.toggled === true">
           <ckeditor
                :editor="editor.type"
                v-model="txt1"
                :config="editor.config"> 
            </ckeditor>
            <br>
            <button @click="$emit('postCmnt', txt1, id)"> POST </button>
        </div>
    </div>
</template>
<script>
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
export default {
    name: 'CommentSection',
    props:['id'],
    data() {
        return {
            toggled:false,
            txt1: '',
             editor: {
                type: ClassicEditor,
                config: {
                    removePlugins: [ 'Heading', 'Link' ],
                    toolbar: []
                }
    }
        }
    },
    methods: {
        openComment() {
            this.toggled = ! this.toggled
        }
    },
   
    
}
</script>
<style scoped>
.formArea
{
    margin-top:2%;
    margin-left: 6%;
    margin-right: 2%;
}
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
