package com.mss.thread.service;

import com.mss.utils.PrintHelper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileService {

    /**
     * 读取文件内容
     *
     * @param filePath 文件路径
     * @return 文件内容，如果文件不存在或读取发生错误，返回null
     */
    public String readFile(String filePath) {
        // 验证文件路径，避免路径遍历攻击
        if (isValidFilePath(filePath)) {
            System.err.println("Invalid file path.");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(filePath)), StandardCharsets.UTF_8))) {
            StringBuilder content = new StringBuilder(Math.max(1024, (int) new File(filePath).length()));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append("\n");
            }
            return content.toString();
        } catch (IOException e) {
            PrintHelper.printExceptionMark(e);
            return null;
        }
    }

    /**
     * 写入文件
     *
     * @param filePath 文件路径
     * @param content 要写入的内容
     * @return true表示文件写入成功，false表示文件写入失败
     */
    public boolean writeFile(String filePath, String content) {
        // 验证文件路径，避免路径遍历攻击
        if (isValidFilePath(filePath)) {
            System.err.println("Invalid file path.");
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(filePath)), StandardCharsets.UTF_8))) {
            writer.write(content);
            return true;
        } catch (IOException e) {
            PrintHelper.printExceptionMark(e);
            return false;
        }
    }

    // 验证文件路径的方法，防止路径遍历攻击
    private boolean isValidFilePath(String filePath) {
        // 示例：简单的验证，防止路径遍历，根据实际情况进行增强
        return filePath.contains("..") || !filePath.startsWith("./");
    }
}

