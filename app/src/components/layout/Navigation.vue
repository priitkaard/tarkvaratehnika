<template>
    <div class="Navigation">
        <div class="container">
            <div class="mobile-view">
                <img v-on:click="$router.push({name: 'HomeView'})" src="@/assets/img/logo-inline.png"/>
                <menu-icon/>
            </div>

            <div class="desktop-view">
                <div class="nav-logo">
                    <img v-on:click="$router.push({name: 'HomeView'})" src="@/assets/img/logo-inline.png"/>
                </div>


                <div class="Navigation__menu">
                    <ul>
                        <li>Log in</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import MenuIcon from "vue-material-design-icons/Menu";

    export default {
        name: "Navigation",
        components: {MenuIcon}
    }
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

        &__menu {
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

        img {
            display: inline-block;
            vertical-align: top;
            height: $nav-height;

            &:hover {
                cursor: pointer;
            }
        }

        .desktop-view {
            display: flex;
            justify-content: space-between;
            .nav-logo, .nav-menu {
                display: inline-block;
            }

            .nav-search {
                display: inline-block;
                width: 50%;
                line-height: 50px;
                .QuestionSearch {
                    width: 100%;
                    margin-top: 5px;
                }
            }
        }

        .mobile-view {
            display: none;
            width: 100%;
            justify-content: space-between;
        }

        @media (max-width: 1024px) {
            padding: 0 5%;
            .mobile-view {
                display: flex;
            }
            .desktop-view {
                display: none;
            }
        }
    }
</style>