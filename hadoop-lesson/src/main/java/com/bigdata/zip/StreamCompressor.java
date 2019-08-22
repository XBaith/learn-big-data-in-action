package com.bigdata.zip;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.InputStream;
import java.io.OutputStream;

public class StreamCompressor {

    public static void main(String[] args) throws Exception{
        String codeClassName = args[0];
        Configuration configuration = new Configuration();

        Class<?> className = Class.forName(codeClassName);
        CompressionCodec codec = (CompressionCodec)ReflectionUtils.newInstance(className, configuration);
        CompressionOutputStream outputStream = codec.createOutputStream(System.out);
        IOUtils.copyBytes(System.in, outputStream, 4096,false);

        outputStream.flush();

    }
}
