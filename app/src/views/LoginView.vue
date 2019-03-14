<template>
    <div class="LoginView">
        <form @submit.prevent="submitLogin" class="LoginView__form">
            <img src="../assets/img/logo.png" alt="" />
            <div>
                <input type="text" placeholder="Username" />
            </div>
            <div>
                <input type="text" placeholder="Password" />
            </div>

            <div>
                <UIButton text="Register" style="margin-bottom: 10px"/>
                <UIButton text="Log in" />
            </div>
        </form>
    </div>
</template>

<script>
    import UIButton from "../components/UIButton";
    export default {
        name: "LoginView",
        components: {UIButton},
        data() {
            return {

            };
        },
        methods: {
            submitLogin() {

            },
        },
    }
</script>

<style scoped lang="scss">
    @import '../assets/styles/_mixins';

    .LoginView {
        display: flex;
        width: 100%;
        height: calc(100vh - 80px - 200px);
        align-items: center;
        justify-content: center;
        &__form {
            display: block;
            width: 95%;
            max-width: 400px;
            background-color: black;
            padding: 20px;
            text-align: center;
            @include shadow-depth;

            input[type="text"] {
                width: 100%;
                height: 40px;
                line-height: 40px;
                font-size: 12px;
                text-align: center;
                margin-bottom: 10px;
            }
        }
    }

</style>