<template>
    <div class="AddQuestionView container">
        <div class="row">
            <div class="col-lg-6">
                <img class="AddQuestionView__image" src="../assets/img/question-mark.png" />
            </div>
            <div class="col-lg-6">
                <div class="AddQuestionView__box">
                    <input class="form-control AddQuestionView__input_title" placeholder="Question title" type="text" />
                    <ckeditor :editor="editor" v-model="editorData" :config="editorConfig"></ckeditor>
                    <div class="row AddQuestionView__input_group">
                        <div class="col-sm-5">
                            <UISelect :options="categories" />
                        </div>
                        <div class="col-sm-2"></div>
                        <div class="col-sm-5">
                            <UIButton text="Post question" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
    import UISelect from '../components/UISelect';
    import UIButton from '../components/UIButton';

    export default {
        name: "AddQuestionView",
        components: {
            UISelect,
            UIButton
        },
        data() {
            return {
                editor: ClassicEditor,
                editorData: '',
                editorConfig: {
                    // The configuration of the rich-text editor.
                    removePlugins: [ 'Heading', 'Link' ],
                    toolbar: [ 'bold', 'italic', 'bulletedList', 'numberedList', 'blockQuote' ],
                    height: 500
                },
                categories: [
                    {
                        value: 1, // should be ID
                        text: 'Tarkvaratehnika'
                    },
                    {
                        value: 2,
                        text: 'Arvutivõrgud'
                    }
                ]
            }
        }
    }
</script>

<style lang="scss" scoped>

    .AddQuestionView {
        &__image {
            width: 50%;
            margin-left: 25%;
        }
        &__box {
            display: block;
            margin: 100px 0;
            background-color: white;
            box-shadow: 0 0 100px rgba(0, 0, 0, 0.2);
            padding: 40px 30px;
        }
        &__input_title {
            margin-bottom: 20px;
        }
        &__input_group {
            margin-top: 20px;

            .UISelect {
                text-align: center;
                width: 100%;
                margin-bottom: 20px;
            }
            .UIButton {
                width: 100%;
                text-align: right;
            }
        }
    }
</style>