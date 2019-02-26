<template>
    <div class="HomeView" :style="{ backgroundImage: 'url(' + require('../assets/img/hero-image.jpg') + ')' }">
        <div class="HomeView__overlay">
            <!--div class="HomeView__header">
                <h1>TUTKit</h1>
                <p>Answers to your education questions</p>
            </div-->
            <div class="HomeView__header">
                <img src="@/assets/img/logo.png" />
            </div>

            <QuestionFilterSearch
                    class="HomeView__searchbar"
                    v-on:execute-search="startSearch"
                    v-on:input="updateQuery"
                    :rounded="true" />

            <div class="HomeView__or">or</div>

            <button v-on:click="$router.push({name: 'AddQuestionView'})"
                    class="HomeView__new_question_button">
                Create new question
            </button>

        </div>
    </div>
</template>

<script>
    import QuestionFilterSearch from "../components/questions/filters/QuestionFilterSearch";

    export default {
        name: "HomeView",
        components: {
            QuestionFilterSearch
        },
        data() {
            return {
                query: ''
            }
        },
        methods: {
            startSearch() {
                this.$router.push({
                    name: 'QuestionListView',
                    params: {
                        query: this.query
                    }
                });
            },
            updateQuery(value) {
                this.query = value;
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import '../assets/styles/_colors.scss';
    @import '../assets/styles/_mixins.scss';

    .HomeView {
        width: 100%;
        height: 100vh;
        background-size: cover;
        background-position: center;
        color: white;
        position: absolute;
        top: 0;


        &__overlay {
            display: flex;
            height: 100vh;
            flex-direction: column;
            justify-content: center;
            align-content: center;
            align-items: center;
            background-color: rgba(0, 0, 0, 0.4);
        }

        &__header {
            text-align: center;
            h1 {
                font-size: 60px;
            }
            p {
                font-size: 12px;
            }
            img {
                margin-bottom: 20px;
            }
        }

        &__searchbar {
            width: 95%;
            max-width: 800px;
            // color: black;
            // border-radius: 20px;
            // overflow: hidden;
        }

        &__or {
            font-style: normal;
            margin: 10px 0;
        }

        &__new_question_button {
            background-color: rgba(255, 255, 255, 0.9);
            color: black;
            width: auto;
            padding: 0 20px;
            border-radius: 20px;
            transition: background-color 0.4s;
            @include shadow-box;

            &:hover {
                background-color: white;
                cursor: pointer;
                transition: background-color 0.4s;
            }
        }

        // &__new_question_button {
        //     height: 40px;
        //     background-color: rgba(255, 255, 255, 0.9);
        //     outline-color: transparent;
        //     color: black;
        //     font-weight: normal;
        //     border: none;
        //     padding: 0 20px;
        //     border-radius: 20px;
        //     margin-top: 20px;
        //     transition: background-color 0.4s;
//
        //     &:hover {
        //         background-color: white;
        //         transition: background-color 0.4s;
        //     }
        // }
    }

    h1 {
        margin: 0;
    }

    p {
        margin: -10px 0 50px 0;
    }

    h1, p, em.or {
        text-shadow: 0 0 2px rgba(0, 0, 0, 0.2);
    }

    .or {
        font-style: normal;
        margin: 10px 0;
    }

    .browse-questions-btn {
        border-radius: 20px;
        padding-left: 15px;
        padding-right: 15px;
        font-size: 14px;
        background-color: rgba(255, 255, 255, 0.9);
        &:hover {
            background-color: rgba(255, 255, 255, 1);
        }
    }
</style>
