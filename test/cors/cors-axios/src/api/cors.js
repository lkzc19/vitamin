import { instance } from "@/util/request.js"

export const ping = () => {
    return instance({
        url: "/ping",
        method: "get",
    });
};

export const normal = (data) => {
    return instance({
        url: "/normal",
        method: "post",
        data: data
    });
};

export const xNahida = (data) => {
    return instance({
        url: "/XNahida",
        method: "post",
        data: data,
        headers: {"X-Nahida": "nahida"}
    });
};

export const xHutao = (data) => {
    return instance({
        url: "/XHutao",
        method: "post",
        data: data,
        headers: {"X-Hutao": "hutao"}
    });
};

export const corsBug = (data) => {
    return instance({
        url: "/cors-bug",
        method: "post",
        data: data
    });
};