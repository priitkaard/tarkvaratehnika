<template>
    <div class="UISelect">
        <select class="form-control"  @change="onChange($event)">
            <option v-for="option in options" v-bind:value="option.value">
                {{ option.text }}
            </option>
        </select>
    </div>
</template>

<script>
    export default {
        name: "UISelect",
        props: ['options'],
        methods: {
            onChange($event) {
                this.$emit('select-changed', $event.target.value);
            }
        }
    }
</script>

<style scoped>
</style>