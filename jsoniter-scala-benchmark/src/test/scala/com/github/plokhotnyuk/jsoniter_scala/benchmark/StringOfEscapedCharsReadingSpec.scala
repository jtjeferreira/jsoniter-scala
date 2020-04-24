package com.github.plokhotnyuk.jsoniter_scala.benchmark

class StringOfEscapedCharsReadingSpec extends BenchmarkSpecBase {
  private val benchmark = new StringOfEscapedCharsReading {
    setup()
  }

  "StringOfEscapedCharsReading" should {
    "read properly" in {
      benchmark.avSystemGenCodec() shouldBe benchmark.obj
      benchmark.borerJson() shouldBe benchmark.obj
      benchmark.circe() shouldBe benchmark.obj
      benchmark.dslJsonScala() shouldBe benchmark.obj
      benchmark.jacksonScala() shouldBe benchmark.obj
      benchmark.jsoniterJava() shouldBe benchmark.obj
      benchmark.jsoniterScala() shouldBe benchmark.obj
      benchmark.playJson() shouldBe benchmark.obj
      benchmark.sprayJson() shouldBe benchmark.obj
      benchmark.uPickle() shouldBe benchmark.obj
      benchmark.weePickle() shouldBe benchmark.obj
      benchmark.sjson() shouldBe benchmark.obj
    }
  }
}
