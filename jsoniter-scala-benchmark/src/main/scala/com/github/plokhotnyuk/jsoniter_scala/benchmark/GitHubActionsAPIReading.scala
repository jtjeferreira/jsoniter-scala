package com.github.plokhotnyuk.jsoniter_scala.benchmark

import java.nio.charset.StandardCharsets.UTF_8

import com.github.plokhotnyuk.jsoniter_scala.benchmark.BorerJsonEncodersDecoders._
import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceEncodersDecoders._
import com.github.plokhotnyuk.jsoniter_scala.benchmark.GitHubActionsAPI._
import com.github.plokhotnyuk.jsoniter_scala.benchmark.JacksonSerDesers._
import com.github.plokhotnyuk.jsoniter_scala.benchmark.JsoniterScalaCodecs._
import com.github.plokhotnyuk.jsoniter_scala.benchmark.SprayFormats._
import com.github.plokhotnyuk.jsoniter_scala.benchmark.WeePickleFromTos._
import com.github.plokhotnyuk.jsoniter_scala.core._
import com.rallyhealth.weejson.v1.jackson.FromJson
import com.rallyhealth.weepickle.v1.WeePickle.ToScala
import io.circe.parser.decode
import org.openjdk.jmh.annotations.Benchmark
import spray.json.JsonParser

class GitHubActionsAPIReading extends GitHubActionsAPIBenchmark {
  @Benchmark
  def borerJson(): GitHubActionsAPI.Response =
    io.bullet.borer.Json.decode(jsonBytes).to[GitHubActionsAPI.Response].value

  @Benchmark
  def circe(): GitHubActionsAPI.Response =
    decode[GitHubActionsAPI.Response](new String(jsonBytes, UTF_8)).fold(throw _, identity)

  @Benchmark
  def jacksonScala(): GitHubActionsAPI.Response = jacksonMapper.readValue[GitHubActionsAPI.Response](jsonBytes)

  @Benchmark
  def jsoniterScala(): GitHubActionsAPI.Response = readFromArray[GitHubActionsAPI.Response](jsonBytes)

  @Benchmark
  def sprayJson(): GitHubActionsAPI.Response = JsonParser(jsonBytes).convertTo[GitHubActionsAPI.Response]

  @Benchmark
  def weePickle(): GitHubActionsAPI.Response = FromJson(jsonBytes).transform(ToScala[GitHubActionsAPI.Response])

  @Benchmark
  def sjson(): GitHubActionsAPI.Response = {
    import sjsonnew.support.scalajson.unsafe._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.SJsonEncodersDecoders._
    Converter.fromJsonUnsafe[GitHubActionsAPI.Response](Parser.parseFromByteArray(jsonBytes).get)
  }
}
