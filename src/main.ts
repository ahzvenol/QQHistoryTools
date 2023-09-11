import { createApp, ReactiveEffect } from 'vue'
import axios from 'axios'
import { useEffect } from 'micro-reactive'
import { micro } from './directives/micro'
import Viewer from 'v-viewer'
import './assets/viewer.css'

import App from './App.vue'
import router from "./router"

// hack ReactiveEffect
const hackRun = ReactiveEffect.prototype.run
ReactiveEffect.prototype.run = function () {
    return useEffect(hackRun.bind(this))
}

//@ts-ignore
window.require = (str: string) => new URL(str, import.meta.url).href

axios.defaults.baseURL = import.meta.env.VITE_BASE_URL || window.location.protocol + "//" + window.location.host

Date.prototype.format = function (fmt) {
    let o = {
        'M+': this.getMonth() + 1, //月份
        'd+': this.getDate(), //日
        'h+': this.getHours(), //小时
        'm+': this.getMinutes(), //分
        's+': this.getSeconds(), //秒
        'q+': Math.floor((this.getMonth() + 3) / 3), //季度
        S: this.getMilliseconds(), //毫秒
    }
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length))
    for (let k in o)
        if (new RegExp('(' + k + ')').test(fmt))
            //@ts-ignore
            fmt = fmt.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length))
    return fmt
}

createApp(App)
    .use(router)
    .use(Viewer, {
        defaultOptions: {
            inline: false,  //默认值：false。启用内联模式。
            button: false,  //在查看器的右上角显示按钮。
            navbar: false,  //指定导航栏的可见性。
            title: true,  // 指定标题的可见性和内容。
            toolbar: false,  //指定工具栏及其按钮的可见性和布局。
            tooltip: true,  //放大或缩小时显示带有图像比率（百分比）的工具提示。
            movable: true,  //启用以移动图像。
            zoomable: true,  //启用以缩放图像
            rotatable: true,  //启用以旋转图像
            scalable: true,  //启用以缩放图像。
            transition: true,  //为某些特殊元素启用CSS3转换。
            fullscreen: true,  //启用以在播放时请求全屏。
            keyboard: true,  //启用键盘支持。
            // url: 'src',  //默认值：'src'。定义获取原始图像URL以供查看的位置。
        }
    })
    .directive('micro', micro)
    .mount('#app')
