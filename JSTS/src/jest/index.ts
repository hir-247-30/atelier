export function square (args: { width: number, height: number}): number {
    const { width, height } = args;
    return width * height;
}

export function error (): never {
    throw new Error('エラー');
}

export async function asyncFunc (): Promise<string> {
    return Promise.resolve('ok');
}