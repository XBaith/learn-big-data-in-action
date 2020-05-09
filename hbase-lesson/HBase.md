# HBase

基于Hadoop2.8.5, HBase 2.1.8, 并且是分布式集群中

##  开始

### 备份HMaster

`master-backup.sh`启动备份 HMaster

例如：

```
master-backup.sh start 2 3 5
```

其中2 3 5 分别代表该主节点的端口偏移量，每个 HMaster 使用三个端口（默认情况下为 16010,16020 和 16030）

HMaster的PID在/tmp/hbase-USER-X-master.pid中

## HBase Shell

### 进入shell后用`help`查看相关命令

```
Group name: general
  Commands: processlist, status, table_help, version, whoami

  Group name: ddl	#数据定义语言
  Commands: alter, alter_async, alter_status, clone_table_schema, create, describe, disable, disable_all, drop, drop_all, enable, enable_all, exists, get_table, is_disabled, is_enabled, list, list_regions, locate_region, show_filters

  Group name: namespace
  Commands: alter_namespace, create_namespace, describe_namespace, drop_namespace, list_namespace, list_namespace_tables

  Group name: dml	#数据管理语言
  Commands: append, count, delete, deleteall, get, get_counter, get_splits, incr, put, scan, truncate, truncate_preserve
```

### count命令

Count 命令返回表中的行数。配置正确的 CACHE 时速度非常快：

```
hbase> count '<tablename>', CACHE => 1000
```

上述计数一次取 1000 行。如果行很大，请将 CACHE 设置得较低。默认是每次读取一行。

## 数据模型

- 行(row)：包含一个唯一标示rowkey，多个列和值。在行存储时，顺序按照字母（字典）进行排序。

- 列(column)：HBase 中的列由**列族**和**列限定符**组成，它们由`:`（冒号）字符分隔。一个列族可以有多个列限定符qualifier。

- 列族(column family)：每个列族都有一组存储属性，例如是否应将其值缓存在内存中，如何压缩其数据或对其行键进行编码等。

  - 表中的每一行都具有相同的列族，但**给定的行可能不会在给定的列族中存储任何内容**。
  - 调优和存储规范是在**列族级别**完成的。

- 列限定符(quailfier)：列限定符被添加到列族中，以提供给定数据段的索引。列族在创建表时是固定的，但列限定符是可变的。

- 单元格(cell)：单元格是**行，列族和列限定符**，**一个时间戳**，**操作类型（Put/Delete）**，**值**的组合。其中前四个属于Key，值属于Value。时间戳表示值的版本。下例中的CELL就是一个单元格。*{row，column，version}* 它是一个元组并确切地指定了 HBase 中的cell

  ```
  hbase(main):004:0> get 'test', 'row1'
  COLUMN                                       CELL                                   
   cf:a                                        timestamp=1575996063358, value=value1   1 row(s)
  Took 0.0736 seconds                                                                 
  ```

- 时间戳(timestamp)：按递减的顺序排序，因此从存储文件中读取时，首先找到最近的值。在hbase shell中可JRuby导入java.util.Date类转换为公制时间：

  ```
  hbase(main):003:0> Date.new(1575996075797).toString()
  => "Wed Dec 11 00:41:15 CST 2019"
  ```
  可以通过在 *hbase-site.xml* 中设置`hbase.column.max.version`，为所有新创建的列指定的最大版本数指定全局默认值。

- 列名：由列族前缀和限定符组成

- 最大版本数（VERSION）：默认设置为1，表示一次get得到的最几新的数据；最小版本数(MIN_VERSION)默认为0。

### namespace

查看命名空间，默认有两个特殊的namespace

- hbase - 系统命名空间，用于包含 HBase 内部表
- default - 没有明确指定名称空间的表将自动落入此名称空间

```
hbase(main):006:0> list_namespace
NAMESPACE                                                                               default                                                                                 hbase                                                                                   2 row(s)
Took 0.3560 seconds 
```



### 数据模型操作

#### Get(查)

返回指定行的属性

#### Put（增，改）

添加到表中的新行（如果键是新的）或者可以更新现有行（如果键已存在）

#### Scan（查）

得到多行，并可以对指定属性的多行进行迭代

#### Delete（删）

从表中删除一行

删除的操作是通过创建名为 *tombstones* 的新标记来处理删除操作，这些*tombstones* 标记以及删除的值在主要的压缩（major compactions）中得到了清理。

### 列的元数据 

除了KeyValue实例以外，列族中没有其他列的元数据

### JOIN

等值连接操作需要自己实现

1. 反规范化数据
2. 在应用程序或MapReduce代码中查找表并在HBase表中进行连接（嵌套循环，散列连接）



## HBase和Schema设计

因为存在许多种不同的数据集，不同的访问模式和服务层级的要求。所以，以下经验法则只是概述。阅读完此列表后，请阅读这一章的剩余部分以获得更多详细信息。

- 目标是把 region 的大小限制在 10 到 50 GB 之间。
- 目标是限制 cell 的大小在 10 MB 之内，如果使用的是 [mob](https://hbase.apachecn.org/#/docs/6?id=hbase_mob) 类型，限制在 50 MB 之内。否则，考虑把 cell 的数据存储在 HDFS 中，并在 HBase 中存储指向该数据的指针。
- 典型的 scheme 每张表包含 1 到 3 个列族。HBase 表设计不应当和 RDBMS 表设计类似。
- 对于拥有 1 或 2 个列族的表来说，50-100 个 region 是比较合适的。请记住， region 是列族的连续段。
- 保持列族名称尽可能短。每个值都会存储列族的名称(忽略前缀编码)。它们不应该像典型 RDBMS 那样，是自文档化，描述性的名称。
- 如果你正在存储基于时间的机器数据或者日志信息，并且 row key 是基于设备 ID 或者服务 ID + 时间，最终会出现这样一种情况，即更旧的数据 region 永远不会有额外写入。在这种情况下，最终会存在少量的活动 region 和大量不会再有新写入的 region。对于这种情况，可以接受更多的 region 数量，因为资源的消耗只取决于活动 region。
- 如果只有一个列族会频繁写，那么只让这个列族占用内存。当分配资源的时候注意写入模式。

## RegionServer 大小调整规则

### 列族数量

刷新和压缩是基于每个Region来操作的，因此，如果有很多Region而你想只对一个Region操作时，其他Region会产生不必要的I/O。大约是1~3个Region

### ColumnFamilies的基数（行数）

如果两个列族的行数相差太多，那么对于较少的列族扫描会非常麻烦。

## 六、RowKey设计

### 热点问题

当有大量客户端流量指向集群中的某一个节点或仅几个节点，就会产生热点。由于HBase的行是按照字典序排列，所以相似的行会放在一起，扫描更有效，但是也会让大量客户端流量指向一个节点，造成热点问题。

#### 解决热点

##### 1.Salting

将随机数（字符）添加到行键的开头。Salting 虽然增加了写操作的吞吐量，与此同时也增加了读操作的开销。

##### 2.哈希

使用单向散列而不是随机分配，这将导致给定行始终使用相同的前缀“加盐”，从而将负载分散到 RegionServers，并且允许读取期间的可预测性。。使用确定性哈希允许客户端重建完整的 rowkey 并使用 Get 操作正常检索该行。

比如采用 md5 散列算法取前4位做前缀。结果如下：

> 9bf0-abc001 （abc001在md5后是9bf049097142c168c38a94c626eddf3d，取前4位是9bf0）、7006-abc002、95e6-abc003。

若以前4个字符作为不同分区的起止，上面几个 RowKey 数据会分布在3个 Region中。实际应用场景是当数据量越来越大的时候，这种设计会使得分区之间更加均衡。如果 RowKey 是数字类型的，也可以考虑 Mod 方法。

##### 3.反转建

反转固定宽度或数字行键，可以有效的随机 RowKey ，但是牺牲了 RowKey 的有序性。

以手机举例，可以将手机号反转后的字符串作为RowKey，这样的就避免了以手机号那样比较固定开头(137x、15x等)导致热点问题。



### 单调递增行键问题

通过将输入记录随机化为不按排序顺序，可以减轻由单调增加的密钥引起的单个区域的堆积，但通常最好避免使用时间戳或序列（例如，1,2,3）作为行键。

### 名字尽量短

- 因为HBase需要根据HFile上的索引来找需要随机访问的文件，在访问时需要加载到RAM中。因此，如果行名或列名太长，会放不下多的行列。还有存储块也不能尽量大点，这样HFile中的索引间隔就会大，存储的内容也就更多。

- 列族名称也要尽可能的短，最好一个字符

- 属性也尽量短
- RowKey名字长度短了便于访问（Get和Scan）。

### 反向时间戳

为了让扫描过程中，首先扫描的是最新版本的，可以对时间戳做一些处理，例如Long.MAX_VALUE - timestamp作为RowKey，这样最新的时间戳大，在被Long.MAX_VALUE减去之后就会更小，因此按照HBase的排序顺序最新的行就会在前。

> `Scan.setReversed()`可以反向扫描表个或表格中的范围，这样可以根据表格中的顺序，自己选择更好的扫描方式。

### RowKeys和ColumnFamlies

**Rowkeys 的作用域为 ColumnFamilies**。因此，在没有冲突的表中，每个 ColumnFamily 可以存在相同的 RowKey。

### RowKeys的不变性

Rowkeys 无法更改。它们可以在表中“更改”的唯一方法是删除行然后重新插入。



## 七、架构

### 概述

#### NoSQL

HBase是一个分布式的数据库，相比于传统的RDMBS，它不支持SQL语句的操作，也缺少一些RDMBS的功能，例如：事物，二级索引，类型列，触发器和高级查询语言等。

优点是：HBase有很强的线性和模块化缩放的功能，可以通过增加RegionServer来扩展。

HBase的特点是：

- 强一致读/写：HBase 不是”最终一致”的数据存储。这使得它非常适合高速计数聚合等任务。
- 自动分片：HBase 表通过 region 分布在集群上，并且随着数据的增长，region 会自动分割和重新分配。
- RegionServer 自动故障转移
- 集成 Hadoop/HDFS：HBase 支持 HDFS 作为其分布式文件系统。
- MapReduce：HBase 可以作为 source 和 sink，通过 MapReduce 来支持大规模并行处理。
- Java 客户端 API：HBase 支持易用的 Java API 以进行编程式访问。
- Thrift/REST API：对于非 Java 前端访问，HBase 还支持 Thrift 和 REST 方式。
- 块缓存和布隆过滤器：HBase 支持块缓存和布隆过滤器，以实现高容量查询优化。
- 运维管理：HBase 提供内置网页监控运维情况，并支持 JMX 指标。

#### 适用场景

1. 足够庞大的数据量：上亿条数据适合HBase，如果只是百万级别，传统的关系型数据库更适合，因为数据量太小有可能会让所有数据放到一个节点中，而其他节点是空闲的。
2. 确认可以不用RDMBS的额外功能：从RDMBS到HBase不是简单的替换JDBC Driver就可以，需要重新设计架构。
3. 确认有足够的硬件设施：少于5个DataNode时，HDFS甚至什么都做不了。

#### HBase与HDFS的区别

**HDFS是一个分布式文件系统，适合存储大型文件，并且不能快速单个的记录查找。而HBase是建立在HDFS之上的，他可以快速的记录查找和更新，它是将存储索引的StoreFiles放在HDFS之上，从而进行快速的查找。**

### 目录表(hbase:meta)

#### hbase:meta

保存系统中所有 region 的列表，`hbase：meta` 被存储在 ZooKeeper 中。

启动顺序：首先，在 ZooKeeper 中查询 `hbase:meta` 的位置信息。然后，使用服务器和启动编码更新 `hbase:meta` 的值。

### Client（客户端）

客户端会到`hbase:meta`表中找到特定行范围提供服务的RegionServer，找到所需要的region之后，客户端直接和RegionServer链接进行读写操作，并把这类信息缓存在本地客户端中，下次找相同范围的行时就不需要再次查找了。当然，如果RegionServer挂掉了或者负载均衡器重新分配了，Client也会重新查找。

- 管理功能是通过`Admin`实例完成。

#### 集群连接

从`ConnectionFactory`中获取一个`Connection`对象，然后在从中获取`Admin`，`Table`和`RegionLocator`。

**源码分析**

```
//获取连接函数方法
ConnectionFactory.createConnection(Configuration conf)
```

```
public interface Connection extends Abortable, Closeable {
    Configuration getConfiguration();
	
	//获取Table的方法1
    default Table getTable(TableName tableName) throws IOException {
        return this.getTable(tableName, (ExecutorService)null);
    }
	//获取Table的方法2
    default Table getTable(TableName tableName, ExecutorService pool) 
    		throws IOException {
        return this.getTableBuilder(tableName, pool).build();
    }

    BufferedMutator getBufferedMutator(TableName var1) throws IOException;

    BufferedMutator getBufferedMutator(BufferedMutatorParams var1) throws IOException;
	
	//获取RegionLocator的函数方法
    RegionLocator getRegionLocator(TableName var1) throws IOException;
	
	//获取Admin函数方法
    Admin getAdmin() throws IOException;

    void close() throws IOException;

    boolean isClosed();

    TableBuilder getTableBuilder(TableName var1, ExecutorService var2);
}
```

`Connection`是线程安全的重量级对象，可以保持一个实例，最后关闭；而`Admin`，`Table`和`RegionLocator`是轻量级的，可以随时创建和关闭（疑似用到了缓冲区，线程不安全，所以需要关闭）。



#### APIs

- Read APIs
  - get
  - scan
- Write APIs
  - put
  - batch put
  - delete
- Combination(read-modify-wirte)APIs
  - incrementColumnValue
  - checkAndPut

ACID特性：

1. 原子性：操作要么全部完成要么全部失败，是原子的，不可分割的
2. 一致性：所有操作都会导致表从一种有效状态**直接**转换为另一种有效状态（例如，更新期间行不会消失，等等）
3. 隔离性：如果操作完全独立于其他并发操作，那么该操作就是隔离的
4. 耐久性：向客户端报告“成功”的任何更新都不会丢失
5. 可见性：如果任何后续读取将更新视为已提交，则认为更新是可见的

##### 批处理方法

在`Table`接口中定义了批处理方法：

```
default void batch(List<? extends Row> actions,
                   Object[] results) throws IOException,InterruptedException
```

> 函数是 Deletes, Gets, Puts, Increments, Appends, RowMutations 操作的批处理。 actions中的执行顺序是未定义的。也就是说如果你在同一个batch函数中做了 Put and a Get 操作，不一定会保证Get会返回Put所放置的内容。
>
> Parameters:
>
> actions - 一系列 Get, Put, Delete, Increment, Append, RowMutations操作的List集合。
> results - 空的 Object[]数组, 和actions的集合大小相同.。如果抛出异常，则提供对部分结果的访问。 如果数组结果为空说明即使重试，操作调用也是失败的。结果数组中的对象顺序和actions的一致。



#### 异步客户端

从 `ConnectionFactory` 中获取 `AsyncConnection`，然后从中获取一个异步表实例来访问 HBase。处理完成后，关闭 `AsyncConnection` 实例（通常在程序退出时）。

**源码**

```
public static CompletableFuture<AsyncConnection> createAsyncConnection(Configuration conf)
```



**与非异步表的区别**

对于异步表，因为这里没有任何缓冲区，所以没有异步表的 close 方法，你不需要关闭它。它是线程安全的。

对于 scan，有几点不同：

- 仍然有一个 `getScanner` 方法返回一个 `ResultScanner`。可以以旧方式使用它，它的工作方式类似于旧的 `ClientAsyncPrefetchScanner`。
- 有一个 `scanAll` 方法，它会立即返回所有结果。它的目的是为通常希望立即获得全部结果的小范围扫描提供更简单的方法。
- 观察者模式。 有一个 scan 方法接受 `ScanResultConsumer` 作为参数。 它会将结果传递给消费者。

请注意，`AsyncTable` 接口是模板化的。模板参数指定扫描使用的 `ScanResultConsumerBase` 的类型，这意味着观察者类型的 scan API 是不同的。scan 的消费者有两种类型 - `ScanResultConsumer` 和 `AdvancedScanResultConsumer`。

`ScanResultConsumer` 需要一个单独的线程池，用于执行注册到返回 CompletableFuture 的回调方法。因为使用独立的线程池可以释放 RPC 线程，所以回调可以自由地执行任何操作。如果回调慢或不确定的时候，使用此配置。

`AdvancedScanResultConsumer` 在框架线程内执行回调。不允许在回调中进行耗时操作，否则它可能会阻塞框架线程并导致非常糟糕的性能问题。正如其名称一样，它专为希望编写高性能代码的高级用户而设计。有关如何使用它编写完全异步代码，请参阅 `org.apache.hadoop.hbase.client.example.HttpProxyExample`。



#### 异步管理

从`AsyncConnection`中获取`AsyncAdmin`来访问HBase

```
CompletableFuture<AsyncConnection> asyncConnection = 												ConnectionFactory.createAsyncConnection(config);
AsyncAdmin asyncAdmin = asyncConnection.get().getAdmin();
```

如果要自定义某些配置，可以使用 `getAdminBuilder` 方法获取用于创建 `AsyncAdmin` 实例的 `AsyncAdminBuilder`。返回值通常被 CompletableFuture 包装。

对于大多数管理操作，当返回的 CompletableFuture 完成时，也意味着管理操作已完成。但是对于压缩操作，它只意味着压缩请求被发送到 HBase 并且可能需要一些时间来完成压缩操作。对于 `rollWALWriter` 方法，它只表示 rollWALWriter 请求被发送到 region server，可能需要一些时间来完成 `rollWALWriter` 操作。

对于 region 名称，只接受 `byte[]` 类型的参数，它可以是完整的 region 名称或编码后的 region 名称。对于服务器名称，只接受 `ServerName` 类型的参数。 对于表名，只接受 `TableName` 类型的参数。 对于 `list*` 操作，如果想要进行正则表达式匹配，只接受 `Pattern` 类型的参数。



### 客户端请求过滤器

