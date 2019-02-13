<template>
    <div id="ping">
        <h1>Ping: </h1>
        <em>{{ message }}</em>
    </div>
</template>

<script>
    import {ApiService} from "../core/api.service";

    export default {
        name: 'Ping',
        data() {
            return {
                message: 'Loading...'
            }
        },
        async mounted() {
            const response = await ApiService.get('/ping');
            this.message = response.data.message;
        }
    }
</script>

<style>
    #ping {
        width: 100%;
        height: 100vh;
        background-color: #1c1c1c;
        overflow: hidden;
        text-align: center;
        color: white;
    }
</style>