package com.github.plokhotnyuk.jsoniter_scala.benchmark

import java.nio.charset.StandardCharsets.UTF_8

import com.avsystem.commons.serialization.json._
import com.github.plokhotnyuk.jsoniter_scala.benchmark.BorerJsonEncodersDecoders._
import com.github.plokhotnyuk.jsoniter_scala.benchmark.JacksonSerDesers._
import com.github.plokhotnyuk.jsoniter_scala.benchmark.JsoniterScalaCodecs._
import com.github.plokhotnyuk.jsoniter_scala.benchmark.PlayJsonFormats._
import com.github.plokhotnyuk.jsoniter_scala.benchmark.SprayFormats._
import com.github.plokhotnyuk.jsoniter_scala.core._
//import com.jsoniter.input.JsoniterJavaParser
import com.rallyhealth.weejson.v1.jackson.FromJson
import com.rallyhealth.weepickle.v1.WeePickle.ToScala
import io.circe.parser._
import org.openjdk.jmh.annotations.Benchmark
import play.api.libs.json.Json
import spray.json._
import upickle.default._

class ArrayOfCharsReading extends ArrayOfCharsBenchmark {
  @Benchmark
  def avSystemGenCodec(): Array[Char] = JsonStringInput.read[Array[Char]](new String(jsonBytes, UTF_8))

  @Benchmark
  def borerJson(): Array[Char] = io.bullet.borer.Json.decode(jsonBytes).to[Array[Char]].value

  @Benchmark
  def circe(): Array[Char] = decode[Array[Char]](new String(jsonBytes, UTF_8)).fold(throw _, identity)

  @Benchmark
  def jacksonScala(): Array[Char] = jacksonMapper.readValue[Array[Char]](jsonBytes)
/* FIXME: Jsoniter Java parsers chars as ints
  @Benchmark
  def jsoniterJava(): Array[Char] = JsoniterJavaParser.parse[Array[Char]](jsonBytes, classOf[Array[Char]])
*/
  @Benchmark
  def jsoniterScala(): Array[Char] = readFromArray[Array[Char]](jsonBytes)

  @Benchmark
  def playJson(): Array[Char] = Json.parse(jsonBytes).as[Array[Char]]

  @Benchmark
  def sprayJson(): Array[Char] = JsonParser(jsonBytes).convertTo[Array[Char]]

  @Benchmark
  def uPickle(): Array[Char] = read[Array[Char]](jsonBytes)

  @Benchmark
  def weePickle(): Array[Char] = FromJson(jsonBytes).transform(ToScala[Array[Char]])

  @Benchmark
  def sjson(): Array[Char] = {
    import sjsonnew.support.scalajson.unsafe._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.SJsonEncodersDecoders._
    Converter.fromJsonUnsafe[Array[Char]](Parser.parseFromByteArray(jsonBytes).get)
  }
}
