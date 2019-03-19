<template>
    <div id="app">
        <navigation-drawer />

        <transition name="fade">
            <Navigation v-if="$route.meta.layout !== 'full'"/>
        </transition>

        <div class="page-content" v-bind:class="{'nav-offset': $route.meta.layout !== 'full'}">
            <transition name="router-animation">
                <router-view></router-view>
            </transition>
        </div>

        <transition name="fade">
            <Footer v-if="$route.meta.layout !== 'full'"/>
        </transition>
    </div>
</template>

<script>

    import Navigation from "./components/layout/Navigation";
    import Footer from "./components/layout/Footer";
    import NavigationDrawer from "./components/layout/NavigationDrawer";

    export default {
        name: 'app',
        components: {
            NavigationDrawer,
            Footer,
            Navigation
        }
    }
</script>

<style lang="scss" scoped>
    html, body {
        margin: 0;
    }

    .page-content {
        padding-top: 50px;
        min-height: calc(100vh - 200px);

        &.nav-offset {
            padding-top: 80px;
        }
    }

    .fade-enter-active {
        opacity: 1;
        transition: opacity .4s;
    }

    .fade-enter {
        opacity: 0;
    }

    .router-animation-enter-active {
        transition: margin-top .4s;
    }

    .router-animation-enter {
        margin-top: 100vh;
    }

    .router-animation-leave-active, .fade-leave-active {
        opacity: 0;
        transition: opacity .4s;
    }

    .router-animation-enter, .fade-leave {
        opacity: 1;
    }
</style>
