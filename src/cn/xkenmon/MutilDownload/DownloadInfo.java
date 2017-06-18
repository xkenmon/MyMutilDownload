package cn.xkenmon.MutilDownload;

import cn.xkenmon.MutilDownload.Util.logUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * info bean
 * Created by mxk94 on 2017/6/13.
 */
public class DownloadInfo {
    private long fileLength;
    private URL url;
    private File saveFile;
    long speedSize = 0;
    long downloaded = 0;

    DownloadInfo(URL url, File saveFile) {
        this.saveFile = saveFile;
        this.url = url;
        this.fileLength = getFileLength();
    }

    long getFileLength() {
        if (fileLength != 0) {
            return fileLength;
        }

        URLConnection connection = null;
        try {
            connection = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (connection != null) {
            fileLength = connection.getContentLengthLong();
        }
        if (fileLength == -1){
            logUtil.log("can not get url-file,length: "+fileLength);
            System.exit(1);
        }
        return fileLength;
    }

    File getSaveFile() {
        return saveFile;
    }

    URL getUrl() {
        return url;
    }

    public void setSaveFile(File saveFile) {
        this.saveFile = saveFile;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    static String readSize(double size) {
        String[] unit = {" B", " KB", " MB", " GB"};
        String currentUnit;
        if (size <= 1024)
            currentUnit = unit[0];
        else if (size <= 1024 * 1024) {
            size = (size / 1024.0);
            currentUnit = unit[1];
        } else if (size <= 1024 * 1024 * 1024) {
            size /= 1024.0 * 1024.0;
            currentUnit = unit[2];
        } else {
            size /= 1024.0 * 1024.0 * 1024.0;
            currentUnit = unit[3];
        }
        String size_str = Double.toString(size).substring(0, Double.toString(size).indexOf(".")+2);
        return size_str + currentUnit;
    }

    String getCompleteDegree(){
        int f=2;
        double degree = (double)downloaded/(double)fileLength;
        if (degree != 0)
            f=3;
        String degree_str = Double.toString(degree*100).substring(0,Double.toString(degree).lastIndexOf(".")+f);
        return degree_str+" %";
    }

    public String toString() {
        return "Download URL: " + url +
                "\nSave Path: " + saveFile.getAbsolutePath() +
                "\nFile Size: " + fileLength;
    }
}
