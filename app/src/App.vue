<template>
    <div id="app">
        <transition name="fade">
            <Navigation v-if="!isHomeView"/>
        </transition>

        <div class="page-content">
            <transition name="router-animation">
                <router-view></router-view>
            </transition>
        </div>

        <transition name="fade">
            <Footer v-if="!isHomeView"/>
        </transition>
    </div>
</template>

<script>

    import Navigation from "./components/layout/Navigation";
    import Footer from "./components/layout/Footer";

    export default {
        name: 'app',
        components: {
            Footer,
            Navigation
        },
        data() {
            return {
                isHomeView: this.$route.name === 'HomeView'
            }
        },
        watch: {
            '$route'(to) {
                this.isHomeView = to.name === 'HomeView';
            }
        },
        created() {
            window.addEventListener('scroll', this.handleScroll);
        },
        destroyed() {
            window.removeEventListener('scroll', this.handleScroll);
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
