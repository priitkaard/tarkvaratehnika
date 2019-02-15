<template>
    <div class="QuestionsListElementDetails">
        <div class="detail">
            <folder-icon :size="20" />
            Various
        </div>
        <div class="detail">
            <clock-outline-icon :size="20" />
            2 days ago
        </div>
        <div class="detail">
            <eye-icon :size="20" />
            1315 views
        </div>
        <div class="detail">
            <comment-icon :size="20" />
            4 comments
        </div>
    </div>
</template>

<script>
    import FolderIcon from 'vue-material-design-icons/Folder';
    import ClockOutlineIcon from "vue-material-design-icons/ClockOutline";
    import EyeIcon from "vue-material-design-icons/Eye";
    import CommentIcon from "vue-material-design-icons/Comment";

    export default {
        name: "QuestionsListElementDetails",
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
    }
</style>