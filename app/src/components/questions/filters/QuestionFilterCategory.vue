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
                    {id: 1, text: 'Tarkvaratehnika'},
                    {id: 2, text: 'Tarkvaratehnika'},
                    {id: 3, text: 'Tarkvaratehnika'},
                    {id: 4, text: 'Tarkvaratehnika'},
                    {id: 5, text: 'Tarkvaratehnika'},
                    {id: 6, text: 'Tarkvaratehnika'},
                    {id: 7, text: 'Tarkvaratehnika'},
                    {id: 8, text: 'Tarkvaratehnika'},
                    {id: 9, text: 'Tarkvaratehnika'},
                    {id: 10, text: 'Tarkvaratehnika'},
                    {id: 11, text: 'Tarkvaratehnika'},
                    {id: 12, text: 'Tarkvaratehnika'},
                    {id: 13, text: 'Tarkvaratehnika'},
                    {id: 14, text: 'Tarkvaratehnika'},
                    {id: 15, text: 'Tarkvaratehnika'},
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