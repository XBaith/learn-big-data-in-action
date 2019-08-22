package org.hadoop.guide.sequencefile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;

import java.net.URI;

public class SequenceFileWriteDemo {
    static final String[] DATA = {
            "line 1",
            "line 2",
            "line 3",
            "line 4",
            "line 5"
    };

    public static void main(String[] args) throws Exception{
        System.setProperty("hadoop.home.dir", "D:\\winutil\\");
        String uri = "output/sequencefile/number";
        Configuration conf = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI(uri), conf);
        Path path = new Path(uri);

        IntWritable key = new IntWritable();
        Text value = new Text();
        SequenceFile.Writer writer = null;
        try {
            writer = SequenceFile.createWriter(fileSystem,conf,path,key.getClass(),value.getClass());
            for(int i = 0 ; i < 100; i++){
                key.set(100 - i);
                value.set(DATA[i % DATA.length]);
                System.out.printf("[%s]\t%s\t%s\n",writer.getLength(),key,value);
                writer.append(key,value);
            }
        }finally {
            IOUtils.closeStream(writer);
        }

    }
}
