package etl.util;

import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @program: Graduation->etl.util->IpUtil
 * @description: 根据IP解析出来的地区信息，对地区信息进行格式化；地区信息数据格式为：国家|区域|省份|城市|网络服务提供商（ISP）
 * @author: Mr.黄
 * @create: 2020-03-16 18:43
 **/
public class IpUtil {
    private String region = null; // 地区信息

    public IpUtil(String IP) {
        this.region = new ParseIP().parseIp(IP);
    }

    private String[] splitRegion() {
        return region.split("\\|");
    }

    // 国家
    public String getCountry() {
        return splitRegion()[0];
    }

    // 省份
    public String getProvince() {
        return splitRegion()[2];
    }

    // 城市
    public String getCity() {
        return splitRegion()[3];
    }

    // 运营商
    public String getISP() {
        return splitRegion()[4];
    }

    // 原始数据
    public String getRegion() {
        return this.region;
    }

    /**
     * @program: Graduation->etl.ip->ParseIP
     * @description: 使用第三方工具包ip2region解析IP，获取标准化数据格式，部分字段无法解析显示结果为0
     *               标准化数据格式：国家|区域|省份|城市|网络服务提供商（ISP）
     * @author: Mr.黄
     * @create: 2020-03-16 14:18
     **/
    private class ParseIP {
        private DbConfig config = null;
        private DbSearcher searcher = null;
        private java.util.logging.Logger logger = Logger.getLogger(ParseIP.class.getName());

        {
            String dbfile = ParseIP.class.getClassLoader().getResource("ip2region.db").getPath();
            try {
                config = new DbConfig();
                searcher = new DbSearcher(config, dbfile);
            } catch (DbMakerConfigException e) {
                logger.warning("ip2region config init exception:" + e.getMessage());
            } catch (FileNotFoundException e) {
                logger.warning("ip2region file not found" + e.getMessage());
            }
        }

        /**
         * @Description: 解析ip，获取地区信息
         * @param ip IP地址
         * @return: 返回标准化格式的地区信息，标准化格式：国家|区域|省份|城市|运营商
         * @Author: Mr.黄
         * @Date: 2020/3/16
         */
        private String parseIp(String ip) {
            boolean isIpAddress = Util.isIpAddress(ip);
            if (isIpAddress) {
                try {
                    return searcher.btreeSearch(ip).getRegion();
                } catch (IOException e) {
                    logger.warning("ip2region parse error" + e.getMessage());
                }
            }
            return null;
        }
    }
}