<template>
    <div class="UISelect" ref="UISelect" :class="{ 'UISelect__full': full }">
        <div class="UISelect__closed noselect" v-on:click="toggleSelect">
            {{ selected ? selected.name : '' }}

            <chevron-down-icon v-if="selectClosed" class="UISelect__closed_icon" />
            <chevron-up-icon v-if="!selectClosed" class="UISelect__closed_icon" />
        </div>

        <transition name="fade">
            <div class="UISelect__options" v-if="!selectClosed">
                <div class="UISelect__option"
                     v-for="option in options"
                     v-bind:key="option.id"
                     v-on:click="onChange(option)">
                    {{ option.name }}
                </div>
            </div>
        </transition>
    </div>
</template>

<script>
    import ChevronDownIcon from 'vue-material-design-icons/ChevronDown';
    import ChevronUpIcon from 'vue-material-design-icons/ChevronUp';
    export default {
        name: 'UISelect',
        components: { ChevronUpIcon, ChevronDownIcon },
        props: {
            options: Array,
            value: Object,
            full: Boolean,
        },
        data() {
            return {
                selectClosed: true,
            };
        },
        computed: {
            selected() {
                return this.options.filter(option => option === this.value)[0];
            },
        },
        methods: {
            toggleSelect() {
                this.selectClosed = !this.selectClosed;
            },
            onChange(option) {
                this.$emit('update:value', option);
                this.$emit('onChange', option);
                this.selectClosed = true;
            },
            onClickEvent(event) {
                const el = this.$refs['UISelect'];
                if (!this.selectClosed && !(el === event.target || el.contains(event.target))) {
                    this.selectClosed = true;
                }
            },
        },
        created() {
            document.body.addEventListener('click', this.onClickEvent);
        },
        destroyed() {
            document.body.removeEventListener('click', this.onClickEvent);
        },
    };
</script>

<style scoped lang="scss">
    @import '../../assets/styles/colors';
    @import '../../assets/styles/mixins';

    .fade-enter-active, .fade-leave {
        opacity: 1;
        transition: opacity 0.4s;
    }
    .fade-enter, .fade-leave-active {
        opacity: 0;
        transition: opacity 0.4s;
    }

    .UISelect {
        @include shadow-box;
        position: relative;
        height: 40px;
        width: 200px;
        max-width: 100%;
        background-color: white;
        text-align: center;
        line-height: 40px;

        &__full { width: 100% }


        &__closed {
            height: 40px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;


            &:hover {
                cursor: pointer;
            }
            &_icon {
                position: absolute;
                right: 10px;
                pointer-events: none;
            }
        }

        &__options {
            position: absolute;
            top: 40px;
            z-index: 5;
            max-height: 250px;
            width: 100%;
            overflow-y: scroll;
            color: white;
            background-color: $color-primary;
            @include shadow-depth;

            &::-webkit-scrollbar-track {
                position: absolute;
                -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
                background-color: $color-primary;
            }

            &::-webkit-scrollbar {
                width: 6px;
                background-color: transparent;
            }

            &::-webkit-scrollbar-thumb {
                background-color: #000000;
            }
        }

        &__option {
            height: 40px;
            background-color: $color-primary;
            transition: background-color 0.4s;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;


            &:hover {
                cursor: pointer;
                background-color: $color-primary-darker;
                transition: background-color 0.4s;
            }
        }
    }
</style>
