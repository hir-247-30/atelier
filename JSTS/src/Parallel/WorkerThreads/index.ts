import { Worker } from 'node:worker_threads';
import { fileURLToPath } from 'node:url';
import { resolve } from 'node:path';

// エントリポイントをtsxの実行ファイルにする
const tsxCli = fileURLToPath(
    new URL('file://' + require.resolve('tsx/cli')) // 実行フルパス
);

const params = {
    argv: [
        resolve('./src/Parallel/WorkerThreads/threads.ts'), // tsx の引数
        'threadMethod', // 別スレッドで実行するメソッド名
        'OK!' // メソッドの引数
    ], 
}

const worker = new Worker(tsxCli, params);

worker.on('message', (message) => {
    console.log(message);
});