package org.hadoop.guide.sequencefile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.util.ReflectionUtils;

import java.net.URI;

public class SequenceFileReadDemo {

    public static void main(String[] args) throws Exception{
        System.setProperty("hadoop.home.dir", "D:\\winutil\\");
        String uri = "output/sequencefile/number";
        Configuration conf = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI(uri), conf);
        Path path = new Path(uri);

        SequenceFile.Reader reader = null;
        try {
            reader = new SequenceFile.Reader(fileSystem,path,conf);
            Writable key = (Writable)ReflectionUtils.newInstance(reader.getKeyClass(),conf);
            Writable value = (Writable)ReflectionUtils.newInstance(reader.getValueClass(),conf);
            long position = reader.getPosition();
            while(reader.next(key,value)){
                String syncSeen = reader.syncSeen() ? "*" : "";
                System.out.printf("[%s%s]\t%s\t%s\n", position,syncSeen,key,value);
                position = reader.getPosition();
            }
        }finally {
            IOUtils.closeStream(reader);
        }

    }
}
