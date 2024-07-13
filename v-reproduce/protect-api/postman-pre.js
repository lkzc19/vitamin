const cryptojs = require('crypto-js')


// X-Timestamp
const timestamp = new Date().getTime().toString();
pm.globals.set("X-Timestamp", timestamp);

// X-Nonce
let nonce = '';
for (let i = 0; i < 4; i++) {
    nonce += Math.floor(Math.random() * 10);
}
pm.globals.set("X-Nonce", nonce);

// X-Sign
const params = new Map();
// body
const requestBody = JSON.parse(pm.request.body.raw)
Object.entries(requestBody).forEach(([key, value]) => {
    params.set(key, value);
});
// query
const queryParamEntries = pm.request.url.getQueryString().split("&")
for (const entry of queryParamEntries) {
    const tmp = entry.split("=")
    if (tmp[1] == undefined) {
        continue;
    }
    params.set(tmp[0], tmp[1])
}
// 排序
const sortedParams = Array.from(params.entries()).sort((a, b) => a[0].localeCompare(b[0]));
// 拼接
let sign = nonce + timestamp;
let sortedParamsStr = ''
for (const [key, value] of sortedParams) {
    sortedParamsStr += `"${key}":"${value}",`;
}
sign += `{${sortedParamsStr.slice(0, -1)}}`
console.log(sign)
sign = cryptojs.MD5(sign).toString().toUpperCase()
console.log(sign)
pm.globals.set("X-Sign", sign);