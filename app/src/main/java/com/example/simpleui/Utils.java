package com.example.simpleui;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2015/11/30.
 */
//寫檔
public class Utils {
    public static void writeFile(Context context, String fileName, String content) {
        //try的內容若true就執行，否則執行catch
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_APPEND);
            //MODE_APPEND：檢查檔案是否存在，否則寫新檔
            fos.write(content.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();//印出錯的err message
            //IO的例外情況
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //讀檔
    public static String readFile(Context context, String fileName){
                try {
                        FileInputStream fis = context.openFileInput(fileName);
                        byte[] buffer = new byte[1024];//buffer存資料用
                        fis.read(buffer, 0, buffer.length);
                        fis.close();
                        return new String(buffer);//把buffer轉string型態
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                return null;//若讀錯就回傳空值
            }
}



