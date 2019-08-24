package org.hadoop.guide.output;

import com.bigdata.project.utils.LogParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import java.io.IOException;
import java.util.Map;

public class PartitionByProvinceMultipleOutputs {

    static class MyMapper extends Mapper<LongWritable, Text, Text, Text>{

        private LogParser parser = new LogParser();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            Map<String, String> info = parser.parse(value.toString());
            String province = info.get("province");
            context.write(new Text(province), value);
        }
    }

    static class MyReducer extends Reducer<Text, Text, NullWritable, Text> {
        private LogParser parser = null;
        private MultipleOutputs<NullWritable, Text> outputs = null;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            parser = new LogParser();
            outputs = new MultipleOutputs<>(context);
        }

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            Map<String,String> info = null;
            String basePath = "";

            for(Text val : values){
                info = parser.parse(val.toString());
                basePath =String.format("%s/part", info.get("province"));
                outputs.write(NullWritable.get(),val,basePath);
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            outputs.close();
        }
    }

    public static void main(String[] args) throws Exception{
        System.setProperty("hadoop.home.dir", "D:\\winutil\\");

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(PartitionByProvinceMultipleOutputs.class);
        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);

        String input = "E:\\ComputerQQDownload\\trackinfo_20130721.txt";
        String output = "output/partitions";
        Path outputPath = new Path(output);

        FileSystem fs = FileSystem.get(conf);
        if(fs.exists(outputPath)){
            fs.delete(outputPath,true);
        }
        FileInputFormat.setInputPaths(job,new Path(input));
        FileOutputFormat.setOutputPath(job,outputPath);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }


}
