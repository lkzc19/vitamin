
export interface Page<T> {
    pageC: number,
    pageS: number,
    pageT: number,
    items: T[],
}

export interface Info {
    fileCount: number,
}

export interface FileMeta {
    id: number,
    name: string,
    ext: string,
    isDir: boolean,
    size: number
}
