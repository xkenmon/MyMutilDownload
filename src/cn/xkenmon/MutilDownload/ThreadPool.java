package cn.xkenmon.MutilDownload;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * thread Pool
 * Created by mxk94 on 2017/6/13.
 */
class ThreadPool {
    private ExecutorService pool;
    private List<DownloadThread> threadList;

    ThreadPool(DownloadInfo downloadInfo,int threadCount,int size) throws Exception {
        threadList = new ArrayList<>();
        pool = Executors.newFixedThreadPool(threadCount);
        getSpeed speed = new getSpeed(downloadInfo);
        for (int i = 0; i < downloadInfo.getFileLength(); i += 1024 * 1024 * size) {
            DownloadThread thread = new DownloadThread(downloadInfo, i, i + 1024 * 1024 * size,threadList);
            pool.submit(thread);
            threadList.add(thread);
        }
        new Thread(speed).start();
        pool.shutdown();
    }

    ThreadPool(ArrayList<DownloadThread> list,int threadCount){
        pool = Executors.newFixedThreadPool(threadCount);
        for (DownloadThread thread:list){
            pool.submit(thread);
        }
        pool.shutdown();
    }

    public List<DownloadThread> getThreadList() {
        return threadList;
    }

    ExecutorService getPool() {
        return pool;
    }

}
