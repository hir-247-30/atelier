import cluster from 'node:cluster';
import { cpus } from "os";
import { Hono } from 'hono';
import { serve } from '@hono/node-server';

const CPU_CORE_NUMS = cpus().length;
const BASE_PORT = 3000;

if (cluster.isPrimary) {  
    // ワーカープロセスを生成
    for (let i = 0; i < CPU_CORE_NUMS; i++) {
        // ポートの競合を避けるため、ポート番号をインクリメントして子プロセスに渡す
        const workerPort = BASE_PORT + i;
        cluster.fork({ WORKER_PORT: workerPort.toString() });
    }
} else {
    // 子プロセスで実行
    const hono = new Hono();
    hono.get('/', (c) => c.text('OK!'));

    // 親から渡されたポート番号を展開
    const workerPort = parseInt(process.env['WORKER_PORT'] || '3000');
    const server = serve({ ...hono, port: workerPort }, () => 
        console.log(`ワーカープロセス ${process.pid} がポート ${workerPort} で起動中。`)
    );

    const gracefulShutdown = function (): void {
        server.close(function () {
            console.log(`ワーカープロセス ${process.pid} が終了中。`);
            process.exit();
        });
    };

    process.on('SIGTERM', gracefulShutdown);
    process.on('SIGINT', gracefulShutdown);
    process.on('SIGHUP', gracefulShutdown);
}