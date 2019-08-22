package com.bigdata.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URI;

/**
 * Hadoop Java API hdfs文件操作
 */
public class HDFSTest {
    public static final String hadoopUrl = "hdfs://192.168.132.128:8020";
    public static final String user = "hadoop";
    Configuration configuration = null;
    FileSystem fileSystem = null;

    @Before
    public void setUp() throws Exception {
        System.out.println("=========set up=========");
        configuration = new Configuration();
        configuration.set("dfs.replication", "1");
        fileSystem = FileSystem.get(new URI(hadoopUrl), configuration, user);
    }

    /**
     * 创建一个文件夹
     *
     * @throws Exception
     */
    @Test
    public void mkdir() throws Exception {
        System.out.println(fileSystem.mkdirs(new Path("/hdfsapi/mkdir")));
    }

    /**
     * 创建一个文件
     *
     * @throws IOException
     */
    @Test
    public void creat() throws IOException {
        Path path = new Path("/hdfsapi/test/words.txt");
        FSDataOutputStream out = fileSystem.create(path);
        out.writeUTF(" hello word word word count count hdfs hdfs hdfs hdfs\n");
        out.flush();
        out.close();
    }

    /**
     * 删除文件
     *
     * @throws Exception
     */
    @Test
    public void remove() throws Exception {
        Path path = new Path("/hdfsapi/test/jdk.tgz");
        boolean isRm = fileSystem.delete(path, true);
        System.out.println(isRm);
    }

    /**
     * 重命名
     *
     * @throws Exception
     */
    @Test
    public void rename() throws Exception {
        Path oldPath = new Path("/hdfsapi/test/a.txt");
        Path newPath = new Path("/hdfsapi/test/aa.txt");
        System.out.println(fileSystem.rename(oldPath, newPath));
    }

    /**
     * 从本地拷贝文件到hdfs文件系统中
     *
     * @throws Exception
     */
    @Test
    public void copyFromLocalFile() throws Exception {
        Path src = new Path("E:\\ComputerQQDownload\\6届泰迪杯C题的部分数据\\C题部分数据\\test_data_sample.json");
        Path path = new Path("/hdfsapi/test/");
        fileSystem.copyFromLocalFile(src, path);
    }

    /**
     * 从本地拷贝大文件到HDFS文件系统
     *
     * @throws Exception
     */
    @Test
    public void copyFromLocalBigFile() throws Exception {
        InputStream input = new BufferedInputStream(new FileInputStream(new File("E:\\ComputerQQDownload\\jdk-8u192-linux-x64.tar.gz")));

        FSDataOutputStream out = fileSystem.create(new Path("/hdfsapi/test/jdk.tgz"), new Progressable() {
            @Override
            public void progress() {
                System.out.print(">");
            }
        });

        IOUtils.copyBytes(input, out, 4096);
        out.close();
        input.close();
    }

    /**
     * 从HDFS文件系统中拷贝到本地（下载）
     *
     * @throws Exception
     */
    @Test
    public void copyToLocalFile() throws Exception {
        Path src = new Path("/hdfsapi/test/aa.txt");
        Path dst = new Path("D:\\Downloads");
        fileSystem.copyToLocalFile(false, src, dst, true);
    }

    /**
     * 列出路径下的所有文件状态
     *
     * @throws Exception
     */
    @Test
    public void listSatue() throws Exception {
        FileStatus[] status = fileSystem.listStatus(new Path("/hdfsapi/test/"));
        for (FileStatus fileStatu : status) {
            String isDir = fileStatu.isDirectory() ? "dir" : "file";
            String permission = fileStatu.getPermission().toString();
            short replication = fileStatu.getReplication();
            long len = fileStatu.getLen();
            long blockSize = fileStatu.getBlockSize();
            Path path = fileStatu.getPath();

            System.out.println(isDir + "\t" + permission + "\t" + replication + "\t" + len + "\t" + path + "\t" + blockSize);
        }
    }

    @Test
    public void recurListFile() throws Exception {
        RemoteIterator<LocatedFileStatus> iterator = fileSystem.listFiles(new Path("/hdfsapi/test/"), true);

        while (iterator.hasNext()) {
            LocatedFileStatus file = iterator.next();
            System.out.println(file.isDirectory() ? "dir" : "file" + "\t" + file.getPermission() + "\t" + file.getLen() + "\t" + file.getPath());
        }

    }

    @Test
    public void blockStatus() throws Exception {
//        FileStatus fileStatus = fileSystem.getFileStatus(new Path("/hdfsapi/test/jdk.tgz"));
//        BlockLocation[] blockLocations = fileSystem.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
//        for (BlockLocation bk : blockLocations) {
//            for (int i = 0; i < bk.getNames().length; i++)
//                System.out.println(bk.getNames()[i] + "\t" + bk.getLength() + "\t" + bk.getOffset() + "\t" + bk.getHosts()[i]);
//
//        }
        Path[] paths = new Path[2];
        paths[0] = new Path("/hdfsapi/test/aa.txt");
        paths[1] = new Path("/hdfsapi/test/test_data_sample.json");

        FileStatus[] statuses = fileSystem.listStatus(paths);
        Path[] listedPaths = FileUtil.stat2Paths(statuses);
        for (Path p : listedPaths)
            System.out.println(p);
    }

    @Test
    public void openTest() throws Exception {
        FSDataInputStream in = fileSystem.open(new Path("/hdfsapi/test/aa.txt"));
        IOUtils.copyBytes(in,System.out,1024);
        IOUtils.closeStream(in);
    }

    @Test
    public void testReplication() {
        System.out.println(configuration.get("dfs.replication"));
    }

    /**
     * 完成测试后将configuration，filesystem置，空回收内存
     */
    @After
    public void tearDown() {
        configuration = null;
        fileSystem = null;
        System.out.println("=========tear down=========");
    }

}
