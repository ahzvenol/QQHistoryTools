<template>
    <ul id="right-menu" v-if="onPC">
        <li v-show="!colorfulBubbles()" @click="changeMode()">使用多彩气泡</li>
        <li v-show="colorfulBubbles()" @click="changeMode()">隐藏多彩气泡</li>
        <!-- <li @click="save()">另存为</li> -->
        <li @click="reductionContextmenu()">显示正常右键菜单(不可逆)</li>
    </ul>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { colorfulBubbles, onPC } from '../store'

const changeMode = () => colorfulBubbles(!colorfulBubbles())

const reductionContextmenu = () => document.oncontextmenu = _ => true

onMounted(() => {
    let rm = document.getElementById('right-menu')!
    document.oncontextmenu = function (e) {
        e = e || window.event
        let mx = e.clientX
        let my = e.clientY
        rm.style.left = mx + 'px'
        rm.style.top = my + 'px'
        rm.style.display = 'block'
        return false
    }
    document.onclick = function () {
        rm.style.display = 'none'
    }
})

</script>

<style scoped>
#right-menu {
    border: 2px solid rgb(235, 235, 235);
    border-right: none;
    border-left: none;
    background-color: white;
    cursor: default;
    list-style: none;
    position: fixed;
    display: none;
    box-shadow: 0px 0px 6px rgba(0, 0, 0, 0.2);
}

#right-menu li {
    padding: 8px 42px;
    white-space: nowrap;
}

#right-menu li:hover {
    background-color: rgb(230, 230, 230);
}
</style>