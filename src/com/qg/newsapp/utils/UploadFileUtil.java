package com.qg.newsapp.utils;

import java.util.UUID;

/**
 * 文件上传工具类
 */
public class UploadFileUtil {

    /**
     * 切掉filename路径
     *
     * @param filename 本地路径+文件名
     * @return 文件名
     */
    public static String subFileName(String filename) {
        if (filename.contains("\\")) {
            return filename.substring(filename.lastIndexOf("\\"));
        }
        return filename;
    }

    /**
     * 生成唯一文件名
     *
     * @param filename 文件名
     * @return 唯一的文件名
     */
    public static String generateUUIDName(String filename) {
        String ext = filename.substring(filename.lastIndexOf("."));
        return UUID.randomUUID().toString() + ext;
    }

    /**
     * 生成随机目录
     *
     * @param uuidName
     * @return 随机目录
     */
    public static String generateRandomDir(String uuidName) {
        int hashCode = uuidName.hashCode();
        int d1 = hashCode & 0xf;
        int d2 = (hashCode >> 4) & 0xf;
        return "/" + d1 + "/" + d2;
    }
}
