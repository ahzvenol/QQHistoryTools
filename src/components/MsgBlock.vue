<template>
    <div>
        <div v-for="msgBlock in msgContent" class="msg_block" :key="msgBlock.toString()"
            :set="((msg = msgBlockFormat(msgBlock)), (type = Object.keys(msgBlock)[0]))">
            <span v-if="type == 'text'">
                <span v-if="mode == 'quote'">{{ msg }}</span>
                <span v-else class="text" v-html="renderHref(msg as string)"></span>
            </span>
            <!-- todo: msg['isEmoji'] 表情和非表情显示大小不同 -->
            <div v-else-if="type == 'image'" v-viewer>
                <img v-if="(msg instanceof Object)" class="msg_img" :src="imageBlockFormat(msg)"
                    :style="msg['isEmoji'] ? '' : ''" @error="imageError($event, msg)" />
                <img v-else class="msg_img" :src="imageBlockFormat(msg)" />
            </div>
            <div v-else-if="type == 'at'" style="display: inline-block" :set="(msg = msg as ObjectMap<string>)">
                <div class="at" :title="msg['atName'] + '(' + msg['code'] + ')'">@{{ msg['atName'] }}</div>
            </div>
            <img v-else-if="type == 'face'" class="emoji" :src="'./static/' + msg + '.gif'" alt="此表情未收录ㅤ" />
            <span v-else>[暂时不支持查看此消息类型[{{ type }}]]</span>
        </div>
    </div>
</template>

<script setup lang="ts">
import axios from 'axios'

defineProps<{ msgContent: MessageChain, mode?: 'quote' }>()

declare const msg: ValueOf<SingleMessage>
declare const type: string

// 一个历史遗留问题的处理,新数据应该没有这个问题
function msgBlockFormat(msgBlock: SingleMessage) {
    let msgBlockTemp = Object.values(msgBlock)[0]
    if (JSON.stringify(msgBlockTemp) != '{}') {
        return msgBlockTemp
    } else {
        return ''
    }
}

//旧数据兼容
function imageBlockFormat(msg: ValueOf<SingleMessage>) {
    return msg instanceof Object
        ? msg['url'].startsWith('http://')
            ? msg['url']
            : `http://gchat.qpic.cn/gchatpic_new//--${msg['url']}/0`
        : msg
}

function imageError(event: Event, msg: ObjectMap<string>) {
    if (!msg['url'] || !msg['url'].startsWith('http://')) return
    (event.srcElement! as HTMLImageElement).src = axios.defaults.baseURL + '/QQChatRecord/image/' + msg['url'] + '.' + msg['imageType']
}

function renderHref(content: string) {
    if (!content) return ''
    const urlPattern = /(https?:\/\/|www\.)[a-zA-Z_0-9\-@]+(\.\w[a-zA-Z_0-9\-:]+)+(\/[()~#&\-=?+%/.\w]+)?/g
    content = content.replace(urlPattern, match => {
        const urlHttp = match.indexOf('http://') && match.indexOf('https://')
        const url = urlHttp === -1 ? match.split('/')[0] : match.split('/')[2]
        const href = urlHttp === -1 ? `https://${match}` : match
        return `<a target="_blank" href="${href}">${url}</a>`
    })
    return content
}

</script>

<style>
.text a:link,
.text a:hover,
.text a:active,
.text a:visited {
    color: #00a4ff;
}
</style>

<style scoped>
img {
    border-radius: 5px;
}

img:hover {
    /* 对第三方库增加功能 */
    cursor: zoom-in;
}

@media (max-aspect-ratio: 1/1) {
    .quote img {
        max-height: 13.5vw;
        max-width: 13.5vw;
    }
}

@media (min-aspect-ratio: 1/1) {
    .quote img {
        max-height: 13.5vh;
        max-width: 13.5vh;
    }
}

.msg_block {
    display: inline;
    /* text-align: center; */
}

.PC .at {
    color: #00a4ff;
}

.emoji {
    height: 30px;
    width: 30px;
    /* 与文字居中对齐 */
    vertical-align: middle;
}

/* .height_depend {
  display: inline-block;
  height: 13px;
} */
</style>