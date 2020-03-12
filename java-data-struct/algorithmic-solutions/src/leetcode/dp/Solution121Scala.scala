package leetcode.dp

object Solution121Scala {

  def maxProfit(prices: Array[Int]): Int = {
    var min: Int = Integer.MAX_VALUE
    var maxP = 0

    for (i <- 0 until prices.length) {
      val t = prices(i)
      if (min > t) min = t
      val tp = t - min
      if (tp > 0) if (tp > maxP) maxP = tp
    }
    maxP
  }


}
