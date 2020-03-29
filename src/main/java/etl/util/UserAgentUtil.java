package etl.util;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: Graduation->etl.util->UserAgentUtil
 * @description: 解析日志中的user agent的工具类，
 * @author: Mr.黄
 * @create: 2020-03-16 14:49
 **/
public class UserAgentUtil {
    private String userAgent; // 用户代理信息

    private UserAgent ua = null; // UserAgent类
    private Browser browser = null; // 浏览器类
    private OperatingSystem operatingSystem = null; // 操作系统类
    private Version version = null; // 版本类

    public UserAgentUtil(String userAgent) {
        this.userAgent = userAgent;
        this.ua = UserAgent.parseUserAgentString(userAgent);
        this.browser = ua.getBrowser();
        this.operatingSystem = ua.getOperatingSystem();
        this.version = ua.getBrowserVersion();
    }

    // 获取浏览器名称
    public String getBrowserName() {
        return browser != null ? browser.getGroup().getName() : "Unknown";
    }

    // 获取浏览器版本
    public String getBrowserVersion() {
        return version != null ? version.getVersion() : "Unknown";
    }

    // 获取系统名称
    public String getSystemName() {
        return operatingSystem != null ? operatingSystem.getGroup().getName() : "Unknown";
    }

    // 获取系统版本
    public String getSystemVersion() {
        return operatingSystem != null ? operatingSystem.getName() : "Unknown";
    }

    // 获取平台
    public String getPlatform() {
        return operatingSystem != null ? operatingSystem.getDeviceType().toString() : "Unknown";
    }

    // 获取用户代理
    public String getUserAgent() {
        return userAgent;
    }
}