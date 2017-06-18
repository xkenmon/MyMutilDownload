package cn.xkenmon.MutilDownload.Util;

/**
 * logUtil
 * Created by mxk94 on 2017/6/13.
 */
public class logUtil {
    public static void log(Object object){
        System.err.println("Err: "+object);
    }
    public static void log(String string){
        System.err.println("Err: "+string);
    }
    public static void info(Object object){
        System.out.println("Info: "+object);
    }
    public static void info(String string){
        System.out.println("Info: "+string);
    }

}
