function smartDateFormat(date: string) {
    const dateObject = new Date(date)
    if (dateObject.format('yyyyMMdd') == new Date().format('yyyyMMdd')) {
        return dateObject.format('hh:mm:ss')
    } else {
        return dateObject.format('yyyy/MM/dd hh:mm:ss')
    }
}

import { onPC } from '../store'

function sourceHighlight(quote: string) {
    if (onPC()) {
        document.getElementById(quote)!.style.backgroundColor = '#EAEAEA'
    } else {
        document.getElementById(quote)!.style.backgroundColor = '#DFE1E9'
    }
}

function go(quote: string) {
    document.getElementById(quote)!.scrollIntoView(true)
    sourceHighlight(quote)
}

export { smartDateFormat, sourceHighlight, go }