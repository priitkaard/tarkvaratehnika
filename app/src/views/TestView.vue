<template>
    <div class="container">
        Sort by: {{ sort }}
    </div>
</template>

<script>
    export default {
        name: 'TestView',
        computed: {
            sort() {
                return this.$store.state.question.filters.sort;
            }
        }
    }
</script>

<style scoped>
</style>
