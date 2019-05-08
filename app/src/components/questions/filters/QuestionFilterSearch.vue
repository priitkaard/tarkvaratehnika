<template>
    <div class="QuestionFilterSearch">
        <div class="QuestionFilterSearch__search"
             v-bind:class="{'QuestionFilterSearch__search_rounded': rounded}">

            <form v-on:submit.prevent="search">
                <input class="QuestionFilterSearch__search_input"
                       type="text"
                       :value="query"
                       v-on:input="onQueryChanged"
                       v-on:blur="clearSuggestions"
                       placeholder="Search..." />

                <magnify-icon class="QuestionFilterSearch__search_icon" v-on:click="search" />
            </form>
        </div>

        <transition name="fade">
            <div class="QuestionFilterSearch__suggestions"
                 v-bind:class="{'QuestionFilterSearch__suggestions_rounded': rounded}"
                 v-if="suggestions.length > 0">
                <em>Similar questions:</em>

                <div v-for="(suggestion, index) in suggestions"
                     v-bind:key="index"
                     v-on:click="selectSuggestion(suggestion)"
                     class="QuestionFilterSearch__suggestions_option">
                    {{ suggestion.title }}
                </div>
            </div>
        </transition>
    </div>
</template>

<script>
    import MagnifyIcon from 'vue-material-design-icons/Magnify';
    import questionService from '../../../services/QuestionService';
    import {mapActions} from 'vuex';

    export default {
        name: 'QuestionFilterSearch',
        props: ['rounded'],
        components: {MagnifyIcon},
        data() {
            return {
                suggestions: [],
            };
        },
        computed: {
            query() {
                return this.$store.state.question.filters.query;
            },
        },
        methods: {
            ...mapActions('question', ['updateQuery', 'updateQuestionList']),
            clearSuggestions() {
                this.suggestions = [];
            },
            search() {
                this.$emit('search');
                this.updateQuestionList();
                this.clearSuggestions();
            },
            selectSuggestion(suggestion) {
                this.$router.push({
                    name: 'QuestionDetailView',
                    params: {
                        id: suggestion.id,
                    },
                });
            },
            async onQueryChanged(e) {
                const query = e.target.value;
                this.updateQuery(query);

                if (query) {
                    this.suggestions = await questionService.autoCompleteSuggestions(query);
                } else {
                    this.suggestions = [];
                }
            },
        },
    };
</script>

<style lang="scss" scoped>
    @import '../../../assets/styles/mixins';
    @import '../../../assets/styles/colors';

    .QuestionFilterSearch {
        color: black;
        height: 40px;

        position: relative;

        &__search {
            width: 100%;
            height: 40px;
            background-color: white;
            @include shadow-box;
            position: absolute;
            z-index: 4;
            overflow: hidden;


            &_input {
                width: calc(100% - 50px);
                height: 40px;
                line-height: 40px;
                font-size: 14px;
                text-align: center;
                text-indent: 40px;
                border: none;
                outline-color: transparent;
            }
            &_icon {
                margin-left: 13px;

                &:hover {
                    cursor: pointer;
                }
            }
            &_rounded {
                border-radius: 20px;
            }
            &:focus-within {
                box-shadow: 0 0 2px $color-primary;
            }
        }

        &__suggestions {
            width: 100%;
            padding-top: 20px;
            background-color: $color-background-darker;
            position: absolute;
            top: 20px;
            z-index: 3;
            overflow: hidden;
            @include shadow-depth;

            em {
                display: block;
                font-style: normal;
                padding: 5px 15px;
            }
            &_option {
                background-color: white;
                height: 40px;
                line-height: 40px;
                text-align: center;
                transition: background-color 0.4s;

                &:hover {
                    cursor: pointer;
                    background-color: $color-background;
                    transition: background-color 0.4s;
                }
            }
            &_rounded {
                border-bottom-left-radius: 20px;
                border-bottom-right-radius: 20px;
            }
        }
    }
</style>
