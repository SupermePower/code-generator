package com.xmxc.generator.util;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

@Slf4j
@Component
public class FileHelper {

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
     * 创建xml文件
     *
     * @param doc      文件内容
     * @param filePath 文件路径
     * @param fileName 文件名称
     */
    public void writeXml(Document doc, String filePath, String fileName) {
        //用于格式化xml内容和设置头部标签
        OutputFormat format = OutputFormat.createPrettyPrint();
        //设置xml文档的编码为utf-8
        format.setEncoding("utf-8");
        Writer out;
        XMLWriter writer = null;
        try {
            File folder = new File(filePath);
            if (!folder.exists()) {
                folder.mkdir();
            }
            File file = new File(filePath + "/" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            //创建一个输出流对象
            out = new FileWriter(file);
            //创建一个dom4j创建xml的对象
            writer = new XMLWriter(out, format);
            //调用write方法将doc文档写到指定路径
            writer.write(doc);
            log.info("生成mybatis映射XML文件成功");
        } catch (IOException e) {
            log.error("生成mybatis映射XML文件SS失败", e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
