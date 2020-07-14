package com.vankillua.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @Author KILLUA
 * @Date 2020/7/13 17:41
 * @Description
 */
@Slf4j
public class CommonUtils {
    @SuppressWarnings("unchecked")
    public static <T> T deepClone(T object) {
        try {
            // 序列化
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            objectOutputStream.writeObject(object);

            // 反序列化
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            return (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("对象深拷贝时遇到异常：", e);
        }
        return null;
    }
}
