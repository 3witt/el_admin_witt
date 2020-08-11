package me.zhengjie;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Authoor: witt
 * @Decsription:
 * @Date: Created in 9:08 2020/8/10
 * @Modified:
 **/
public class test {

    public static void main(String[] args) {
        testNum();
    }

    public static void testNum() {
        BigDecimal i = new BigDecimal("-1.1111234");
        BigDecimal e = new BigDecimal("-4.12");

        // 加
        BigDecimal add = i.add(e);
        // 减
        BigDecimal subtract = i.subtract(e);
        // 乘
        BigDecimal multiply = i.multiply(e);
        // 除
        BigDecimal divide = i.divide(e, 2, BigDecimal.ROUND_HALF_UP);
        System.out.println(add.compareTo(BigDecimal.ZERO) == -1 ? add.negate() : add.toString());
        System.out.println(subtract);
        System.out.println(multiply);
        System.out.println(divide);


        BigDecimal multiply1 = e.subtract(i);
        System.out.println(multiply1.toString());
        System.out.println(multiply1.compareTo(BigDecimal.ZERO) == -1 ? multiply1.negate() : multiply1.toString());


    }

    public static void getDate() {

        long startTime1 = 1530613938532l;
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);//设置星期一为一周开始的第一天
        calendar.setMinimalDaysInFirstWeek(4);//可以不用设置
        calendar.setTimeInMillis(System.currentTimeMillis());//获得当前的时间戳
        int weekYear = calendar.get(Calendar.YEAR);//获得当前的年
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);//获得当前日期属于今年的第几周

        System.out.println("第几周：" + weekOfYear);
        calendar.setWeekDate(weekYear, weekOfYear, 2);//获得指定年的第几周的开始日期
        long starttime = calendar.getTime().getTime();//创建日期的时间该周的第一天，
        calendar.setWeekDate(weekYear, weekOfYear, 1);//获得指定年的第几周的结束日期
        long endtime = calendar.getTime().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = simpleDateFormat.format(starttime);//将时间戳格式化为指定格式
        String dateEnd = simpleDateFormat.format(endtime);
        System.out.println(dateStart);
        System.out.println(dateEnd);
    }
}
