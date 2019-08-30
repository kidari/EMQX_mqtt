import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * @author sunduo
 * @note current_time
 * @date 2019/6/10 16:13
 */
public class Time {
    /**
     * String转Date
     * @input String
     */
    public static Date StringToDate(String time){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date  date = null;
        try {
            date = formatter.parse(time);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
    /**
     * Date转String
     * @input Date
     */
    public static String DateToString (Date time){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(time);
    }
    /**
     * 获取当前时间
     * @return String  "yyyy-MM-dd HH:mm:ss"
     */
    public static String timeNow (){

        long currentTime = System.currentTimeMillis();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date(currentTime);

        return formatter.format(date);
    }
    /**
     * 获取当前时间
     * @input '_'
     * @return String  "yyyy_MM_dd_HHmmss"
     */
    public static String timeNow (char s){
        if((byte)s == 95){
            long currentTime = System.currentTimeMillis();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HHmmss");

            Date date = new Date(currentTime);

            return formatter.format(date);
        }else if(s == ' '){
            long currentTime = System.currentTimeMillis();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

            Date date = new Date(currentTime);

            return formatter.format(date);
        }else{
            return timeNow ();
        }


    }
    /**
     * 计算时间
     * @input String  "yyyy-MM-dd HH:mm:ss"
     * @return String  "yyyy-MM-dd HH:mm:ss"
     */
    public static String timeCount (String time, int service_life){


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date  date = null;
        try {
            date = formatter.parse(time);
            Calendar calendar = Calendar.getInstance();
            // 获取service_life后时间
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, service_life);
            Date frontCountDate = calendar.getTime();
            return formatter.format(frontCountDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }

    /**
     * 格式化iot平台时间并将格林威治时间转成目标时间
     * @return String  "yyyy-MM-dd HH:mm:ss"
     * sourceId = "Asia/Shanghai"
     */
    public static String convertIotTime(String iotTime){
        try {
        String work_date = iotTime.split("T")[0];
        String work_time = iotTime.split("T")[1].substring(0,iotTime.split("T")[1].length()-1);

        SimpleDateFormat sdf_input = new SimpleDateFormat("yyyyMMddHHmmss");//输入格式
        SimpleDateFormat sdf_output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String sourceTime= sdf_output.format(sdf_input.parse(work_date+work_time));

//        System.out.println("原时间:" + sourceTime);

        //原时区Greenwich,GMT0
        String sourceId = "Greenwich";
        //目标时区东八区Asia/Beijing,GMT+8:00
        String targetId = "Asia/Shanghai";

        //转换后的时间
        return timeConvert(sourceTime, sourceId, targetId);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 获取本地默认时区id
     * @return string 本地时区id
     */
    public static String getLocalTimeId()
    {
        TimeZone defaultTimeZone = TimeZone.getDefault();
        return defaultTimeZone.getID();
    }

    /**
     * 获取受支持的所有可用 ID
     * 用来作为页面显示的时区下拉列表
     * 以绝对时区显示（不考虑夏令时）
     * @return map 存储时区列表+偏移量的map(可用来显示如Hongkong,GMT+08:00)
     * 实际使用时，传给服务器是零时区，值传递时区id就可以了，不传递偏移量
     */
    public static Map<String, String> getZoneList()
    {
        String[] zoneIds = TimeZone.getAvailableIDs();
        int length = zoneIds.length;
        TimeZone timeZone = null;
        //存储时区列表+偏移量到map中
        Map<String, String> map = new HashMap<String, String>(650);
        long offset = 0L;
        String diplayOffset = "";
        for (String zoneId : zoneIds) {
            //获取给定 ID 的 TimeZone
            timeZone = TimeZone.getTimeZone(zoneId);
            //返回添加到 UTC 以获取此时区中的标准时间的时间偏移量（以毫秒为单位）。
            offset = timeZone.getRawOffset();
            //对偏移量做显示，如GMT-09:30、GMT+09:30
            diplayOffset = appendZoneSuffix(offset);
            //存储到map中，形式为Hongkong---GMT+08:00
            map.put(zoneId, diplayOffset);
        }
        return map;
    }

    /**
     * 添加时区偏移量
     * @param offset 偏移量（以毫秒为单位）
     * @return 日期
     */
    public static String appendZoneSuffix(long offset)
    {
        //将偏移量转化为小时（小数去除不要）
        long hour = offset / 3600000;
        //偏移量对小时取余数，得到小数（以毫秒为单位）
        double decimals = offset % 3600000;
        //显示为09:30分钟形式
        double decimalsZone = (decimals / 3600000) * 60 / 100;
        String sAdd = "";
        if (hour >= 0)
        {
            sAdd = "+";
        }
        else
        {
            sAdd = "-";
        }
        hour = hour > 0 ? hour : -hour;
        String sHour = hour + "";
        if (sHour.length() == 1)
        {
            sHour = '0' + sHour;
        }

        decimalsZone = decimalsZone < 0 ? -decimalsZone : decimalsZone;
        String sDecimalsZone = decimalsZone + "";
        sDecimalsZone = sDecimalsZone.substring(2);
        if (sDecimalsZone.length() == 1)
        {
            sDecimalsZone = sDecimalsZone + '0';
        }
        else if (sDecimalsZone.length() >= 3)
        {
            sDecimalsZone = sDecimalsZone.substring(0, 2);
        }
        return "GMT" + sAdd + sHour + ':' + sDecimalsZone;
    }

    /**
     * 时区 时间转换方法:将当前时间（可以为其他时区）转化成目标时区对应的时间
     * @param sourceTime 时间格式必须为：yyyy-MM-dd HH:mm:ss
     * @param sourceId 入参的时间的时区id
     * @param targetId 要转换成目标时区id（一般是是零时区：取值UTC）
     * @return String 转化时区后的时间
     */
    public static String timeConvert(String sourceTime, String sourceId,
                                     String targetId)
    {
        //校验入参是否合法
        if (null == sourceId || "".equals(sourceId) || null == targetId
                || "".equals(targetId) || null == sourceTime
                || "".equals(sourceTime))
        {
            return "";
        }
        //校验 时间格式必须为：yyyy-MM-dd HH:mm:ss
        String reg = "^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$";
        if (!sourceTime.matches(reg))
        {
            return "";
        }

        try
        {
            //时间格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //根据入参原时区id，获取对应的timezone对象
            TimeZone sourceTimeZone = TimeZone.getTimeZone(sourceId);
            //设置SimpleDateFormat时区为原时区（否则是本地默认时区），目的:用来将字符串sourceTime转化成原时区对应的date对象
            df.setTimeZone(sourceTimeZone);
            //将字符串sourceTime转化成原时区对应的date对象
            Date sourceDate = df.parse(sourceTime);

            //开始转化时区：根据目标时区id设置目标TimeZone
            TimeZone targetTimeZone = TimeZone.getTimeZone(targetId);
            //设置SimpleDateFormat时区为目标时区（否则是本地默认时区），目的:用来将字符串sourceTime转化成目标时区对应的date对象
            df.setTimeZone(targetTimeZone);
            //得到目标时间字符串
            return df.format(sourceDate);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return "";

    }
    public static void main(String[] args) {
//        String s = convertIotTime("20160229T183456Z");
//        System.out.println("转换后时间："+ s);
//        System.out.println(StringToDate(timeCount(timeNow(),24)));
//        System.out.println(StringToDate("2016-02-29 18:34:56"));
        System.out.println(timeNow(' '));
        String s = "2019-07-09T12:43:13.000+0000";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(formatter.format(s));
    }
}
