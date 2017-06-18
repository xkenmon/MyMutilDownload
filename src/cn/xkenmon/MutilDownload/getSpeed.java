package cn.xkenmon.MutilDownload;

import cn.xkenmon.MutilDownload.Util.logUtil;

/**
 * speed
 * Created by mxk94 on 2017/6/13.
 */
public class getSpeed implements Runnable {
    private final DownloadInfo info;
    private boolean stopFlag;

    getSpeed(DownloadInfo info) {
        this.info = info;
    }

    @Override
    public void run() {
        while (!stopFlag) {
            logUtil.info("Speed: " + DownloadInfo.readSize(info.speedSize)+"/S"+ "---"+info.getCompleteDegree());
            synchronized(info) {
                info.downloaded += info.speedSize;
                info.speedSize = 0;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void stop() {
        stopFlag = true;
    }
}
