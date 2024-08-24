
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

export const getFilename = (fullPath: String | undefined) => {
    if (fullPath == undefined || fullPath == "") return ""
    const split = fullPath.split("\\");
    return split[split.length - 1]
}

export const addSlant = (path: String) => {
    return  path + (path.endsWith("/") ? "" : "/")
}
