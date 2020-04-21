package leetcode.recall;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 * 示例:
 * 输入："23"
 * 输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 */
public class PhoneNumber {

    static final String[] letterMap = {
            " ", //0
            "", //1
            "abc",  //2
            "def",  //3
            "ghi",  //4
            "jkl",  //5
            "mno",
            "pqrs",
            "tuv",
            "wxyz"
    };

    public List<String> res = new ArrayList<>();

    public List<String> letterCombinations(String digits) {
        if(digits.length() == 0) return res;

        parse(digits, 0, "");
        return res;
    }

    private void parse(String digits, int index, String s) {
        if(s.length() == digits.length()){
            res.add(s);
            return;
        }

        char c = digits.charAt(index);
        String letter = letterMap[c - '0'];
        for(int i = 0; i < letter.length(); i++) {
            parse(digits, index + 1, s + letter.charAt(i));
        }

    }

    public static void main(String[] args) {
        PhoneNumber phoneNumber = new PhoneNumber();
        System.out.println(phoneNumber.letterCombinations("23"));
    }
}
