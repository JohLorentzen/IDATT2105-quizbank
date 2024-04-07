import router from "@/router/index.js";

export function isUserLoggedIn() {
    let twoHours = 7200000;
    let tokenTime = localStorage.getItem('tokentime');
    if (localStorage.getItem('token') && tokenTime && (new Date().getTime() - tokenTime) < twoHours) {
        return true;
    }
    router.push('/login-or-signup')
    return false;
}