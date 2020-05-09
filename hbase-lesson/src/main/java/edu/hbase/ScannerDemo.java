package edu.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Iterator;

public class ScannerDemo {

    public static void main(String[] args) {
        Configuration conf = new Configuration();
        try(Connection conn = ConnectionFactory.createConnection(conf)) {
            //get table
            Table table = conn.getTable(TableName.valueOf("table"));
            Scan scan = new Scan();
            //set the location where the scanner starts and stops
            scan.withStartRow(Bytes.toBytes("startRow"))
                    .withStopRow(Bytes.toBytes("stopRow"));
            //result scanner aggregates the results we need
            ResultScanner scanner = table.getScanner(scan);
            //get
            Iterator<Result> iterator = scanner.iterator();
            while(iterator.hasNext()) {
                Result result = iterator.next();
                for(Cell kv : result.rawCells()) {
                    //do something with cell
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
