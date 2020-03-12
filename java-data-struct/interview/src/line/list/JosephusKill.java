package line.list;

public class JosephusKill {
    /**
     * 老编号 = (新编号 + s - 1) % i + 1
     * s = (m - 1) % i + 1
     * s为将被杀的编号，每遍历到m就删除当前节点
     * => 老编号 = (新编号 + (m - 1) % i) % i + 1
     * @param i　当前链表节点数
     * @param m　
     */
    public static int getLive1(int i, int m) {
        if(i == 1) return 1;
        return (getLive1(i - 1, m) + (m - 1) % i) % i + 1;
    }

    public static int getLive2(int i, int m) {
        if(i == 1) return 1;
        return (getLive1(i - 1, m) + m - 1) % i + 1;
    }

    public static void main(String[] args) {
        System.out.println(getLive1(5, 3));
        System.out.println(getLive2(5, 3));
    }
}
