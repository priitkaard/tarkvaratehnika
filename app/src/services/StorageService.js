export const LOCAL_STORAGE = {
    AUTH_TOKEN: 'authToken',
    USERNAME: 'username',
};

export function storageSet(key, value) {
    localStorage.setItem(key, value);
}

export function storageGet(key) {
    return localStorage.getItem(key);
}

export function storageRemove(key) {
    localStorage.removeItem(key);
}
