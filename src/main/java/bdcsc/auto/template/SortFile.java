package bdcsc.auto.template;


import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

/**
 * 为jmx文件重排序
 */
public class SortFile extends FileTemplate {

    /**
     * 重排序的主实现
     * @param fileUrl 文件所在的文件夹的绝对路径，例如 /Users/mawenrui/Desktop/生成jmeter模版/result/
     */
    public void sortFile(String fileUrl) {
        // 获取文件夹下的所有jmx文件
        ArrayList<File> jmxFiles = findJmxFiles(fileUrl);
        if (jmxFiles.size() == 0) {
            System.out.println("文件夹下无jmx格式文件");
            return;
        }

        // 遍历处理每个文件
        for (File jmx : jmxFiles) {
            editFile(jmx);
        }
        System.out.println("重排序结束！");
    }

    /**
     * 读取出文件中的内容
     * @param jmx
     */
    private void editFile(File jmx) {
        try {
            FileInputStream fis = new FileInputStream(jmx);
            String contents = IOUtils.toString(fis, "utf-8");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<File> findJmxFiles(String fileUrl) {
        File result;
        File[] files;
        ArrayList<File> jmxFiles = new ArrayList<>();
        try {
            result = new File(fileUrl);
            files = result.listFiles();
        } catch (Exception e) {
            System.out.println("输入的文件夹路径错误或文件夹为空");
            return jmxFiles;
        }

        LinkedList<File> dirs = new LinkedList<>();
        for (File file : Objects.requireNonNull(files)) {
            if (file.isDirectory()) {
                dirs.add(file);
            } else {
                String path = file.getPath();
                if (".jmx".equals(path.substring(path.length() - 4))) {
                    jmxFiles.add(file);
                }
            }
        }

        while (dirs.size() != 0) {
            File dirFile = dirs.removeFirst();
            for (File f : Objects.requireNonNull(dirFile.listFiles())) {
                if (f.isDirectory()) {
                    dirs.add(f);
                } else {
                    String p = f.getPath();
                    if (".jmx".equals(p.substring(p.length() - 4))) {
                        jmxFiles.add(f);
                    }
                }
            }
        }
        return jmxFiles;
    }
}
