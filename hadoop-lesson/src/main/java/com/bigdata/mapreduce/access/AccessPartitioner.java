package com.bigdata.mapreduce.access;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class AccessPartitioner extends Partitioner<Text, Access> {
    @Override
    public int getPartition(Text phone, Access access, int numReduceTasks) {
        String phoneNum = phone.toString();
        if(phoneNum.startsWith("13")){
            return 0;
        } else if(phoneNum.startsWith("15"))
        {
            return 1;
        } else {
            return 2;
        }
    }
}
