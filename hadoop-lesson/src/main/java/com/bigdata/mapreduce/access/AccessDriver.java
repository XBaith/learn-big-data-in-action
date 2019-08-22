package com.bigdata.mapreduce.access;

import com.bigdata.hdfs.wc.Constants;
import com.bigdata.hdfs.wc.ParamsUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.util.Properties;

public class AccessDriver {

    public static void main(String[] args) throws Exception{
        Configuration configuration = new Configuration();
        System.setProperty("hadoop.home.dir", "D:\\winutil\\");
       // Properties properties = ParamsUtils.getProperties();
        //configuration.set("fs.dedaultFS", properties.getProperty(Constants.HDFS_URL));
        Job job = Job.getInstance(configuration);
        //System.setProperty("HADOOP_USER_NAME", properties.getProperty(Constants.USERNAME));


        job.setJarByClass(AccessDriver.class);

        job.setMapperClass(AccessMapper.class);
        job.setReducerClass(AccessReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Access.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Access.class);

        job.setPartitionerClass(AccessPartitioner.class);
        job.setNumReduceTasks(3);

        FileInputFormat.setInputPaths(job, new Path("access/input"));

        Path output = new Path("access/output");
        FileOutputFormat.setOutputPath(job, output);

        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : -1);
    }

}
