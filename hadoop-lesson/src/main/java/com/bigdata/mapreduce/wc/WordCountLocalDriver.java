package com.bigdata.mapreduce.wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.nio.file.FileSystem;

public class WordCountLocalDriver {

    public static void main(String[] args) throws Exception{

        Configuration configuration = new Configuration();

        System.setProperty("hadoop.home.dir", "D:\\winutil\\");

        Job job = Job.getInstance(configuration);

        job.setJarByClass(WordCountLocalDriver.class);

        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //设置的Combiner，一般和Ruducer逻辑一样
        job.setCombinerClass(WordCountReducer.class);

        Path outputPath = new Path("output/");

        FileInputFormat.setInputPaths(job, new Path("input/"));
        FileOutputFormat.setOutputPath(job, outputPath);

        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : -1);
    }
}
