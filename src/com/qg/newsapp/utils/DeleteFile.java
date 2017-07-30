package com.qg.newsapp.utils;

import java.io.File;

public class DeleteFile {
    public static void delete(String filePath){
        File file = new File(filePath);
        //假如这是一个目录，也就是说是一个文件夹，遍历文件夹中的文件将它删除
        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (int i=0;i<files.length;i++){
                files[i].delete();
            }
            //最后将目录删除
            file.delete();
        }else
            file.delete();
    }
}
