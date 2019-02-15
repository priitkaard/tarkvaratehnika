<template>
    <div class="QuestionsListElementDetails">
        <div class="detail">
            <folder-icon :size="24" />
            Various
        </div>
    </div>
</template>

<script>
    import FolderIcon from 'vue-material-design-icons/Folder';
    export default {
        name: "QuestionsListElementDetails",
        components: {
            FolderIcon
        }
    }
</script>

<style scoped>
    .QuestionsListElementDetails {
        position: absolute;
        bottom: 10px;
        right: 10px;
        font-size: 14px;
        line-height: 24px;

        .detail {

        }
    }
</style>