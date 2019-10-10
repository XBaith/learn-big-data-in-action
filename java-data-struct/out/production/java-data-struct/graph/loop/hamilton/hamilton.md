# 哈密尔顿回路
从一个点出发，沿着边走，**经过每个顶点恰好一次**，之后再回到出发点

条件：
- 回到出发点
- 经过每个顶点恰好一次

属于NP难的问题，即找不到多项式级的算法，只有指数级

## 求解哈密尔顿回路
**回溯法**: 暴力求解，遍历所有的可能性
```
0 - 3
| \ |
2 - 1
```
**step1**: 0->1->2，3还没有访问过，此时回溯到0->1,并把2恢复为没有访问过；0->1->3，2没有被访问过，回溯到0->1，3恢复未访问过，再次回溯到0，恢复1的visited

**step2**: 0->2->1->3(->0)，此时得到一个哈密尔顿回路

- 对于哈密尔顿回路，因为条件包含每个顶点都恰好访问一次，因此只从一个顶点搜索即可

- 时间复杂度：O(n!)

### 优化
在判断全部顶点已经遍历过时，可以用一个变量代表还有多少个顶点没有遍历过，避免遍历全部顶点

```
dfs(int v, int parent, int left){
visited[v] = true;
pre[v] = parent;
left--;
for(w : adj(v)){
    if(!visited[w])
        if(dfs(w, v, left)return true;
    else if(w == 0 && left == 0){
        end = v;
        return true;
    }
visited[v] = false;
return false
}
```

#### 状态压缩
例如：
- 二维坐标用一个数字表示
- 两个桶的水量用一个数来表示，10位表示a桶，个位表示b桶水量

##### 位运算
**visited数组可以用一个int(32位)/long(64位)数来表示**

用二进制数表示，例如：
visited数组中1代表true, 0代表false

|二进制码|二进制表示|十进制表示|
|---|---|---|
|1 0 1 0| 0b0101|5|

- 判断第n位是否为1时(visited[v] == true)：
    二进制中：第0位是否为1：0b0101 & 0b0001；第1位是否为1：0b0101 & 0b0010；以此类推...
    
    第0位是否为1:5 & 1 == 0；
    
    第1位是否为1:5 & 2 == 0
    ...
    
    **即判断n位是否为1，需要判断：visited & (1 << i) == 0**
    
- 设置第n位为1(visited[v] = true)：
    
    将第1位设为1：5+2
    
    将第3为设置为1：5+8
    
    **visited + (1 << i)**
    
- 将第n位为0(visited[v] = false)：
    **visited - (1 << i)**
    
#### 记忆化搜索
用一个二维数组memo[1 << grapg.vs][grapg.vs]来记录dfs(visited, v)的状态，来避免一些多次重复的搜索

例如：
```
1--4  6--7
|/\| \|/\|
3--2  5--8
```
1. 1->2->3->4->5...
2. 1->3->2->4->5...

这两种情况都无法得到哈密尔顿回路，却因为顶点遍历顺序的不同多次遍历右边5,6,7,8顶点

- 时间复杂度O(n*2^n)对比O(n!)需要以情况而定，如果有多次的重复访问，记忆化搜索更优。

## 哈密尔顿路径
每个顶点都经过一次，但不需要回到出发点，因此只需要判断所有顶点是否全部遍历完。