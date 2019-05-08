<template>
    <div class="UIAlert" :class="{'UIAlert__success': success, 'UIAlert__danger': danger}">
        {{ value }}
    </div>
</template>

<script>
    export default {
        name: 'UIAlert',
        props: {
            value: String,
            success: Boolean,
            danger: Boolean,
        },
        data() {
            return { input: this.value };
        },
        watch: {
            input(val) {
                this.$emit('input', val);
            },
        },
    };
</script>

<style scoped lang="scss">
    @import '../../assets/styles/colors';

    .UIAlert {
        display: block;
        width: 100%;
        padding: 10px;
        text-align: center;
        background-color: black;
        color: white;
        font-size: 12px;

        &__success { background-color: $color-success }
        &__danger { background-color: $color-danger }
    }
</style>
