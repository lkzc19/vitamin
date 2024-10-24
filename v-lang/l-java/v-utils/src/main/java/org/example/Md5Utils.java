package org.example;


import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.MessageDigest;

@UtilityClass
public class Md5Utils {

    @SneakyThrows
    public static String md5(File file) {
        return md5(Files.newInputStream(file.toPath()));
    }

    @SneakyThrows
    public static String md5(InputStream inputStream) {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        // 读取文件并更新哈希计算
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            messageDigest.update(buffer, 0, bytesRead);
        }

        // 完成哈希计算
        byte[] hashBytes = messageDigest.digest();
        // 将哈希值转换为十六进制字符串
        StringBuilder hexString = new StringBuilder(2 * hashBytes.length);
        for (byte hashByte : hashBytes) {
            String hex = Integer.toHexString(0xff & hashByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
