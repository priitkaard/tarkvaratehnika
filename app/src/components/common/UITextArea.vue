<template>
    <div class="UITextArea">
        <ckeditor :editor="editor.type"
                  v-model="input"
                  :config="editor.config">
        </ckeditor>
    </div>
</template>

<script>
    import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

    export default {
        name: "UITextArea",
        props: {
            value: String,
        },
        watch: {
            value(value) {
                this.input = value;
            },
            input(value) {
                this.$emit('update:value', value);
            },
        },
        data() {
            return {
                editor: {
                    type: ClassicEditor,
                    config: {
                        removePlugins: [ 'Heading', 'Link' ],
                        toolbar: [ 'bold', 'italic', 'bulletedList', 'numberedList', 'blockQuote' ]
                    },
                },
                input: '',
            }
        },
    }
</script>

<style scoped>
    .UITextArea {}
</style>