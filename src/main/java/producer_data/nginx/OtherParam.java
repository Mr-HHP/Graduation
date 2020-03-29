package producer_data.nginx;

import java.util.Random;

/**
 * @program: Graduation->producer_data.nginx->OtherParam
 * @description: 模拟cookie、分辨率、色深、语言、字符集等字段
 * @author: Mr.黄
 * @create: 2020-03-29 16:47
 **/
public class OtherParam {
    private static final String[] RESOLUTION = {"360x640", "360x760", "360x780", "375x667", "414x736", "414x896", "800x600", "1366x768", "1440x900", "1920x1080"}; // 分辨率
    private static final String[] COLOR_DEPTH = {"9", "10", "12", "16", "24", "32"}; // 色深
    private static final String[] CHARSET = {"UTF-8", "UTF-16", "GB2312", "GBK", "ANSI"}; // 字符集
    private static final String LANGUAGE = "zh-CN"; // 语言

    private final StringBuilder OTHER_PARAM = new StringBuilder();
    private Random random = new Random();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(new OtherParam().getOtherParam());
        }
    }

    // 设置cookie
    private String setCookie() {
        StringBuilder cookie = new StringBuilder();
        for (int j = 0; j < 3; j++) {
            double randTri = Math.random() * 3;
            if (randTri >= 0 && randTri < 1) {
                cookie.append((char) (Math.random() * ('9' - '0') + '0'));
            } else if (randTri >= 1 && randTri < 2) {
                cookie.append((char) (Math.random() * ('Z' - 'A') + 'A'));
            } else {
                cookie.append((char) (Math.random() * ('z' - 'a') + 'a'));
            }
        }
        return cookie.toString();
    }

    // 设置分辨率
    private String setResolution() {
        return RESOLUTION[random.nextInt(RESOLUTION.length)];
    }

    // 设置颜色深度
    private String setColor_depth() {
        return COLOR_DEPTH[random.nextInt(COLOR_DEPTH.length)];
    }

    // 设置语言
    private String setLanguage() {
        return LANGUAGE;
    }

    // 设置字符集
    private String setCharset() {
        return CHARSET[random.nextInt(CHARSET.length)];
    }

    public String getOtherParam() {
        return OTHER_PARAM.append(setCookie()).append(" ")
                .append(setResolution()).append(" ")
                .append(setColor_depth()).append(" ")
                .append(setLanguage()).append(" ")
                .append(setCharset()).append(" ")
                .toString();
    }
}