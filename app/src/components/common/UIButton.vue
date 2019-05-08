<template>
    <div class="UIButton">
        <button @click.prevent="$emit('click')" :class="{'UIButton__outline': outline, 'UIButton__full': full}">
            {{ text }}
        </button>
    </div>
</template>

<script>
    export default {
        name: 'UIButton',
        props: {
            text: String,
            outline: Boolean,
            full: Boolean,
        },
    };
</script>

<style lang="scss" scoped>
    @import '../../assets/styles/colors';

    .UIButton {
        button {
            display: inline-block;
            height: 40px;
            // width: 100%;
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
            &.UIButton__outline {
                background-color: transparent;
                border: 1px solid $color-primary;
                color: $color-primary;
                &:hover {
                    background-color: $color-primary-darker;
                    color: white;
                }
            }
            &.UIButton__full { width: 100% }
        }
    }
</style>
