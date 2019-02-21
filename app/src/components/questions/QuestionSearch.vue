<template>
    <div class="QuestionSearch">
        <div class="search">
            <input type="text"
                   placeholder="Ask a question"
                   v-model="searchInput"
                   v-on:input="updateAutoComplete" />

            <arrow-right-icon
                    class="icon-button"
                    v-on:click="startQuestionCreate()"/>
        </div>

        <div class="suggestions" v-if="suggestions.length > 0">
            <em>Already asked questions:</em>

            <div v-for="(suggestion, index) in suggestions" v-bind:key="index" class="answer">
                {{ suggestion.title }}
            </div>
        </div>
    </div>
</template>

<script>
    import ArrowRightIcon from "vue-material-design-icons/ArrowRight.vue"
    import QuestionsService from '../../services/QuestionsService';

    export default {
        name: "QuestionSearch",
        data() {
            return {
                searchInput: '',
                suggestions: []
            }
        },
        components: {
            ArrowRightIcon
        },
        methods: {
            async updateAutoComplete() {
                if (this.searchInput.length === 0) {
                    this.suggestions = [];
                    return;
                }
                this.suggestions = await QuestionsService.autoCompleteSuggestions(this.searchInput);
            },

            startQuestionCreate() {
                this.$router.push({
                    name: 'AddQuestionView',
                    params: {
                        userInput: this.searchInput
                    }
                });
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import '../../assets/styles/colors';

    .QuestionSearch {
        display: inline-block;
        width: 90%;
        max-width: 800px;
        height: 40px;
        color: black;

        position: relative;

        .search {
            box-shadow: 0 0 10px 10px rgba(0, 0, 0, 0.2);

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

            &:focus-within {
                box-shadow: 0 0 5px 5px $color-primary-transparent;
                transition: 0.4s box-shadow;
            }
        }

        .suggestions {
            display: block;
            background-color: $color-background-darker;
            width: 100%;
            position: absolute;
            top: 20px;
            z-index: 1;
            padding-top: 30px;
            border-bottom-left-radius: 20px;
            border-bottom-right-radius: 20px;
            overflow: hidden;
            box-shadow: 0 0 250px 100px rgba(0, 0, 0, 0.2);

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
                    background-color: $color-background;
                    cursor: pointer;
                    transition: 0.4s background-color;
                }
            }
        }
    }
</style>