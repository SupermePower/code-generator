package com.xmxc.generator.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
@Component
public class CreateFileHelper {

    /**
     * 创建映射接口
     *
     * @param fileName 文件名称
     * @param filePath 文件路径
     */
    public void createMapperInterface(String fileName, String filePath, String fileContent) {
        FileOutputStream outputStream = null;
        FileChannel channel = null;
        ByteBuffer buffer = null;
        try {
            try {
                File dir = new File(filePath);
                if (!dir.exists()) {
                    dir.mkdir();
                }
            } catch (Exception e) {
                log.error("创建文件夹失败 filePath -> {} \n Exception -> ", filePath, e);
            }
            File file = null;
            try {
                file = new File(filePath + "/" + fileName + ".java");
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (IOException e) {
                log.error("创建文件失败 filePath -> {} \n fileName -> {} \n Exception -> ", filePath, fileName, e);
            }
            try {
                outputStream = new FileOutputStream(file);
                channel = outputStream.getChannel();
                buffer = ByteBuffer.allocate(2048);
                buffer.put(fileContent.getBytes());
                buffer.flip();     //此处必须要调用buffer的flip方法
                channel.write(buffer);
            } catch (IOException e) {
                log.error("文件写入失败 -> IOException", e);
            }
        } finally {
            close(outputStream, channel, buffer);
        }
    }

    /**
     * 关闭资源
     *
     * @param outputStream
     * @param channel
     * @param buffer
     */
    private void close(FileOutputStream outputStream, FileChannel channel, ByteBuffer buffer) {
        try {
            if (channel != null) {
                channel.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            log.error("关闭资源失败 -> {}", e);
        }
    }
}
