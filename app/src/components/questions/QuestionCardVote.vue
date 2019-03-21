<template>
    <div class="QuestionCardVote">
        <menu-up-icon class="QuestionCardVote__vote_icon" v-if="!disableVoting" :size="40" @click="upVote" />
        <em class="QuestionCardVote__score"
            :class="{ 'QuestionCardVote__positive': score > 0, 'QuestionCardVote__negative': score < 0 }">
            {{ score }}
        </em>
        <menu-down-icon class="QuestionCardVote__vote_icon" v-if="!disableVoting" :size="40" @click="downVote" />
    </div>
</template>

<script>
    import MenuUpIcon from "vue-material-design-icons/MenuUp.vue"
    import MenuDownIcon from "vue-material-design-icons/MenuDown.vue"

    export default {
        name: "QuestionCardVote",
        props: {
            'disableVoting': Boolean,
            'score': Number,
        },
        components: {
            MenuUpIcon,
            MenuDownIcon,
        },
        methods: {
            upVote() {
                this.$emit('onUpVote');
            },
            downVote() {
                this.$emit('onDownVote');
            },
        }
    }
</script>

<style scoped lang="scss">
    @import '../../assets/styles/colors';

    .QuestionCardVote {
        display: inline-flex;
        vertical-align: top;
        flex-direction: column;
        text-align: center;
        width: 40px;
        height: 104px;
        justify-content: center;
        &__score { font-style: normal }
        &__positive { color: $color-success }
        &__negative { color: $color-danger }
        &__vote_icon {
            display: inline-block;
            &:hover { cursor: pointer }
        }
    }
</style>