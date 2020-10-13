# 动态规划

**动态规划的思想：将原问题拆解为若干个子问题，同时保存子答案的答案，使得每个子问题只求解一次，最终得到原问题的答案**
```
                     记忆化搜索：自顶向下
                    /
递归问题 -> 重叠子问题&最优子结构       
                    \        
                     动态规划：自底向上
```
- 动态规划在查找有很多**重叠子问题**的情况的最优解时有效。它将问题重新组合成子问题。为了避免多次解决这些子问题，它们的结果都逐渐被计算并被保存，从简单的问题直到整个问题都被解决。因此，动态规划保存递归时的结果，因而不会在解决同样的问题时花费时间。

- 动态规划只能应用于有**最优子结构**的问题。最优子结构的意思是局部最优解能决定全局最优解（对有些问题这个要求并不能完全满足，故有时需要引入一定的近似）。简单地说，问题能够分解成子问题来解决。