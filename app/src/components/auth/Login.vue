<template>
    <div class="Login">
        <form @submit.prevent="submitLogin" class="Login__form">
            <close-icon class="Login__form_close" @click="onDismiss" />

            <img src="../../assets/img/logo.png" alt="" class="Login__form_logo" />

            <UIAlert v-if="message" :value="message" success />
            <UIAlert v-if="error" :value="error" danger />

            <div>
                <UITextField :value.sync="inputs.username" placeholder="Username" full text-center />
            </div>
            <div>
                <UITextField :value.sync="inputs.password" placeholder="Password" password full text-center />
            </div>

            <UIGroup>
                <UIButton text="Register" @click="submitRegister" outline />
                <UIButton text="Log in" @click="submitLogin"/>
            </UIGroup>
        </form>
    </div>
</template>

<script>
    import UIGroup from "../common/UIGroup";
    import UIButton from "../common/UIButton";
    import UIAlert from "../common/UIAlert";
    import UITextField from "../common/UITextField";
    import * as authService from '../../services/AuthService';
    import CloseIcon from "vue-material-design-icons/Close";

    export default {
        name: "Login",
        props: ['onDismiss'],
        components: {
            CloseIcon,
            UIButton,
            UIGroup,
            UITextField,
            UIAlert,
        },
        data() {
            return {
                inputs: {
                    username: '',
                    password: '',
                },
                error: null,
                message: null,
            };
        },
        methods: {
            async submitLogin() {
                try {
                    await authService.tryLogin(this.inputs.username, this.inputs.password);
                    this.onDismiss();
                    this.$router.push({name: 'HomeView'});
                } catch (err) {
                    this.setError('Invalid credentials!');
                }
                this.inputs.password = '';
            },
            async submitRegister() {
                try {
                    await authService.register(this.inputs.username, this.inputs.password);
                    this.setMessage('Registered successfully. You can now log in.');
                } catch (err) {
                    if (err.error) {
                        this.setError(err.error);
                        return;
                    }
                    this.setError('Technical error');
                }
            },
            setError(message) {
                this.message = null;
                this.error = message;
            },
            setMessage(message) {
                this.message = message;
                this.error = null;
            }
        },
    }
</script>

<style scoped lang="scss">
    @import '../../assets/styles/_mixins';

    .Login {
        display: flex;
        width: 100%;
        height: calc(100vh - 80px - 200px);
        align-items: center;
        justify-content: center;
        position: relative;
        &__form {
            display: block;
            width: 95%;
            max-width: 350px;
            background-color: #212121;
            padding: 20px;
            border-radius: 10px;
            text-align: center;
            position: relative;
            @include shadow-depth;

            &_close {
                position: absolute;
                top: 20px;
                right: 20px;
                color: white;
                &:hover {
                    cursor: pointer;
                }
            }
            &_logo {
                height: 150px;
                margin-bottom: 10px;
            }
            .UITextField, .UIAlert {
                margin-bottom: 10px;
            }
        }
    }
</style>
