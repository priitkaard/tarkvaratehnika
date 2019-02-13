<template>
    <div id="ping">
        <h1>Ping: </h1>
        <em>{{ message }}</em>
    </div>
</template>

<script>

    import apiService from "../services/ApiService";

    export default {
        name: 'Ping',
        data() {
            return {
                message: 'Loading...'
            }
        },
        async mounted() {
            const response = await apiService.get('/ping');
            this.message = response.data.message;
        }
    }
</script>

<style lang="scss">
    #ping {
        width: 100%;
        height: 100vh;
        background-color: #1c1c1c;
        overflow: hidden;
        text-align: center;
        // color: white;
    }
</style>