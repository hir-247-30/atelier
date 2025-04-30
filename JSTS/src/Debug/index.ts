// ブレークポイント等を試す用
const debug = 'tsx + VSCode + Debuggerの動作確認用ファイル';

console.log(debug);

const debugObj = {
    name: 'debug object',
    show: true,
}

if (debugObj.show) {
    const name = debugObj.name;
    console.log(name);
}