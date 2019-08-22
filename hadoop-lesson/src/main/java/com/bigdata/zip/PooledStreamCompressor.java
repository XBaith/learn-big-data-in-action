package com.bigdata.zip;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.ReflectionUtils;

public class PooledStreamCompressor {

    public static void main(String[] args) throws Exception{
        String codecClassName = args[0];
        Class<?> codecClass = Class.forName(codecClassName);
        Configuration conf = new Configuration();
        CompressionCodec codec = (CompressionCodec)ReflectionUtils.newInstance(codecClass,conf);

        Compressor compressor = null;
        try{
            compressor = CodecPool.getCompressor(codec);
            CompressionOutputStream outputStream = codec.createOutputStream(System.out, compressor);
            IOUtils.copyBytes(System.in, outputStream,4096);
            outputStream.flush();
        }finally {
            CodecPool.returnCompressor(compressor);
        }

    }
}
