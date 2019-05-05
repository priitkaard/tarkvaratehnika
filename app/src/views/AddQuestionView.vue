<template>
    <div class="AddQuestionView container">
        <div class="row">
            <div class="col-lg-6 AddQuestionView__block">
                <img class="AddQuestionView__image" src="../assets/img/question-mark.png" />
            </div>

            <div class="col-lg-6 AddQuestionView__block">

                <div class="AddQuestionView__box">

                    <input class="form-control AddQuestionView__box_title"
                           placeholder="Question title"
                           type="text"
                           v-model="question.title" />

                    <UITextArea :value.sync="question.description" />

                    <UISelect class="AddQuestionView__box_categories"
                              :options="categories"
                              :value.sync="question.category"
                              @onChange="onCategoryChange"
                              full />

                    <div class="row">
                        <div class="col-md-7"></div>
                        <div class="col-md-5">
                            <UIButton class="AddQuestionView__box_submit"
                                      text="Post question"
                                      v-on:click="postQuestion()"
                                      full />
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</template>

<script>
    import UISelect from '../components/common/UISelect';
    import UIButton from '../components/common/UIButton';
    import UITextArea from '../components/common/UITextArea';
    import questionService from '../services/QuestionService';
    import categoryService from '../services/CategoryService';

    export default {
        name: "AddQuestionView",
        components: {
            UISelect,
            UIButton,
            UITextArea,
        },
        async created() {
            this.question.title = this.$route.params.userInput;
            this.categories = await categoryService.listCategories();
            this.question.category = this.categories[0];
            this.question.categoryId = this.categories[0] && this.categories[0].id;
        },
        data() {
            return {
                question: {
                    title: '',
                    description: '',
                    category: null,
                    categoryId: null,
                },
                categories: [],
            }
        },
        methods: {
            onCategoryChange(category) {
                this.question.categoryId = category.id;
            },

            async postQuestion() {
                const response = await questionService.postQuestion({
                    title: this.question.title,
                    description: this.question.description,
                    categoryId: this.question.categoryId
                });
                this.$router.push({
                    name: 'QuestionDetailView',
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
        margin-bottom: 150px;

        &__block {
            display: flex;
            align-items: center;
            justify-content: space-between;
            min-height: calc(100vh - #{$nav-height} - 150px);
        }

        &__image {
            width: 50%;
            margin-left: 25%;
        }
        &__box {
            display: block;
            box-sizing: border-box;
            width: 100%;
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