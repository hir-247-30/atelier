// TSではリリースされたがNodeがまだ未サポート
import defer * as val from './defer-import';

// このタイミングでは defer-import は読み込まれない
console.log('main呼び出し');

// ここの必要になったタイミングで呼び出される
console.log(val);