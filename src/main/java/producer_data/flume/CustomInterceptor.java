package producer_data.flume;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @program: Graduation->log.flume->CustomInterceptor
 * @description: 自定义拦截器，将模拟日志内容中的时间添加到header中
 * @author: Mr.黄
 * @create: 2020-03-13 16:49
 **/
public class CustomInterceptor implements Interceptor {
    private CustomInterceptor() {
    }

    // 初始化拦截器
    // 在拦截器的构造方法之后执行，也就是创建完拦截器对象之后执行
    @Override
    public void initialize() {

    }

    // 用来处理每一个Event对象，将log内的日期添加进header中
    @Override
    public Event intercept(Event event) {
        byte[] body = event.getBody();
        String log = new String(body, Charset.forName("UTF-8")); // 原始日志信息
        String str = log.split(" ")[3].substring(1);  // 日志中的日期信息
        // 转换日期格式，英文转换成中文
        FastDateFormat dateformat = FastDateFormat.getInstance("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);
        FastDateFormat sdf = FastDateFormat.getInstance("yyyyMMddHH");
        try {
            String date = sdf.format(dateformat.parse(str));
            event.getHeaders().put("date", date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return event;
    }

    // 用来处理了一批Event对象
    // 集合大小与flume启动配置有关，和transactionCapacity大小保持一致
    // 一般直接调用Event intercept(Event event)处理每一个Event数据
    @Override
    public List<Event> intercept(List<Event> events) {
        Event event;
        List<Event> results = new ArrayList<>();
        for (Event e : events) {
            event = intercept(e);
            if (event != null) {
                results.add(event);
            }
        }
        return results;
    }

    // 销毁拦截器对象值，释放资源的处理
    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder {

        // 该方法主要用来返回创建的自定义拦截器对象
        @Override
        public Interceptor build() {
            return new CustomInterceptor();
        }

        // 用来接收flume配置自定义拦截器参数
        @Override
        public void configure(Context context) {

        }
    }

}