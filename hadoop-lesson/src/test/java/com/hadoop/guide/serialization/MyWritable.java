package com.hadoop.guide.serialization;

import org.apache.hadoop.io.*;
import org.apache.hadoop.util.StringUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.apache.hadoop.io.WritableComparator.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MyWritable {
    @Before
    public void setUp(){
        System.setProperty("hadoop.home.dir","d:\\winutil\\");
    }

    @Test
    public void writableTest() throws Exception{
        IntWritable intWritable = new IntWritable(163);
        byte[] bytes = serializ(intWritable);
        assertThat(bytes.length, is(4));
        String hexStr = StringUtils.byteToHexString(bytes);
        assertThat(hexStr, is("000000a3"));

        IntWritable newWritable = new IntWritable();
        byte[] bytes1 = deserialize(newWritable,bytes);
        assertThat(newWritable.get(), is(163));
    }

    private static byte[] serializ(Writable writable) throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(out);
        writable.write(outputStream);
        outputStream.close();
        return out.toByteArray();
    }

    private static byte[] deserialize(Writable writable, byte[] bytes) throws IOException{
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        DataInputStream inputStream = new DataInputStream(in);
        writable.readFields(inputStream);
        inputStream.close();
        return bytes;
    }

    @Test
    public void comparableTest() throws IOException{
        WritableComparator comparator = get(IntWritable.class);
        IntWritable a = new IntWritable(163);
        IntWritable b = new IntWritable(67);
        assertThat(comparator.compare(a,b), Matchers.greaterThan(0));

        //序列化表示
        byte[] aBytes = serializ(a);
        byte[] bBytes = serializ(b);
        assertThat(comparator.compare(aBytes,0,aBytes.length, bBytes,0,bBytes.length), Matchers.greaterThan(0));
    }

    @Test
    public void textTest() {
        String str = "hadoop";
        Text t = new Text(str);
        assertThat(t.getLength(),is(6));
        assertThat(t.getBytes().length,is(6));
        //charAt()返回值不同
        assertThat(t.charAt(2),is((int)'d'));               //返回索引位置字母的编码，int
        assertThat(str.charAt(2),is('d'));           //String返回索引位置的字母，char
        assertThat("Out of bounds",t.charAt(10),is(-1));    //越界会返回-1

        //Text的find()与String的indexOf()类似
        assertThat("Text:   Find a substring",t.find("do"),is(2));
        assertThat("Text:   Find first 'o'",t.find("o",4),is(4));

        assertThat("String: Find a substring",str.indexOf("do"),is(2));
        assertThat("String: Find first 'o'",str.indexOf("o",4),is(4));
    }
    @Test
    public void BytesWritableTest() throws  Exception{
        byte[] bytes = {3, 5};
        BytesWritable bytesWritable = new BytesWritable(bytes);
        byte[] serBytes = serializ(bytesWritable);
        //长度2的字节数组包含值3，5，则它序列化为一个4字节的整数00000002和两个字节值03，05
        assertThat(StringUtils.byteToHexString(serBytes), is("000000020305"));
    }

}
