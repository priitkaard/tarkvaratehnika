<template>
    <div id="app">
        <Hero/>
        <Navigation v-if="isContentView"/>
        <HomePage/>
        <Footer />
    </div>
</template>

<script>
    import Hero from './components/layout/Hero';
    import HomePage from './views/HomePage.vue'
    import Navigation from "./components/layout/Navigation";
    import Footer from './components/layout/Footer';

    export default {
        name: 'app',
        components: {
            Navigation,
            Hero,
            HomePage,
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
</style>
