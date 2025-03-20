// ---------------------------------------------------
// 1 noImplicitAny
// メソッドの引数の型を必ず指定させる
// ---------------------------------------------------

// Parameter 'anything' implicitly has an 'any' type.
function example1_FAIL (anything): void {
    console.log(anything);
}

// OK
function example1_SUCCESS (anything: unknown): void {
    console.log(anything);
}

// ---------------------------------------------------
// 2 strictNullChecks
// nullセーフになる
// ---------------------------------------------------

// Type 'null' is not assignable to type 'number'.
const example2_FAIL: number = null;

// OK
const example2_SUCCESS: number|null = null;

// ---------------------------------------------------
// 3 strictFunctionTypes
// 共変（型を広くする）は許可するが、反変（型を狭くする）は許さない
// ---------------------------------------------------

// 反変（型を狭くする）
// Type '(value: number) => void' is not assignable to type '(value: string | number) => any'.
let example3: (value: number|string) => any;
example3 = function (value: number): void {
    console.log(value.toFixed());
};

// 上記の場合、以下が実行されるとランタイムエラーを発生させてしまう
example3('文字列');

// 共変（型を広くする）はOK
example3 = function (value: number|string|null|undefined): void {
    // value.toFixed() を実行したい場合はきちんと型を絞った上で実装しないとエラー
    console.log(value);
};

// ---------------------------------------------------
// 4 strictBindCallApply
// bind call apply を使用した時に引数の型チェックを行う
// ---------------------------------------------------

function example4 (args: string): void {
    console.log(`${args.toUpperCase()}`);
}

// Argument of type 'number' is not assignable to parameter of type 'string'.
example4.bind(null, 100);
example4.call(null, 200);
example4.apply(null, 300);

// ---------------------------------------------------
// ５ strictPropertyInitialization
// クラス内で初期化されていないプロパティを禁止
// ---------------------------------------------------

// Property 'property1' has no initializer and is not definitely assigned in the constructor.
// Property 'property2' has no initializer and is not definitely assigned in the constructor.
// Property 'property3' has no initializer and is not definitely assigned in the constructor.
class example5_FAIL {
    property1: string
    property2: string
    property3: string

    constructor () { }
}

// OK
class example5_SUCCESS {
    property1: string = 'OK';
    property2: string
    property3: string|undefined

    constructor () {
        this.property2 = 'OK';
    }
}

// ---------------------------------------------------
// 6 strictBuiltinIteratorReturn
// イテレータをnext()で辿った時の型チェックを厳密にする
// ---------------------------------------------------

const example6: Iterator<string, BuiltinIteratorReturn> = ['one', 'two', 'three'].values();

// undefinedを踏んでしまうのでtoUpperCase()のところでランタイムエラーを起こしてしまう
// 'example6_FAIL.value' is possibly 'undefined'.
const example6_FAIL = example6.next();
console.log(example6_FAIL.value.toUpperCase());

// OK
const example6_SUCCESS = example6.next();
if (example6_SUCCESS.done) {
    // undefined 確定
    console.log(example6_SUCCESS.value);
} else {
    // string 確定
    console.log(example6_SUCCESS.value.toUpperCase());
}

// ---------------------------------------------------
// 7 noImplicitThis
// 関数内でthisを使う場合、明示的に型の指定をさせる
// ---------------------------------------------------

const exmaple7 = {
    name: 'name',
    greetFail: exmaple7_FAIL,
    greetSuccess: exmaple7_SUCCESS
}

// 'this' implicitly has type 'any' because it does not have a type annotation.
function exmaple7_FAIL () {
    console.log(`${this.name}`)
}

type example7 = {
    name: string
}

// 引数でthisの型を明示してあげればOK
function exmaple7_SUCCESS (this: example7): void {
    console.log(`${this.name}`)
}

// ---------------------------------------------------
// ８ useUnknownInCatchVariables
// try catch での例外補足時、catch節に渡る値をunknown型にする
// ---------------------------------------------------

// 'example8_FAIL' is of type 'unknown'.
try {
    throw 'ERROR';
} catch (example8_FAIL) {
    console.log(example8_FAIL.toUpperCase())
}

// OK
try {
    throw 'ERROR';
} catch (example8_SUCCESS) {
    if (typeof example8_SUCCESS === 'string') {
        console.log(example8_SUCCESS.toUpperCase())
    }
}

// ---------------------------------------------------
// 9 alwaysStrict
// グローバルにstrictモードを有効にする
// ---------------------------------------------------

// 例えば var let const なしでの暗黙的なグローバル変数の作成をエラーにする
// Cannot find name 'example9_FAIL'. Did you mean 'example1_FAIL'?
example9_FAIL = undefined;

// OK
const example9_SUCCESS = undefined;

// ---------------------------------------------------
// 10 noUnusedLocals
// 未使用のローカル変数をエラーにする
// ---------------------------------------------------

function example10_FAIL (): void {
    // 'unused' is declared but its value is never read.
    const unused = 'unused';
    console.log('unused');
}

function example10_SUCCESS (): void {
    console.log('OK');
}

// ---------------------------------------------------
// 11 noUnusedParameters
// 未使用の引数を禁止
// ---------------------------------------------------

// 'args2' is declared but its value is never read.
function example11_FAIL (args1: string, args2: string, args3: string): void {
    console.log(`${args1} ${args3}`);
}

// 使わないものは _ を先頭につけて表現すればOK
// ただ eslint ではエラー出ちゃうので注意
function example11_SUCCESS (args1: string, _args2: string, args3: string): void {
    console.log(`${args1} ${args3}`);
}

// ---------------------------------------------------
// 12 exactOptionalPropertyTypes
// オプショナルプロパティに明示的にundefinedを突っ込むのを禁止
// ---------------------------------------------------

type example12 = {
    property1: string
    property2?: string
}

// Type '{ property1: string; property2: undefined; }' is not assignable to type 'example12' with ...
const example12_FAIL: example12 = {
    property1: 'OK',
    property2: undefined
}

// OK
const example12_OK: example12 = {
    property1: 'OK'
}

// ---------------------------------------------------
// 13 noImplicitReturns
// 戻り値の型を厳密に評価する
// ---------------------------------------------------

// Function lacks ending return statement and return type does not include 'undefined'.
function example13_FAIL (args1: number): string {
    if (args1 > 0) {
        return 'OK';
    }
}

// OK
function example13_SUCCESS (args1: number): string {
    if (args1 > 0) {
        return 'OK';
    }
    return '';
}

// ---------------------------------------------------
// 14 noFallthroughCasesInSwitch
// switch文でbreak, returnが適切に使用されているか調べる
// ---------------------------------------------------

function example14_FAIL (args1: number): void {
    switch (args1) {
        case 1: // Fallthrough case in switch.
            console.log(args1);
        case 2: // Fallthrough case in switch.
            console.log(args1);
        case 3: // Fallthrough case in switch.
            console.log(args1);
        default:
            console.log(args1);
    }
}

// OK
function example14_SUCCESS (args1: number): void {
    switch (args1) {
        case 1:
            console.log(args1);
            break;
        case 2:
            console.log(args1);
            break;
        case 3:
            console.log(args1);
            return;
        default:
            console.log(args1);
    }
}

// ---------------------------------------------------
// 15 noUncheckedIndexedAccess
// 配列アクセス時の型はundefinedとのユニオン型とする
// ---------------------------------------------------

const example15: number[] = [1, 2, 3];

// Type 'number | undefined' is not assignable to type 'number'.
// Type 'undefined' is not assignable to type 'number'.
const example15_FAIL: number = example15[1];
// Object is possibly 'undefined'.
example15[1] += 1;

const example15_SUCCESS: number = example15[1] ?? 0;
example15[1] = example15[1] ?? 0 + 1;

// ---------------------------------------------------
// 16 noImplicitOverride
// オーバライドの明示的な宣言を強制する
// ---------------------------------------------------

class Example15_Parent {
    public example15_FAIL (): void {
        console.log('parent');
    }

    public example15_SUCCESS (): void {
        console.log('parent');
    }
}

class Example15_Child extends Example15_Parent {
    // This member must have an 'override' modifier because it overrides a member in the base class 'Example15_Parent'.
    public example15_FAIL (): void {
        console.log('child');
    }

    // OK
    public override example15_SUCCESS (): void {
        console.log('child');
    }
}

// ---------------------------------------------------
// 17 noPropertyAccessFromIndexSignature
// オブジェクトのプロパティが存在するか不明瞭な場合、プロパティアクセスをインデックス記法に強制する
// ---------------------------------------------------

type Example17 = {
    [key: string]: string;
  };

const example17: Example17 = {
    FAIL: 'NG',
    SUCCESS: 'OK',
}

// Property 'FAIL' comes from an index signature, so it must be accessed with ['FAIL'].
console.log(example17.FAIL);

console.log(example17['SUCCESS']);

// ---------------------------------------------------
// 18 allowUnusedLabels
// falseにしないと起動しない
// 使用されていないラベルにエラーを出すか
// ---------------------------------------------------

example18_SUCCESS: for (let i = 0; i < 5; i++) {
    if (i === 1) {
        continue example18_SUCCESS;
    }
    if (i === 2) {
        // Unused label.
        example18_FAIL: true;
    }
    console.log(i);
}

// ---------------------------------------------------
// 19 allowUnreachableCode
// falseにしないと起動しない
// 未到達コードにエラーを出す
// ---------------------------------------------------

function example19 (args1: number): number {
    if (args1 > 0) {
        return args1 + 1;
    } else {
        return args1 + 2;
    }
    // Unreachable code detected.
    return args1 + 3;
}