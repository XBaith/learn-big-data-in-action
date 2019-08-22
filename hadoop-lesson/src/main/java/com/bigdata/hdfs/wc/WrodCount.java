package com.bigdata.hdfs.wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 1） 读取HDFS文件
 * 2） 词频统计
 * 3） 讲词频统计结果放入缓存
 * 3） 讲结果输出，写入HDFS文件中去
 */
public class WrodCount {

    public static void main(String[] args) throws Exception{
        Configuration configuration = new Configuration();
        configuration.set("dfs.replication", "1");
        Properties properties = ParamsUtils.getProperties();
        FileSystem fs = FileSystem.get(new URI(properties.getProperty(Constants.HDFS_URL)), configuration, properties.getProperty(Constants.USERNAME));
        //hdfs读取路径
        Path inputPath = new Path(properties.getProperty(Constants.INPUT_PATH));

        Class<?> clazz = Class.forName(properties.getProperty(Constants.WordCountMapper));
        WordMapper wordMapper = (WordMapper)clazz.newInstance();
        ContextCache context = new ContextCache();

        RemoteIterator<LocatedFileStatus> iterator = fs.listFiles(inputPath, false);
        while(iterator.hasNext()){
            LocatedFileStatus file = iterator.next();
            FSDataInputStream input = fs.open(file.getPath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = "";
            while((line = reader.readLine()) != null){
                //词频统计
                wordMapper.map(line, context);
            }

            reader.close();
            input.close();
        }
        //上下文Map key:单词 value:词频
        Map<Object,Object> cache = context.getCacheMap();
        //hdfs文件输出路径
        Path outPath = new Path(properties.getProperty(Constants.OUTPUT_PATH));
        FSDataOutputStream output = fs.create(new Path(outPath, new Path(properties.getProperty(Constants.OUTPUT_FILE))));

        Set<Map.Entry<Object,Object>> entries=  cache.entrySet();
        for(Map.Entry<Object,Object> entry : entries){
            output.write((entry.getKey().toString() + "\t" + entry.getValue() + "\n").getBytes("UTF-8"));
        }
        output.hflush();
        output.close();

        fs.close();
        System.out.println("词频统计成功!\n");
    }

}