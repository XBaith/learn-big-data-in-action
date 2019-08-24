package com.bigdata.project.mr2;

import com.bigdata.project.utils.ContentUtils;
import com.bigdata.project.utils.LogParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Map;

public class ETLApp {
    public static void main(String[] args) throws Exception {
        //System.setProperty("hadoop.home.dir", "d:\\winutil\\");
        Configuration configuration = new Configuration();

        Path inputPath = new Path(args[0]);
        Path outputPath = new Path(args[1]);

        FileSystem fileSystem = FileSystem.get(configuration);
        if(fileSystem.exists(outputPath)){
            fileSystem.delete(outputPath, true);
        }

        Job job = Job.getInstance(configuration);
        job.setJarByClass(ETLApp.class);

        job.setMapperClass(MyMapper.class);

        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, inputPath);
        FileOutputFormat.setOutputPath(job, outputPath);

        job.waitForCompletion(true);
    }

    static class MyMapper extends Mapper<LongWritable, Text, NullWritable, Text>{

        private LogParser parser = new LogParser();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            Map<String, String> info = parser.parse(value.toString());

            String ip = info.get("ip");
            String country = info.get("country");
            String province = info.get("province");
            String city = info.get("city");
            String url = info.get("url");
            String date = info.get("date");
            String pageId = ContentUtils.getPageId(url);

            StringBuilder builder = new StringBuilder();
            builder.append(ip).append("\t");
            builder.append(country).append("\t");
            builder.append(province).append("\t");
            builder.append(city).append("\t");
            builder.append(url).append("\t");
            builder.append(date).append("\t");
            builder.append(pageId).append("\t");

            context.write(NullWritable.get(), new Text(builder.toString()));
        }
    }
}
