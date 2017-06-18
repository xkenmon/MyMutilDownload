package cn.xkenmon.MutilDownload;

import cn.xkenmon.MutilDownload.Util.logUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URLConnection;
import java.util.List;

/**
 * Download Process
 * Created by mxk94 on 2017/6/13.
 */
public class DownloadThread implements Runnable {
    private long start;
    private long end;
    private static int memCache = 2;
    private final DownloadInfo info;
    private List list;

    DownloadThread(DownloadInfo info, long start, long end,List list) {
        if (end < start)
            logUtil.log(end + "<" + start);
        this.info = info;
        this.end = end;
        this.start = start;
        this.list = list;
    }

    static void setMemCache(int MemCache) {
        memCache = MemCache;
    }

    @Override
    public void run() {
        try {
            logUtil.info(getRange() + "----------Start!");

            URLConnection connection = info.getUrl().openConnection();
            connection.setRequestProperty("Range", "bytes=" + start + "-" + end);

            try (RandomAccessFile randomAccessFile = new RandomAccessFile(info.getSaveFile(), "rw")) {
                randomAccessFile.seek(start);

                byte[] buf = new byte[memCache * 1024 * 1024];

                try (InputStream inputStream = connection.getInputStream()) {
                    int len;
                    while ((len = inputStream.read(buf)) != -1) {
                        randomAccessFile.write(buf, 0, len);
                        synchronized (info) {
                            info.speedSize += len;
                        }
                    }
                    logUtil.info("\n" + getRange() + "\n-----OK!------\n" + this + "\n---------------");
                } catch (IOException e) {
                    logUtil.log("Can't open InputStream");
                    logUtil.log(e.getMessage());
                }
                randomAccessFile.close();
                list.remove(this);
            }
        } catch (IOException e) {
            logUtil.log(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return info +
                "\nrange: " + start + "-" + end;
    }

    private String getRange() {
        return "range: " + DownloadInfo.readSize(start) + "-" + DownloadInfo.readSize(end);
    }
}
