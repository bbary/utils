package com.bba.parsing

import java.io.{BufferedWriter, File, FileNotFoundException, FileWriter}

import scala.collection.mutable.ListBuffer
import scala.io.{BufferedSource, Source}
import java.io.IOException
import java.io.PrintWriter

/**
  * Created by brahim on 9/30/17.
  */
object Main {

  def main(args: Array[String]): Unit = {


    val input_path: String = "./input/test5M.csv"
    val penality_per_day: Double = 0.95
    val threshold = 0.01
    val aggratings_file: String = "./output/aggratings.csv"
    val lookupuser_file: String = "./output/lookupuser.csv"
    val lookup_product_file: String = "./output/lookup_product.csv"

    // reading input

    var start = System.currentTimeMillis()
    var csv_file: BufferedSource = null
    try
      csv_file = Source.fromFile(input_path)
    catch {
      case ioe: FileNotFoundException =>
        println(s"File not found in  Path $input_path")
        ioe.printStackTrace()
        sys.exit(1)
    }

    println(s"reading file took ${(System.currentTimeMillis()-start)} ms")

    // parsing the input file

    start = System.currentTimeMillis()
    //val rows = new ListBuffer[(String, String, Float, Long)]()
    var counter = 0
    val rows: List[(String, String, Float, Long)] = csv_file.getLines
      .map( line => line.split(","))
      .map(row =>(row(0), row(1), row(2).toFloat, row(3).toLong))
      .toList

    println(s"parsing the input file took ${(System.currentTimeMillis()-start)} ms")

    // processing

    start = System.currentTimeMillis()

    var lookupuser = rows.map(line => line._1).distinct.zip(Stream.from(0))
    val usersMap = lookupuser.toMap

    println(s"toMap 1 took ${(System.currentTimeMillis()-start)} ms")

    start = System.currentTimeMillis()

    var lookup_product = rows.map(line => line._2).distinct.zip(Stream.from(0))
    val productsMap = lookup_product.toMap

    println(s"toMap 2 took ${(System.currentTimeMillis()-start)} ms")

    start = System.currentTimeMillis()
/*
    val aggratings = rows.map(line => (usersMap.get(line._1).get, productsMap.get(line._2).get, line._3, line._4))
      .groupBy(k =>  (k._1, k._2))
      .mapValues(lines => (lines.map(_._3).sum * penilaty_cal(timestamp_diff(lines.map(_._4).max), penality_per_day)))
      .filter( line => line._2 <= threshold)
*/
    val startp = System.currentTimeMillis()
    val trans = rows.map(line => (usersMap.get(line._1).get, productsMap.get(line._2).get, line._3, line._4))
    println(s"map 1 took ${(System.currentTimeMillis()-startp)} ms")
    start = System.currentTimeMillis()
    val grouped = trans.groupBy(k =>  (k._1, k._2))
    println(s"groupBy2Keys took ${(System.currentTimeMillis()-start)} ms")
    start = System.currentTimeMillis()
    val trans2 = grouped.mapValues(lines => (lines.map(_._3).sum * penilaty_cal(timestamp_diff(lines.map(_._4).max), penality_per_day)))
    println(s"mapValue took ${(System.currentTimeMillis()-start)} ms")
    start = System.currentTimeMillis()
    val aggratings = trans2 .filter( line => line._2 <= threshold)
    println(s"filter took ${(System.currentTimeMillis()-start)} ms")

    println(s"all processing took ${(System.currentTimeMillis()-startp)} ms")

    // writing to file

    start = System.currentTimeMillis()

    val directory = new File("./output")
    if (!directory.exists) {
      directory.mkdir
    }

    try {
      val lookupuser_file_writer = new PrintWriter(lookupuser_file, "UTF-8")
      lookupuser.foreach( line => {
        lookupuser_file_writer.write(s"${line._1},${line._2}\n")
      })
      lookupuser_file_writer.close()

      val lookup_product_file_writer = new PrintWriter(lookup_product_file, "UTF-8")
      lookup_product.foreach( line => {
        lookup_product_file_writer.write(s"${line._1},${line._2}\n")
      })
      lookup_product_file_writer.close()

      val aggratings_file_writer = new PrintWriter(aggratings_file, "UTF-8")
      aggratings.foreach( line => {
        aggratings_file_writer.write(s"${line._1._1},${line._1._2},${line._2}\n")
      })
      aggratings_file_writer.close()

    } catch {
      case e: IOException =>
        e.printStackTrace()
        sys.exit(1)

    }

    println(s"writing to file took ${(System.currentTimeMillis()-start)} ms")

    csv_file.close

    // test timestamp_diff
    /*val df:SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
    val date = df.parse("2017-10-01")
    println(timestamp_diff(date.getTime))

    test collections

    println(rows)
    println(rows.map(line => (usersMap.get(line._1).get, productsMap.get(line._2).get, line._3, line._4)))
    println(aggratings)
    */


  }

  def timestamp_diff(ts: Long): Long = {
    val curr_ts: Long = System.currentTimeMillis
    // 1 day = 24 x 60 x 60 x 1000 ms = 86 400 000 ms
    // for testing
    //val df:SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
    //println("date "+df.format(ts))
    (curr_ts-ts)/86400000
  }

  def penilaty_cal(days_delay: Long, penality_per_day: Double): Double = {
    if( days_delay == 0 ) 1
    else Math.pow(penality_per_day,days_delay)
  }
}
