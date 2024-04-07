import router from "@/router/index.js";

export function isUserLoggedIn() {

    if (localStorage.getItem('token')){
        return true;
    }
    router.push('/login-or-signup')
    return false;
}