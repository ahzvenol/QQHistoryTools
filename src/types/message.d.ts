type json = string

type Massage = {
    id: number,
    source: number
    qqCode: string,
    senderName: string,
    msgContent: json,
    msgSendTime: string,
}

type Quote = {
    quote: number,
    quoteName: string
    quoteMsg: json,
    quoteMsgSendTime: string,
}

type Recall = {
    recallTime: string,
    operatorName?: string,
    authorName: string,
}

// 定义排除类型：将U从T中剔除, keyof 会取出T与U的所有键, 限定P的取值范围为T中的所有键, 并将其类型设为never
type Without<T, U> = { [P in Exclude<keyof T, keyof U>]?: never }

// 定义互斥类型，T或U只有一个能出现（互相剔除时，被剔除方必须存在）
type XOR<T, U> = (Without<T, U> & U) | (Without<U, T> & T)

type MassageList = Array<Massage & XOR<Quote, {}> & XOR<Recall, {}>>

type SingleMessage = ObjectMap<string | ObjectMap>

type MessageChain = Array<SingleMessage>