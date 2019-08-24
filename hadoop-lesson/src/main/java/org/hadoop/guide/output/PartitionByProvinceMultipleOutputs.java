package org.hadoop.guide.output;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class PartitionByProvinceMultipleOutputs {

    static class MyMapper extends Mapper<LongWritable, IntWritable, Text, Text>{
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
        }
    }

}
