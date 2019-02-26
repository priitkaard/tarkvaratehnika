<template>
    <div class="UIButton">
        <button v-on:click="$emit('click')">
            {{ text }}
        </button>
    </div>
</template>

<script>
    export default {
        name: "UIButton",
        props: ['text']
    }
</script>

<style lang="scss" scoped>
    @import '../assets/styles/_colors.scss';

    .UIButton {
        button {
            height: 40px;
            width: 100%;
            font-size: 14px;
            padding: 0 30px;
            color: white;
            background-color: $color-primary;
            border: none;
            outline-color: transparent;
            transition: all 0.4s;

            &:hover {
                transition: all 0.4s;
                background-color: $color-primary-darker;
            }
            &:focus {
                transition: all 0.4s;
                box-shadow: 0 0 5px $color-primary;
            }
        }
    }
</style>