package org.hadoop.guide.writable;

import org.apache.hadoop.io.Text;

import java.nio.ByteBuffer;

public class TextIterator {
    public static void main(String[] args) {
        Text text = new Text("\u0041\u00DF\u6771\uD801\uDC00");
        ByteBuffer buf = ByteBuffer.wrap(text.getBytes(),0, text.getLength());
        int cp = 0;
        while(buf.hasRemaining() && (cp = Text.bytesToCodePoint(buf)) != -1){
            System.out.println(Integer.toHexString(cp));
        }
    }
    /*
    Simple (~200 lines) distributed file system like HDFS (and of-course GFS).
    It consists of one Master (NameNode) and multiple Minions (DataNode).
     And a client for interation. It will dump metadata/namespace when given SIGINT and reload it when fired up next time.
     Replicate data the way HDFS does.
    It will send data to one minion and that minion will send it to next one and so on.
    Reading done in similar manner. Will contact first minion for block, if fails then second and so on. Uses RPyC for RPC.
    Requirements
    rpyc (Really! That's it.)
     */
}
