<template>
    <div class="QuestionCommentCard">
        <div class="QuestionCommentCard__user">
            <account-icon />
            {{ comment.user.username }}
        </div>
        <div class="QuestionCommentCard__text">
            {{ comment.text }}
        </div>
        <div class="QuestionCommentCard__created">
            {{ created }}
        </div>
    </div>
</template>

<script>
    import AccountIcon from 'vue-material-design-icons/Account';
    import moment from 'moment';

    export default {
        name: 'QuestionCommentCard',
        props: {
            comment: Object,
        },
        components: {AccountIcon},
        data() {
            return {
                created: moment(this.comment.created).fromNow(),
            };
        },
    };
</script>

<style scoped lang="scss">
    @import '../../assets/styles/mixins';

    .QuestionCommentCard {
        width: 100%;
        margin: 0 auto;
        min-height: 40px;
        line-height: 40px;
        padding: 0 10px;
        background-color: white;
        @include shadow-box;

        &__created, &__user, &__text { display: inline-block }
        &__user { font-weight: bold }
        &__created { float: right }
        &__text { padding: 0 10px }
    }
</style>
