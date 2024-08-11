
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
