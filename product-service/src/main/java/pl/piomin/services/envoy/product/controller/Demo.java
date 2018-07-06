package pl.piomin.services.envoy.product.controller;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 
 * @author lollipop-xtl
 *
 */
public class Demo {

    public static void main(String[] args) {
       /* Set<String> set = new HashSet<String>();
        Long startTime = new Date().getTime();
        while (true) {
            String code = getCharAndNumr(6);
            set.add(code);
            if (set.size() >= 1000) {
                break;
            }
        }
        Long endTime = new Date().getTime();
        long time = (endTime - startTime);
        System.out.println("生成个数：" + set.size());
        System.out.println("生成耗时" + time + "毫秒，约" + (time / 1000) + "秒");//
        for (String string : set) {
            System.out.println(string);
        }*/
    	System.out.println(getCharAndNumr(16));
    }

    /**
     * java生成随机数字和字母组合
     * 
     * @param length[生成随机数的长度]
     * 
     */
    public static String getCharAndNumr(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}