<template>
    <div class="LoginView">
        <form @submit.prevent="submitLogin" class="LoginView__form">
            <img src="../assets/img/logo.png" alt="" />
            <div>
                <input type="text" placeholder="Username" v-model="inputs.username"/>
            </div>
            <div>
                <input type="text" placeholder="Password" v-model="inputs.password"/>
            </div>

            <div>
                <UIButton text="Register" style="margin-bottom: 10px"/>
                <UIButton text="Log in" @click="submitLogin"/>
            </div>
        </form>
    </div>
</template>

<script>
    import UIButton from "../components/UIButton";
    import authService from '../services/AuthService';

    export default {
        name: "LoginView",
        components: {UIButton},
        data() {
            return {
                inputs: {
                    username: '',
                    password: '',
                }
            };
        },
        methods: {
            async submitLogin() {
                const token = await authService.tryLogin(this.inputs.username, this.inputs.password);
                this.inputs.password = '';
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