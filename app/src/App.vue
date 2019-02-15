<template>
    <div id="app">
        <Hero />

        <transition name="fade">
            <Navigation v-if="isContentView"/>
        </transition>
        <router-view></router-view>

        <Footer />
    </div>
</template>

<script>
    import Hero from './components/layout/Hero';
    import Navigation from "./components/layout/Navigation";
    import Footer from './components/layout/Footer';

    export default {
        name: 'app',
        components: {
            Navigation,
            Hero,
            Footer
        },
        data() {
            return {
                isContentView: false
            }
        },
        methods: {
            handleScroll() {
                this.isContentView = window.scrollY > window.innerHeight;
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

<style scoped>
    html, body {
        margin: 0;
    }

    .fade-enter-active {
        opacity: 1;
        transition: opacity .4s;
    }

    .fade-enter {
        opacity: 0.1;
    }

</style>
