import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{LongType, MapType, StringType, StructField, StructType}

/**
  * @program: Graduation->->T
  * @description:
  * @author: Mr.黄
  * @create: 2020-03-17 22:11
  **/
object T {
    def main(args: Array[String]): Unit = {
        val m = MapType(StringType, StringType)
        val l = 9
        println(l)
        val row = Row("abc", 23, ("A" -> "a"), (1, 2, 3), ("a", 5))
        println(row)
        println(row.getStruct(1))

//        val l = m(("" -> ""))
    }
}
