<template>
    <div id="colorful_bubbles" v-bind="$attrs" @click="reductionHighlight()">
        <!-- id:a标签跳转索引 -->
        <div v-for="(msg, index) in msgEX" :id="msg.source.toString()" :key="msg.id.toString()" :time="msg.msgSendTime"
            :source="msg.source" :set="(msgContent = JSON.parse(msg.msgContent))">
            <div v-if="index !== 0 && msg.source - msgEX[index - 1].source > 1" class="missing">
                缺失{{ msg.source - msgEX[index - 1].source - 1 }}条消息
            </div>
            <div v-if="index !== 0
                && new Date(msg.msgSendTime).getTime() - new Date(msgEX[index - 1].msgSendTime).getTime() > 300000"
                class="time">
                {{ smartDateFormat(msg.msgSendTime) }}
            </div>
            <div v-if="msg.recallTime">
                <div v-if="msg.operatorName" class="recall">管理员{{ msg.operatorName }}撤回了一条成员消息</div>
                <div v-else class="recall">{{ msg.authorName }}撤回了一条消息</div>
            </div>
            <nav v-else>
                <!-- <div class="recall" v-if="msg.recallTime">撤回</div> -->
                <div class="no-recall">
                    <!-- <p v-show="msg.quoteMsg || !onlyOneImage(msgContent)"></p> -->
                    <img class="profile" :src="`http://q1.qlogo.cn/g?b=qq&nk=${msg.qqCode}&s=640`" />
                    <div class="main">
                        <div class="user">{{ msg.senderName }}</div>
                        <!-- 根据是否存在引用消息设置不同的边距 -->
                        <!-- 再根据是否只有图片消息判断有没有边距 -->
                        <div class="msg" :style="msg.quoteMsg
                            ? 'padding:0 13px 13px 13px'
                            : onlyOneImage(msgContent)
                                ? { 'background-color': 'transparent', padding: '4px' }
                                : 'padding: 13px'
                            ">
                            <div class="quote" v-if="msg.quoteMsg">
                                <div class="quote_info">
                                    <div class="quote_name">{{ msg.quoteName }}</div>
                                    <div class="quote_msg_send_time">
                                        {{ smartDateFormat(msg.quoteMsgSendTime) }}
                                    </div>
                                    <a class="icon--1" @click.stop="go(msg.quote.toString())"></a>
                                </div>
                                <div class="quote_msg">
                                    <MsgBlock :msgContent="JSON.parse(msg.quoteMsg)" :mode="'quote'" />
                                </div>
                            </div>
                            <MsgBlock :msgContent="msgContent" />
                        </div>
                    </div>
                </div>
            </nav>
        </div>
    </div>
    <component is="style">
        /* 跨组件的样式 */
        @media (max-aspect-ratio: 1/1) {
        .msg img {
        max-height: 45vw;
        max-width: 45vw;
        }
        }

        @media (min-aspect-ratio: 1/1) {
        .msg img {
        max-height: 45vh;
        max-width: 45vh;
        }
        }
    </component>
</template>

<script setup lang="ts">
import { smartDateFormat, go } from '../util'
import MsgBlock from './MsgBlock.vue'

defineProps<{ msgEX: MassageList }>()

declare const msgContent: MessageChain

function onlyOneImage(msgContent: MessageChain) {
    let count = 0
    for (const msgBlock of msgContent) {
        for (const key of Object.keys(msgBlock)) {
            if (key != 'image') {
                return false
            } else {
                count++
            }
        }
    }
    return count === 1
}

function reductionHighlight() {
    for (const element of document.querySelectorAll('nav')) {
        element.style.backgroundColor = ''
    }
}
</script>

<style scoped>
@import url('../assets/style.css');

.time {
    font-size: 10px;
    text-align: center;
    color: #7f7f7f;
}

.missing {
    height: 15px;
    font-size: 15px;
    text-align: center;
    color: #7f7f7f;
    background-color: #bfa;
}

nav {
    margin-left: 14px;
    padding-right: calc(3.5% - 14px);
}

.no-recall {
    display: flex;
    /* 处理边界重叠 */
    /* 防止覆盖上层padding-right */
    padding-top: 7px;
    padding-bottom: 7px;
}

.profile {
    display: block;
    height: 30px;
    width: 30px;
    border-radius: 50%;
    margin-right: 12px;
}

.main {
    display: flex;
    flex-direction: column;
}

.user {
    position: absolute;
    font-size: 10px;
    color: rgb(127, 127, 127);
}

.msg {
    margin-top: 16px;
}

.PC .msg {
    border-radius: 5px;
    background-color: rgb(229, 229, 229);
}

.A .msg {
    border-radius: 10px;
    background-color: #ffffff;
}

p {
    width: 6px;
    height: 10px;
    position: absolute;
    left: 36px;
    top: 34px;
    background-size: 100%;
}

.PC p {
    background-image: url(../assets/0.png);
}

.A P {
    background-image: url(../assets/1.png);
}
</style>
