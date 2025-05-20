// 実行にはts-jestをインストールす必要あり
import { square, error, asyncFunc } from './index'

let store: {
    data: string[] | null
};

// 前処理
beforeEach(() => {
    store = { data: [] };
});
// 後処理
afterEach(() => {
    store = { data: null };
});

describe('グループ化', () => {
    test('success1', () => {
        expect(square({ width: 5, height: 10 })).toBe(50);
    });
    test('success2', () => {
        expect(square({ width: 10, height: 10 })).toBe(100);
    });
    test('success3', () => {
        expect(square({ width: 5, height: 5 })).toBe(25);
    });
});

test('base test', () => {
    // オブジェクトの比較
    expect({ test: 'jest' }).toEqual({ test: 'jest' });

    // 真偽値
    expect(true).toBeTruthy();
    expect(false).toBeFalsy();

    // nullとundefined
    expect(null).toBeNull();
    expect(undefined).toBeUndefined();

    // 数値比較
    expect(4).toBeGreaterThan(3);
    expect(4).toBeLessThan(5);
    expect(0.1 + 0.2).toBeCloseTo(0.3);

    // 配列
    const jestArray = ['one', 'two', 'three'];  
    expect(jestArray).toContain('two');
    expect(jestArray).toHaveLength(3);

    expect(() => error()).toThrow();
    expect(() => error()).toThrow('エラー');
});

test('mock', () => {
    // モック
    const mock = jest.fn();
    mock();
    mock();
    mock('MOCK');
    
    // 呼び出し回数
    expect(mock).toHaveBeenCalledTimes(3);
    // 特定の引数での呼び出し
    expect(mock).toHaveBeenCalledWith('MOCK');
    
    // モック関数に戻り値を設定
    const mockRet = jest.fn().mockReturnValue('モック返り値');
    expect(mockRet()).toBe('モック返り値');
});

test('async', async () => {
    // 非同期
    const asyncData = await asyncFunc();
    expect(asyncData).toBe('ok');
});

test('store', async () => {
    store?.data?.push('データ')
    expect(store?.data).toContain('データ');
});

test.skip('skip', () => {
    // スキップすべき未FIXのものとか
    expect(false).toBeTruthy();
});

// test.only('only', () => {
//     // これしか実行されなくなる
//     // 他のものは「skip」判定となる
//     expect(true).toBeTruthy();
// });