<template>
    <div class="QuestionStatistics sidebar">
        <header>Statistics</header>
        <ul>
            <li>
                <comment-text-multiple-icon/>
                123 questions
            </li>
            <li>
                <chevron-up-icon/>
                321 votes
            </li>
        </ul>
    </div>
</template>

<script>
    import CommentTextMultipleIcon from "vue-material-design-icons/CommentTextMultiple";
    import ChevronUpIcon from "vue-material-design-icons/ChevronUp";

    export default {
        name: "QuestionStatistics",
        components: {ChevronUpIcon, CommentTextMultipleIcon}
    }
</script>

<style lang="scss" scoped>
    .QuestionStatistics {
        ul {
            padding: 20px;
            font-weight: bold;

            li {
                list-style-type: none;
                padding: 5px 10px;

                .material-design-icon {
                    margin-right: 10px;
                }
            }
        }
    }
</style>