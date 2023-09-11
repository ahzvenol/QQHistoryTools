type ValueOf<T> = T[keyof T]

declare type ObjectMap<T = any> = { [key: string]: T }

interface Date {
    format(fmt: string): string
}