package edu.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseTestingUtility;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class HBaseUnitTest {
    private static final HBaseTestingUtility TESTING_UTILITY = new HBaseTestingUtility();
    public static final TableName TABLE_NAME = TableName.valueOf("test");

    @BeforeClass
    public static void setUp() throws Exception {
        TESTING_UTILITY.startMiniCluster();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        TESTING_UTILITY.shutdownMiniCluster();
    }

    @Test
    public void test () throws IOException {
        Configuration conf = TESTING_UTILITY.getConfiguration();
        try (Connection conn = ConnectionFactory.createConnection(conf)){
            try (Table table = conn.getTable(TABLE_NAME)){
                Put put = new Put(Bytes.toBytes("row1"))
                        .addColumn(Bytes.toBytes("family1"), Bytes.toBytes("qualifier1"), Bytes.toBytes("value1"));
                table.put(put);
            }
        }
    }
}
