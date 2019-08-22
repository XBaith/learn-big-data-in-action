package com.bigdata.zip;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;

import java.io.InputStream;
import java.io.OutputStream;

public class FileDecompressor {

    public static void main(String[] args) throws Exception{
        String uri = args[1];
        Path inputPath = new Path(uri);
        Configuration configuration = new Configuration();
        CompressionCodecFactory factory = new CompressionCodecFactory(configuration);
        //getCodec()拿到Codec
        CompressionCodec compressionCodec = factory.getCodec(inputPath);
        //拿到codec之后用工厂的静态方法removeSuffix()去除后缀形成输出文件名，如file.gz得到的解压缩文件名就为file
        String outputUri = CompressionCodecFactory.removeSuffix(uri,compressionCodec.getDefaultExtension());
        InputStream in = null;
        OutputStream out = null;
        FileSystem fs = FileSystem.get(configuration);
        try{
            in = compressionCodec.createInputStream(fs.open(inputPath));
            out = fs.create(new Path(outputUri));
            IOUtils.copyBytes(in, out, configuration);
        }finally {
            IOUtils.closeStream(out);
            IOUtils.closeStream(in);
        }
    }

}
