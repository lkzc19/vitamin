import type {LocationQueryValue} from "vue-router";

export const toNumber = (value: LocationQueryValue | LocationQueryValue[], defaultNum: number = 1) => {
    if (Array.isArray(value)) {
        return defaultNum
    } else if (value == null) {
        return defaultNum
    } else {
        return parseFloat(value as string);
    }
}
