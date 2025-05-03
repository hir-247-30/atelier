// ここにあらかじめ別スレッドで処理したいメソッドを定義する
// 呼び出し側でそのうちどれを起動するかを選ぶ
const actions = new Map([
    ['threadMethod', (arg: string) => threadMethod(arg)],
    ['somethingMethod', (arg: string) => somethingMethod(arg)]
])
  
function execWorker (): void {
    const action = process.argv[2];
    const arg = process.argv[3];

    if (!action || !arg) return;

    const method = actions.get(action);

    if (method === undefined) return;

    method(arg);
}

function threadMethod (arg: string) {
    console.log(arg);
}

function somethingMethod (arg: string) {
    console.log(arg);
}

execWorker();