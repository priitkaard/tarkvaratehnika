<template>
    <div class="UISelect">
        <select v-model="selected" class="form-control">
            <option v-for="option in options" v-bind:value="option.value" v-on:change="onChange(option.value)">
                {{ option.text }}
            </option>
        </select>
    </div>
</template>

<script>
    export default {
        name: "UISelect",
        props: ['options'],
        data() {
            return {
                selected: null
            }
        },
        methods: {
            onChange(value) {
                this.$emit(value);
            }
        }
    }
</script>

<style lang="scss" scoped>
    .UISelect {

    }
</style>