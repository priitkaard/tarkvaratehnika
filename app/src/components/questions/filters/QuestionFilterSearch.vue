<template>
    <div class="QuestionFilterSearch">
        <input class="QuestionFilterSearch__input" type="text" />
        <magnify-icon class="QuestionFilterSearch__icon" />
    </div>
</template>

<script>
    import MagnifyIcon from "vue-material-design-icons/Magnify";
    export default {
        name: "QuestionFilterSearch",
        components: {MagnifyIcon}
    }
</script>

<style lang="scss" scoped>
    @import '../../../assets/styles/mixins';
    @import '../../../assets/styles/colors';

    .QuestionFilterSearch {
        @include shadow-box;

        width: 100%;
        height: 40px;
        background-color: white;

        &__input {
            height: 40px;
            line-height: 40px;
            text-align: center;
            width: calc(100% - 40px);
            border: none;
            font-size: 14px;
            outline-color: transparent;
        }

        &__icon {
            margin-left: 8px;

            &:hover {
                cursor: pointer;
            }
        }

        &:focus-within {
            box-shadow: 0 0 2px $color-primary;
        }
    }
</style>