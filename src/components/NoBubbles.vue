<template>
    <div id="no_bubbles" v-bind="$attrs">
        <nav :id="msg.source.toString()" v-for="msg in msgEX" :key="msg.id">
            <div class="massage_info">
                {{ msg.senderName }}&nbsp;&nbsp;&nbsp;{{ new Date(msg.msgSendTime).format('yyyy/MM/dd hh:mm:ss') }}
            </div>
            <!-- quote部分没有什么变化,没需求先不拆分组件了,增加复杂度 -->
            <div class="quote" v-if="msg.quoteMsg">
                <div class="quote_info">
                    <div class="quote_name">{{ msg.quoteName }}</div>
                    <div class="quote_msg_send_time">
                        {{ smartDateFormat(msg.quoteMsgSendTime) }}
                    </div>
                    <a class="icon--1" @click.stop="go(msg.quote.toString())"></a>
                </div>
                <div class="quote_msg">
                    <MsgBlock :msgContent="JSON.parse(msg.quoteMsg)" />
                </div>
            </div>
            <MsgBlock :msgContent="JSON.parse(msg.msgContent)" />
            <!-- <div class="msg_block" v-for="msgBlock in JSON.parse(msg.msgContent)" :key="msgBlock + getUuid()"> -->
            <!-- <span v-if="Object.keys(msgBlock)[0] != 'image'">{{ $parent.msgBlockFormat(msgBlock) }}</span> -->
            <!-- <img class="msg_img" v-else :src="$parent.msgBlockFormat(msgBlock)" /> -->
            <!-- </div> -->
        </nav>
    </div>
    <component is="style">
            .msg_block {
                margin-left: 16px;
            }

            .msg_block>img {
                border-radius: 5px;
                margin-left: -16px;
            }

            @media (max-aspect-ratio: 1/1) {
                #no_bubbles img {
                    max-height: 62.5vw;
                    max-width: 62.5vw;
                }
            }

            @media (min-aspect-ratio: 1/1) {
                #no_bubbles img {
                    max-height: 62.5vh;
                    max-width: 62.5vh;
                }
            }
    </component>
</template>

<script setup lang="ts">
import { smartDateFormat, go } from '../util'
import MsgBlock from './MsgBlock.vue'

defineProps<{ msgEX: MassageList}>()
</script>

<style scoped>
#no_bubbles {
    padding-left: 13px;
}

nav {
    margin-left: 16px;
    margin-bottom: 4px;
    box-sizing: border-box;
}

.massage_info {
    font-size: 13px;
    color: #0000ff;
}

.quote {
    width: fit-content;
}
</style>
