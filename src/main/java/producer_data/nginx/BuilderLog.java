package producer_data.nginx;

import org.apache.commons.lang3.time.FastDateFormat;

import java.io.*;
import java.text.ParseException;
import java.util.Locale;

/**
 * @program: Graduation->log->BuilderLog
 * @description: 模拟日志数据，一秒产生一行日志，按小时进行存储
 * @author: Mr.黄
 * @create: 2020-03-12 18:45
 **/
public class BuilderLog {
    public static void main(String[] args) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader("D:\\BigDataProject\\Graduation\\src\\main\\resources\\test_data.log")); // 本地测试数据
//            br = new BufferedReader(new FileReader("/home/hdfs/hhp/graduation/data/200_access.log")); // 测试数据
//            br = new BufferedReader(new FileReader("/home/hdfs/hhp/graduation/data/access.20161111.log")); // 真实数据
            String line = br.readLine();

            // 按小时区分日志，按小时进行存储
            FastDateFormat dateFormat = FastDateFormat.getInstance("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);
//            FastDateFormat sdf = FastDateFormat.getInstance("yyyyMMddHH"); // 真实环境根据小时级别进行划分
//            FastDateFormat sdf = FastDateFormat.getInstance("yyyyMMddHHmmss"); // Liunx本地测试使用秒级别
            FastDateFormat sdf = FastDateFormat.getInstance("yyyyMMddHHmmss"); // Windows本地测试使用秒级别
            String tmp_date = line.split(" ")[3].substring(1);// 日志中的原始日期信息
            long original_date = Long.parseLong(sdf.format(dateFormat.parse(tmp_date)));

            System.out.println(original_date);

//            bw = new BufferedWriter(new FileWriter("/home/hdfs/graduation/log/access." + original_date + ".log", true)); // 真实路径
//            bw = new BufferedWriter(new FileWriter("/home/hdfs/graduation/test/access." + original_date + ".log", true)); // 测试路径
            bw = new BufferedWriter(new FileWriter("D:\\BigDataProject\\Graduation\\src\\main\\java\\tmp\\a." + original_date + ".log", true)); // 本地测试路径
            while (line != null) {
                // 转换日期格式，英文转换成中文
                tmp_date = line.split(" ")[3].substring(1);
                long date = Long.parseLong(sdf.format(dateFormat.parse(tmp_date)));
                if (date != original_date) {
                    original_date = date;
//                    bw = new BufferedWriter(new FileWriter("/home/hdfs/graduation/log/access." + original_date + ".log", true)); // 真实路径
//                    bw = new BufferedWriter(new FileWriter("/home/hdfs/graduation/test/access." + original_date + ".log", true)); // 测试路径
                    bw = new BufferedWriter(new FileWriter("D:\\BigDataProject\\Graduation\\src\\main\\java\\tmp\\a." + original_date + ".log", true)); // 本地测试路径
                }
                bw.write(line);
                bw.newLine();
                bw.flush();
                // 一秒产生一条日志
                Thread.sleep(1000);
                line = br.readLine();
            }
            bw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}