import { instance } from "@/util/request.js"

export const ping = () => {
    return instance({
        url: "/ping",
        method: "get",
    });
};

export const foo = (data) => {
    return instance({
        url: "/foo",
        method: "post",
        data: data
    });
};

export const corsBug = (data) => {
    return instance({
        url: "/cors-bug",
        method: "post",
        data: data
    });
};