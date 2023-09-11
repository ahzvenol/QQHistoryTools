import { createRouter, createWebHashHistory } from "vue-router"

const router = createRouter({
    history: createWebHashHistory(),
    routes: [

    ],
})

router.beforeEach((to, from, next) => {
    next()
})

export default router