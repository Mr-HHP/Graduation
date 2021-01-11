package etl

import etl.util.IpUtil
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{LongType, MapType, StringType, StructField, StructType}


/**
  * @program: Graduation->etl->SparkStatFormatJob
  * @description: 第一步清洗数据，将原始日志数据根据字段拆分出来，存入hive中供业务操作
  * @author: Mr.黄
  * @create: 2020-03-15 22:17
  **/
object SparkStatFormatJob {
    // 定义输出的字段
    val struct = StructType(
        Array(
            StructField("ip", StringType),
            StructField("geo", MapType(StringType, StringType)), // 国家，省份，城市
            StructField("isp", StringType), // 运营商
            StructField("time", StringType),
            StructField("request_type", StringType),
            StructField("url", StringType),
            StructField("request_version", StringType),
            StructField("status", StringType),
            StructField("body_bytes_sent", LongType),
            StructField("http_host", StringType),
            StructField("http_referer", StringType),
            StructField("request_body", StringType),
            StructField("userAgent", MapType(StringType, StringType)),
            StructField("upstream_addr", StringType),
            StructField("upstream_status", StringType),
            StructField("upstream_response_time", LongType),
            StructField("request_time", LongType),
            StructField("other_params", MapType(StringType, StringType))
        )
    )

    def init(): RDD[String] = {
        val inputFile: String = "file:///C:\\Users\\\\Desktop\\毕设\\test_data"
        // 创建Spark Session对象，并设置app名字，设置运行模式，测试阶段使用local模式运行
        lazy val spark = SparkSession.builder().appName("etl_format_job").master("local[*]").getOrCreate()
        spark.sparkContext.textFile(inputFile)
    }

    // 解析IP获取地区信息，Map形式存储
    def getGeo(ip: String): Unit = {
        val ipUtil = new IpUtil(ip)
        ipUtil.getCountry // 国家
        ipUtil.getProvince // 省份
        ipUtil.getCity // 城市
        ipUtil.getISP // 运营商
        MapType(StringType, StringType)
    }

    // 解析userAgent字段，获取浏览器、系统等信息，Map形式存储
    def getUserAgent(lineLog: String): String = lineLog.split("\"")(7)

    def parseLog(lineLog: String) = {
        val tmp: Array[String] = lineLog.split(" ")
        try {
            val ip = tmp(0)
            val time = tmp(3) + tmp(4)
            val request_type = tmp(5)
            val url = tmp(6)
            val request_version = tmp(7)
            val status = tmp(8)
            val body_bytes_sent = tmp(9)
            val http_host = tmp(10)
            val http_referer = tmp(11)
            val request_body = tmp(12)
            val userAgent = getUserAgent(lineLog)
            val upstream_addr = tmp(tmp.length - 4)
            val upstream_status = tmp(tmp.length - 3)
            val upstream_response_time = tmp(tmp.length - 2)
            val request_time = tmp(tmp.length - 1)
        } catch {
            case e: Exception => Row(0)
        }
    }

}
