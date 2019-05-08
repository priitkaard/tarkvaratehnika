<template>
    <div class="UIGroup">
        <slot></slot>
    </div>
</template>

<script>
    export default {
        name: 'UIGroup',
    };
</script>

<style scoped>
    .UIGroup {
        display: flex;
        width: 100%;
        justify-content: space-between;
    }
</style>
