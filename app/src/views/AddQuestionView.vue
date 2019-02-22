<template>
    <div class="AddQuestionView container">
        <div class="row">
            <div class="col-lg-6">
                <img class="AddQuestionView__image" src="../assets/img/question-mark.png" />
            </div>

            <div class="col-lg-6">

                <div class="AddQuestionView__box">

                    <input class="form-control AddQuestionView__box_title"
                           placeholder="Question title"
                           type="text"
                           v-model="question.title" />

                    <ckeditor class="AddQuestionView__box_description"
                              :editor="editor.type"
                              v-model="question.description"
                              :config="editor.config">
                    </ckeditor>

                    <UISelect class="AddQuestionView__box_categories"
                              :options="categories"
                              v-on:select-changed="onCategoryChange" />

                    <div class="row">
                        <div class="col-md-7"></div>
                        <div class="col-md-5">
                            <UIButton class="AddQuestionView__box_submit"
                                      text="Post question"
                                      v-on:click="postQuestion()" />
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
        async created() {
            this.question.title = this.$route.params.userInput;
            this.categories = await questionService.getQuestionCategories();
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
                categories: []
            }
        },
        methods: {
            onCategoryChange(value) {
                this.question.categoryId = value;
            },
            async postQuestion() {
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
    @import '../assets/styles/_variables.scss';

    .AddQuestionView {
        min-height: calc(100vh - #{$nav-height});
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

            &_title, &_categories {
                margin-bottom: 20px;
            }
            &_categories {
                margin-top: 20px;
            }
        }
    }
</style>