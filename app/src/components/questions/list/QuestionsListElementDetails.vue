<template>
    <div class="QuestionsListElementDetails">
        <div class="detail">
            <folder-icon :size="20"/>
            Unknown
        </div>
        <div class="detail">
            <clock-outline-icon :size="20"/>
            {{ created }}
        </div>
        <div class="detail">
            <eye-icon :size="20"/>
            N/A views
        </div>
        <div class="detail">
            <comment-icon :size="20"/>
            {{ question.comments }} comments
        </div>
    </div>
</template>

<script>
    import FolderIcon from 'vue-material-design-icons/Folder';
    import ClockOutlineIcon from "vue-material-design-icons/ClockOutline";
    import EyeIcon from "vue-material-design-icons/Eye";
    import CommentIcon from "vue-material-design-icons/Comment";
    import moment from 'moment';

    export default {
        name: "QuestionsListElementDetails",
        props: ['question'],
        data() {
            return {
                created: moment(this.question.created).fromNow()
            }
        },
        components: {
            CommentIcon,
            EyeIcon,
            ClockOutlineIcon,
            FolderIcon
        }
    }
</script>

<style lang="scss" scoped>
    .QuestionsListElementDetails {
        position: absolute;
        bottom: 10px;
        right: 20px;

        .detail {
            display: inline-flex;
            align-items: center;
            font-size: 14px;
            margin-left: 20px;

            .material-design-icon {
                margin-right: 5px;
            }
        }
        @media (max-width: 1000px) {
            position: initial;
            .detail {
                display: inline-block;
                width: 50%;
                margin-left: 0;
                text-align: center;
                margin-top: 10px;
                font-size: 12px;

                .material-design-icon {
                }
            }
        }
    }
</style>