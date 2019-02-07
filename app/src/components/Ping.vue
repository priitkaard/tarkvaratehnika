<template>
    <div id="ping">
        <h1>Ping: </h1>
        <em>{{ message }}</em>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'Ping',
    data() {
        return {
            message: 'Loading...'
        }
    },
    async mounted() {
        const response = await axios.get('http://localhost:8080/ping');
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