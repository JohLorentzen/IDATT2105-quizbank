import router from "@/router/index.js";

export function isUserLoggedIn() {
    if (!localStorage.getItem('token')) {
        router.push('/login-or-signup')
    }
}