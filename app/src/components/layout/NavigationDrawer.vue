<template>
    <div class="NavigationDrawer" v-if="drawer" v-on:click="closeDrawer">
        <div class="NavigationDrawer__menu" v-on:click.stop>

            <div class="NavigationDrawer__menu_header">
                <chevron-right-icon :size="30" class="NavigationDrawer__menu_header_back" v-on:click="closeDrawer"/>
                <h3>Menu</h3>
            </div>

            <div class="NavigationDrawer__menu_list">
                <div class="NavigationDrawer__menu_list_item" v-for="item in menuItems" :key="item.id">
                    {{ item.name }}
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import ChevronRightIcon from "vue-material-design-icons/ChevronRight";
    import {mapActions, mapState} from "vuex";

    export default {
        name: "NavigationDrawer",
        components: {ChevronRightIcon},
        computed: {
            ...mapState('navigation', ['drawer', 'menuItems']),
        },
        methods: {
            ...mapActions('navigation', ['closeDrawer']),
        },
    }
</script>

<style scoped lang="scss">
    @import '../../assets/styles/_colors';
    .NavigationDrawer {
        height: 100vh;
        width: 100%;
        position: fixed;
        top: 0; left: 0;
        background-color: rgba(0, 0, 0, 0.5);
        z-index: 10000;

        @media (min-width: 1025px) {display: none}

        &__menu {
            position: absolute;
            z-index: 10001;
            top: 0; right: 0;
            width: 80%;
            height: 100vh;
            background-color: rgba(255, 255, 255, 0.9);
            &_header {
                height: 50px;
                line-height: 50px;
                letter-spacing: 2px;
                padding: 0 10px;
                font-size: 20px;
                border-left: 5px solid $color-primary;
                background-color: white;
                h3 {
                    display: inline;
                    vertical-align: middle;
                    margin: 0 0 0 10px;
                    font-size: 20px;
                }
                &_back:hover {
                    cursor: pointer;
                }
            }
            &_list_item {
                height: 50px;
                line-height: 50px;
                background-color: white;
                text-align: center;
                transition: background-color 0.4s;
                &:hover {
                    background-color: #ebebeb;
                    cursor: pointer;
                    transition: background-color 0.4s;
                }
            }

        }
    }
</style>