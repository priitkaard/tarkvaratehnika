<template>
    <div class="UISelect" ref="UISelect">
        <div class="UISelect__closed noselect" v-on:click="toggleSelect">
            {{ selected.text }}
            <chevron-down-icon v-if="selectClosed" class="UISelect__closed_icon" />
            <chevron-up-icon v-if="!selectClosed" class="UISelect__closed_icon" />
        </div>

        <transition name="fade">
            <div class="UISelect__options" v-if="!selectClosed">
                <div class="UISelect__option"
                     v-for="option in options"
                     v-on:click="onChange(option)">
                    {{ option.text }}
                </div>
            </div>
        </transition>
    </div>
</template>

<script>
    import ChevronDownIcon from "vue-material-design-icons/ChevronDown";
    import ChevronUpIcon from "vue-material-design-icons/ChevronUp";
    export default {
        name: "UISelect",
        components: {ChevronUpIcon, ChevronDownIcon},
        props: ['options'],
        data() {
            return {
                selectClosed: true,
                selected: this.options[0]
            }
        },
        methods: {
            toggleSelect() {
                this.selectClosed = !this.selectClosed;
            },
            onChange(option) {
                this.$emit('select-changed', option);
                this.selected = option;
                this.selectClosed = true;
            },
            onClickEvent(event) {
                const el = this.$refs['UISelect'];
                if (!this.selectClosed && !(el === event.target || el.contains(event.target))) {
                    this.selectClosed = true;
                }
            }
        },
        created() {
            document.body.addEventListener('click', this.onClickEvent);
        },
        destroyed() {
            document.body.removeEventListener('click', this.onClickEvent);
        }
    }
</script>

<style scoped lang="scss">
    @import '../assets/styles/_colors';
    @import '../assets/styles/_mixins';

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
        width: 100%;
        background-color: white;
        text-align: center;
        line-height: 40px;


        &__closed {
            height: 40px;

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
            width: 100%;
            box-shadow: 0 0 250px 100px rgba(0, 0, 0, 0.2);
            border-bottom-left-radius: 10px;
            border-bottom-right-radius: 10px;
            overflow: hidden;
            color: white;
        }

        &__option {
            height: 40px;
            background-color: $color-primary;
            transition: background-color 0.4s;

            &:hover {
                cursor: pointer;
                background-color: $color-primary-darker;
                transition: background-color 0.4s;
            }
        }
    }
</style>