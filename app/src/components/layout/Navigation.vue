<template>
    <div class="Navigation">
        <div class="mobile-view">
            <img v-on:click="$router.push({name: 'HomeView'})" src="@/assets/img/logo.png"/>
            <menu-icon/>
        </div>

        <div class="desktop-view">
            <div class="nav-logo">
                <img v-on:click="$router.push({name: 'HomeView'})" src="@/assets/img/logo.png"/>
            </div>

            <div class="nav-search">
                <QuestionSearch/>
            </div>

            <div class="nav-menu">
                Log in
            </div>
        </div>
    </div>
</template>

<script>
    import QuestionSearch from "../questions/QuestionSearch";
    import MenuIcon from "vue-material-design-icons/Menu";

    export default {
        name: "Navigation",
        components: {MenuIcon, QuestionSearch}
    }
</script>

<style lang="scss" scoped>
    .Navigation {
        $nav-height: 50px;

        width: 100%;
        height: $nav-height;
        line-height: $nav-height;
        background-color: rgba(0, 0, 0, 0.9);
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
        color: white;
        padding: 0 100px;

        position: fixed;
        top: 0;
        z-index: 5;

        img {
            height: 80px;
            margin-top: -15px;

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