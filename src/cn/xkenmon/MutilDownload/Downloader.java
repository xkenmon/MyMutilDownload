package cn.xkenmon.MutilDownload;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * main
 * Created by mxk94 on 2017/6/13.
 */
public class Downloader {
    public static void main(String[] args) throws IOException {
        String downloadUrl;
        String saveFilePath;
        String threadSize;
        int threadSize_int;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter download URL: ");
        downloadUrl = bufferedReader.readLine();
        if (downloadUrl == null) {
            System.out.println("not enter url\n");
            System.exit(1);
        }

        System.out.println("Enter save path: ");
        saveFilePath = bufferedReader.readLine();

        String filename = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
        if (saveFilePath.equals(""))
            saveFilePath = new File("").getAbsolutePath()+File.separator + filename;
        else saveFilePath = saveFilePath + File.separator + filename;

        System.out.println("Enter thread size(MB)(default: 30): ");
        threadSize=bufferedReader.readLine();
        if (threadSize.equals(""))
            threadSize_int = 30;
        else
            threadSize_int = Integer.parseInt(threadSize);


        try {

            new Thread(new Download(downloadUrl, saveFilePath,8,threadSize_int)).start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
