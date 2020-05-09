package edu.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class HBaseTest {
    private Configuration config = null;
    private Connection connection = null;
    private Admin admin = null;

    @Before
    public void getConfig() throws IOException {
        config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "master,slave1,slave2");
        connection = ConnectionFactory.createConnection(config);
        admin = connection.getAdmin();
    }

    /**
     * 检查表是否存在
     * @throws IOException
     */
    @Test
    public void existTable() throws IOException {
        boolean exist = admin.tableExists(TableName.valueOf("test"));
        System.out.println(exist);
    }

    /**
     * 创建表
     * @throws IOException
     */
    /*旧API*/
    @Test
    public void createTable() throws IOException {
        TableName tableName = TableName.valueOf("create_test");
        HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
        tableDescriptor.addFamily(new HColumnDescriptor("family1").setCompactionCompressionType(Compression.Algorithm.NONE));
        admin.createTable(tableDescriptor);
    }
    /*新API*/
    @Test
    public void createTable2() throws IOException {
        //表名
        TableName tableName = TableName.valueOf("ns:create_test");
        //列族
        String[] cfs = new String[]{"cf1", "cf2", "cf3"};
        //检测表是否存在
        if(admin.tableExists(tableName)) {
            System.err.println("Table has existed, delete and recreate");
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
        }
        //创建列族集合
        List<ColumnFamilyDescriptor> cfds = new ArrayList<>();
        for (String cf : cfs) {
             cfds.add(ColumnFamilyDescriptorBuilder
                     .newBuilder(Bytes.toBytes(cf))
                     .build());
        }
        //添加列族并创建表描述器
        TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(tableName)
                .setColumnFamilies(cfds)
                .build();

        admin.createTable(tableDescriptor); //传入表描述器
    }

    /**
     * 删除表
     * @throws IOException
     * @throws IllegalAccessException
     */
    @Test
    public void dropTable() throws IOException, IllegalAccessException {
        TableName tableName = TableName.valueOf("create_test");
        if(admin.tableExists(tableName)) {
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
        } else {
            throw new IllegalAccessException("Table : " + tableName.getNameAsString() + " doesn't exist!");
        }
    }

    /**
     * 创建命名空间
     */
    @Test
    public void createNamespace() {
        try {
        //创建命名空间描述器，直接new不可行，因为里面构造方法是私有的，所以需要其内部类Builder来创建
        NamespaceDescriptor nsd = NamespaceDescriptor
                .create("ns")   //传入命名空间名
                .build();

        admin.createNamespace(nsd);    //传入命名空间描述器
        } catch (NamespaceExistException exist){
            System.err.println("Namespace has existed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * DDL
     * 对表进行操作，所以管理对象是Table
     */

    /**
     * 插入列数据需要的参数包括
     * 1. 表名 : TableName
     * 2. rowkey : RowKey
     * 3. 列族 : ColumnFamily
     * 4. 限定符 : Qualifier
     * 5. 值 : Value
     * @throws IOException
     */
    @Test
    public void putColumn() throws IOException{
        TableName tableName = TableName.valueOf("test");
        //创建表
        Table table = connection.getTable(tableName);
        //创建Put对象
        Put put = new Put(Bytes.toBytes("row5"))    //传入rowkey
                .addColumn(Bytes.toBytes("cf"), Bytes.toBytes("e"), Bytes.toBytes("value5"));   //插入列所需信息，列族，限定符，值
        //插入Put
        table.put(put);    //传入Put对象

        //Table属于轻量级链接，用完就可以关了
        table.close();
    }

    @Test
    public void getColumn() throws IOException{
        TableName tableName = TableName.valueOf("test");
        Table table = connection.getTable(tableName);
        Get get = new Get(Bytes.toBytes("row1"));
        /*1.指定列族*/
//        get.addFamily(Bytes.toBytes("cf1"));
        /*2.指定列限定符*/
//        get.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("aa"));
        /*3.获取多个版本*/
//        get.readAllVersions();
//        get.readVersions(2);

        Result result = table.get(get); //传入Get

        for (Cell cell : result.rawCells()) {
            String rowkey = Bytes.toString(CellUtil.cloneRow(cell));
            String family = Bytes.toString(CellUtil.cloneFamily(cell));
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));

            System.out.println("RowKey : " + rowkey + ", family : " + family
                    +  ", qualifier : " + qualifier +  ", value : " + value + ", timestamp : " + cell.getTimestamp());
        }

        table.close();
    }

    /**
     * 添加列族
     * @throws IOException
     */
    @Test
    public void addColumnFamily() throws IOException {
        TableName tableName = TableName.valueOf("test");
        ColumnFamilyDescriptor cf = ColumnFamilyDescriptorBuilder
                .newBuilder(Bytes.toBytes("cf1"))
                .build();
        admin.addColumnFamily(tableName, cf);
    }

    @Test
    public void batchTest() throws ExecutionException, InterruptedException {
        CompletableFuture<AsyncConnection> asyncConnection = ConnectionFactory.createAsyncConnection(config);
        AsyncAdmin asyncAdmin = asyncConnection.get().getAdmin();
    }

    @After
    public void close() throws IOException {
        if(admin != null)
            admin.close();
        if(connection != null)
            connection.close();
    }

}
