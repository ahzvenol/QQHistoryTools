<template>
    <div :class="onPC() ? 'PC' : 'A'">
        <!-- 使用<template></template>会奇怪的失去响应式 -->
        <div v-if="JSON.stringify(messageData()) !== '[]'">
            <ColorfulBubbles v-if="colorfulBubbles()" :msgEX="messageData()" />
            <NoBubbles v-else :msgEX="messageData()" />
        </div>
        <RightMenu />
    </div>
</template>

<script setup lang="ts">
import axios from 'axios'
import { Reactive, useReactive } from 'micro-reactive'
import ColorfulBubbles from './components/ColorfulBubbles.vue'
import NoBubbles from './components/NoBubbles.vue'
import RightMenu from './components/RightMenu.vue'
import { colorfulBubbles, onPC } from './store'

const messageData = useReactive([]) as Reactive<MassageList>

// tag:主要是测试使用
messageData(JSON.parse(localStorage.getItem("msgEX") || "[]"))

// fix:如何前后端联动的配置key?
const key = import.meta.env.VITE_KEY || ""

axios
    .post(key + '/getMsgEx', { startIndex: 1, length: 10 })
    // .post('/getMsgEx')
    .then(res => {
        localStorage.setItem("msgEX", JSON.stringify(res.data))
        messageData(res.data)
    })
    .catch(err => {
        console.error(err)
    })
</script>

<style>
@import url(./assets/reset.css);

/* fix:行间距13px */
body * {
    /* user-select: none;
  cursor: default; */
    word-break: break-all;
    /* 处理换行 */
    white-space: pre-wrap;
}

nav {
    position: relative;
    width: 96.5%;
    padding-right: 3.5%;
    font-size: 15px;
}
</style>

<style>
/* quote样式可复用,向下穿透 */
.quote {
    font-size: 14px;
    padding: 4px 10px 10px 10px;
    border-radius: 1.5px;
    margin: 7px 0;
}

.PC .quote {
    background-color: #d6d6d7;
}

.A .quote {
    background-color: #ebeced;
    border-radius: 5px;
}

/* 这可能用不到浮动,但懒改 */
.quote_info {
    margin-bottom: 4px;
}

.quote_info>* {
    display: inline-block;
}

.quote_msg_send_time {
    margin: 0 13px;
}

.icon--1 {
    float: right;
}
</style>
