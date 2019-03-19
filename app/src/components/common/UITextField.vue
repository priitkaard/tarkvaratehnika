<template>
    <div class="UITextField">
        <input
            :type="password ? 'password' : 'text'"
            :class="{'UITextField__full': full, 'UITextField__center': textCenter}"
            :placeholder="placeholder"
            v-model="input"
        />
    </div>
</template>

<script>
    export default {
        name: "UITextField",
        props: {
            placeholder: String,
            value: String,
            password: Boolean,
            textCenter: Boolean,
            full: Boolean,
        },
        data() {
            return { input: this.value }
        },
        watch: {
            input(val) {
                this.$emit('update:value', val);
            }
        }

    }
</script>

<style scoped lang="scss">
    .UITextField {
        input {
            height: 40px;
            line-height: 40px;
            padding: 0 10px;
            font-size: 12px;
            &.UITextField__center { text-align: center }
            &.UITextField__full {
                display: block;
                width: 100%;
            }
        }
    }
</style>