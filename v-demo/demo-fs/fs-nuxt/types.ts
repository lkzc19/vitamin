
export interface Page<T> {
    pageC: number,
    pageS: number,
    pageT: number,
    items: T[],
}

export interface FileMeta {
    name: string,
    ext: string,
    isDir: boolean
}
