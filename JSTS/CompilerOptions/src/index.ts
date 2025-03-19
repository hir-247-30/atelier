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

// 以下が実行されるとランタイムエラーを発生させてしまう
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