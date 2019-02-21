<template>
    <div class="AddQuestionView container">
        <div class="row">
            <div class="col-lg-6">
                <img class="AddQuestionView__image" src="../assets/img/question-mark.png" />
            </div>

            <div class="col-lg-6">

                <div class="AddQuestionView__box">

                    <input class="form-control AddQuestionView__input_title"
                           placeholder="Question title"
                           type="text"
                           v-model="question.title" />

                    <ckeditor :editor="editor.type"
                              v-model="question.description"
                              :config="editor.config">
                    </ckeditor>

                    <div class="row AddQuestionView__input_group">

                        <div class="col-sm-5">
                            <UISelect :options="categories" v-on:select-changed="onCategoryChange" />
                        </div>

                        <div class="col-sm-2"></div>

                        <div class="col-sm-5">
                            <UIButton text="Post question"
                                      v-on:click="postQuestion()"/>
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
    import questionService from '../services/QuestionsService';

    export default {
        name: "AddQuestionView",
        components: {
            UISelect,
            UIButton
        },
        created() {
            this.question.title = this.$route.params.userInput;
        },
        data() {
            return {
                question: {
                    title: '',
                    description: '',
                    categoryId: null
                },
                editor: {
                    type: ClassicEditor,
                    config: {
                        removePlugins: [ 'Heading', 'Link' ],
                        toolbar: [ 'bold', 'italic', 'bulletedList', 'numberedList', 'blockQuote' ]
                    },
                },
                categories: [
                    {
                        value: 1,
                        text: 'Tarkvaratehnika'
                    },
                    {
                        value: 2,
                        text: 'Arvutivõrgud'
                    }
                ]
            }
        },
        methods: {
            onCategoryChange(value) {
                this.question.categoryId = value;
            },
            async postQuestion() {
                console.log('test');
                const response = await questionService.postQuestion({
                    title: this.question.title,
                    description: this.question.description,
                    categoryId: this.question.categoryId
                });
                this.$router.push({
                    name: 'QuestionView',
                    params: {
                        id: response.id
                    }
                });
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
            margin: 50px 0 100px 0;
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