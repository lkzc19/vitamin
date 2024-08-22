import type {LocationQueryValue} from "vue-router";

export const parentPath = (path: string) => {
    if (path == "/") {
        return path;
    }
    let list = path.split("/")
    list = list.filter(it => it != "")
    list.pop()
    if (list.length == 0) {
        return "/"
    } else {
        return "/" + list.join("/") + "/";
    }
}

export const toNumber = (value: LocationQueryValue | LocationQueryValue[], defaultNum: number = 1) => {
    if (Array.isArray(value)) {
        return defaultNum
    } else if (value == null) {
        return defaultNum
    } else {
        return parseFloat(value as string);
    }
}

export const getFilename = (fullPath: String | undefined) => {
    if (fullPath == undefined || fullPath == "") return ""
    const split = fullPath.split("\\");
    return split[split.length - 1]
}

export const addSlant = (path: String) => {
    return  path + (path.endsWith("/") ? "" : "/")
}
