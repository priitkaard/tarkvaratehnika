<template>
    <div class="QuestionSearch">
        <div class="search">
            <input type="text" placeholder="Ask a question" v-model="query" />
            <arrow-right-icon class="icon" />
        </div>
        <div class="suggestions" v-if="query">
            <em>Already asked questions:</em>

            <div class="answer">
                What is the meaning of life?
            </div>
            <div class="answer">
                How many pets does Jesus have?
            </div>
            <div class="answer">
                How many hours per day I should sleep?
            </div>
            <div class="answer">
                Are cats reborn as angry women?
            </div>
        </div>
    </div>
</template>

<script>
    import ArrowRightIcon from "vue-material-design-icons/ArrowRight.vue"

    export default {
        name: "QuestionSearch",
        data() {
            return {
                query: ''
            }
        },
        components: {
            ArrowRightIcon
        }
    }
</script>

<style lang="scss" scoped>
    @import '../assets/styles/_colors.scss';
    @import '../assets/styles/_mixins.scss';

    .QuestionSearch {
        width: 90%;
        max-width: 800px;
        color: black;

        position: relative;

        .search {
            @include shadow-box;

            display: block;
            width: 100%;
            height: 40px;
            line-height: 40px;
            background-color: white;
            border: none;
            border-radius: 20px;
            font-size: 12px;
            outline-color: transparent;
            text-align: center;
            transition: 0.4s box-shadow;
            position: absolute;
            z-index: 2;
            overflow: hidden;

            input[type="text"] {
                width: calc(100% - 50px);
                height: 40px;
                margin: 0;
                float: left;
                padding: 0 20px;
                text-align: center;
                border: none;
                outline-color: transparent;
            }

            .icon:hover {
                cursor: pointer;
            }

            &:focus-within {
                box-shadow: 0 0 5px 5px $primary-color-transparent;
                transition: 0.4s box-shadow;
            }
        }

        .suggestions {
            display: block;
            background-color: $background-color-darker;
            width: 100%;
            position: absolute;
            top: 20px;
            z-index: 1;
            padding-top: 30px;
            border-bottom-left-radius: 20px;
            border-bottom-right-radius: 20px;
            overflow: hidden;

            em {
                display: block;
                font-style: normal;
                margin: 0 20px 10px 20px;
            }

            .answer {
                width: 100%;
                text-align: center;
                height: 40px;
                line-height: 40px;
                background-color: white;
                transition: 0.4s background-color;

                &:hover {
                    background-color: $background-color;
                    cursor: pointer;
                    transition: 0.4s background-color;
                }
            }
        }
    }
</style>