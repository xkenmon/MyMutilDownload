package cn.xkenmon.MutilDownload;

import cn.xkenmon.MutilDownload.Util.logUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;

/**
 * Download Class
 * Created by mxk94 on 2017/6/14.
 */
public class Download implements Runnable {

    private DownloadInfo info;
    private int threadCount;
    private int threadSize;

    Download(String downloadUrl, String saveFilePath, int threadCount, int threadSize) throws Exception {
        URL url = new URL(downloadUrl);
        File file = new File(saveFilePath);
        info = new DownloadInfo(url, file);
        this.threadCount = threadCount;
        this.threadSize = threadSize;
    }

    Download(String url, String file) throws Exception {
        this(url, file, 8, 30);
    }

    @Override
    public void run() {
        logUtil.info(info);
        ThreadPool threadPool = null;
        if (info.getSaveFile().exists())
            logUtil.info(info.getSaveFile() + " is exist!");
        else {
            try {
                if (info.getSaveFile().createNewFile())
                    logUtil.info("file is created.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            threadPool = new ThreadPool(info, threadCount, threadSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (threadPool == null)
            try {
                logUtil.log("Can't create thread pool");
                throw new Exception("Can't create thread pool");
            } catch (Exception e) {
                e.printStackTrace();
            }
        ExecutorService pool = null;
        if (threadPool != null) {
            pool = threadPool.getPool();
        }
        while (true) {
            if (pool != null && pool.isTerminated()) {
                if (info.downloaded < info.getFileLength()) {
                    logUtil.log("downloaded size: " + info.downloaded + "  file length: " + info.getFileLength());
                    logUtil.log("File is missing!!");
//                    while (!threadPool.getThreadList().isEmpty() && )
                } else
                    logUtil.info("Download Succeeded");
                System.exit(0);
                break;
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
