<template>
    <div class="Navigation" :class="{'Navigation__transparent': transparent}">
        <div class="container">

            <div class="Navigation__mobile">
                <img @click="$router.push({name: 'HomeView'})" src="../../assets/img/logo-inline.png" alt="" />
                <menu-icon @click="openDrawer" class="Navigation__mobile_hamburger" />
            </div>

            <div class="Navigation__desktop">
                <div class="Navigation__desktop_logo">
                    <img @click="$router.push({name: 'HomeView'})" src="../../assets/img/logo-inline.png" alt="" />
                </div>


                <div class="Navigation__desktop_menu">
                    <ul>
                        <li v-for="item in getMenuItems()" :key="item.id" @click="chooseItem(item)">
                            {{ item.name }}
                        </li>
                        <li v-if="username && points !== null">
                            {{ username }}: {{points}}
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import MenuIcon from 'vue-material-design-icons/Menu';
    import {mapActions, mapGetters, mapState} from 'vuex';

    export default {
        name: 'Navigation',
        props: {
            transparent: Boolean,
        },
        computed: {
            ...mapState('auth', ['username']),
            ...mapState('user', ['points']),
        },
        components: {MenuIcon},
        methods: {
            ...mapActions('navigation', ['openDrawer', 'chooseItem']),
            ...mapGetters('navigation', ['getMenuItems']),
        },

    };
</script>

<style lang="scss" scoped>
    @import '../../assets/styles/_variables.scss';

    .Navigation {
        width: 100%;
        height: $nav-height;
        line-height: $nav-height;
        background-color: rgba(0, 0, 0, 1);
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
        color: white;
        padding: 0 100px;

        position: fixed;
        top: 0;
        z-index: 9999;

        &__transparent {
            background-color: transparent;
        }

        &__mobile {
            display: none;
            width: 100%;
            justify-content: space-between;
            &_hamburger:hover {
                cursor: pointer;
            }
        }

        &__desktop {
            display: flex;
            justify-content: space-between;
            &_logo, &_menu {
                display: inline-block;
            }
            &_menu {
                ul {
                    li {
                        display: inline-block;
                        list-style-type: none;
                        height: 50px;
                        line-height: 50px;
                        padding: 0 25px;
                        border-bottom: 2px solid white;
                        box-sizing: border-box;
                        transition: background-color 0.4s;

                        &:hover {
                            cursor: pointer;
                            background-color: rgba(255, 255, 255, 0.1);
                            transition: background-color 0.4s;
                        }
                    }
                }
            }
        }
        @media (max-width: 1024px) {
            padding: 0 5%;
            &__mobile {display: flex}
            &__desktop {display: none}
        }

        img {
            display: inline-block;
            vertical-align: top;
            height: $nav-height;

            &:hover {
                cursor: pointer;
            }
        }
    }
</style>
