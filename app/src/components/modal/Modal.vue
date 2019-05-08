<template>
    <transition name="fade">
        <div class="Modal" v-if="component">
            <component :is="component" v-bind:onDismiss="dismissModal"/>
        </div>
    </transition>
</template>

<script>
    import { mapActions, mapState } from 'vuex';
    import Login from '../auth/Login';

    export default {
        name: 'Modal',
        computed: {
            ...mapState('modal', ['component']),
        },
        methods: {
            ...mapActions('modal', ['dismissModal']),
        },
        components: {
            Login,
        },
    };
</script>

<style scoped lang="scss">
    @import '../../assets/styles/transitions';

    .Modal {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        z-index: 10000;

        display: flex;
        align-items: center;
        justify-content: center;

        width: 100%;
        height: 100vh;

        background-color: rgba(0, 0, 0, 0.2);
    }
</style>
