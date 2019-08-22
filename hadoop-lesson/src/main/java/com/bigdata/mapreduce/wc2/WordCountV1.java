package com.bigdata.mapreduce.wc2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class WordCountV1 {

    static class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
        private final static IntWritable ONE = new IntWritable(1);
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String rawWord = value.toString();
            String[] ws = rawWord.split("\t");
            String word = ws[0];

            if(word.startsWith("大")) {
                context.write(new Text(word), ONE);
            }

        }
    }

    static class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            StringBuilder sb = new StringBuilder();
            int sum = 0;
            for(IntWritable val : values){
                sum += val.get();
                sb.append(key.toString() + "\t");
            }
            context.write( new Text(sb.toString()), new IntWritable(sum));
        }
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("hadoop.home.dir", "d:\\winutil\\");
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "words start with 大");

        job.setJarByClass(WordCountV1.class);
        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);
        job.setCombinerClass(MyReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileSystem fs = FileSystem.get(conf);
        Path input = new Path("E:\\resource\\游览器\\数据集\\THUOCL\\THUOCL_chengyu.txt");
        Path output = new Path("output/wc/v1");
        if(fs.exists(output)){
            fs.delete(output,true);
        }

        FileInputFormat.setInputPaths(job, input);
        FileOutputFormat.setOutputPath(job, output);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
