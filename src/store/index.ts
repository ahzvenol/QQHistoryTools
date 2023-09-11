import { useReactive } from "micro-reactive"

const colorfulBubbles = useReactive(true)

const onPC = useReactive(!/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent))

export { colorfulBubbles, onPC }