<template>
    <div class="QuestionFilterCategory">
        <UISelect :options="categories" />
    </div>
</template>

<script>
    import UISelect from "../../UISelect";
    export default {
        name: "QuestionFilterCategory",
        components: {UISelect},
        data() {
            return {
                categories: [
                    {id: 0, text: 'All lectures'},
                    {id: 1, text: 'Tarkvaratehnika'}
                ]
            }
        }
    }
</script>

<style lang="scss" scoped>
    .QuestionFilterCategory {
        margin-bottom: 10px;
    }
</style>