object Modules {
  val sdk = "library"
}

object Repos {
  val m2 = "https://plugins.gradle.org/m2/"
}

object Versions {
  val kotlin = "1.3.31"

  val okhttp = "3.10.0"
  val retorfit = "2.4.0"
  val gson = "2.8.5"

  val rxKotlin = "2.3.0"
  val rxJava = "2.2.6"
  val threeTen = "1.3.6"
  val slf4j = "1.7.25"
  val guava = "25.0-android" //jdk7 support
  val kluent = "1.41"
  val okio = "2.2.2"

  val bouncyProv = "1.59"

  val errorPronePlugin = "0.8"
  val errorProne = "2.3.3"
  val dokka = "0.9.16"
  val detekt = "1.0.0-RC14"
  val dockerCompose = "0.9.4"
}

object GradlePlugins {
  val errorProne = "net.ltgt.errorprone"
  val dokka = "org.jetbrains.dokka"
  val mavenPublish = "maven-publish"
  val detekt = "io.gitlab.arturbosch.detekt"
  val dockerCompose = "com.avast.gradle.docker-compose"
}

object Libs {
  val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
  val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"

  val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retorfit}"
  val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retorfit}"
  val retrofitRx = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retorfit}"
  val gson = "com.google.code.gson:gson:${Versions.gson}"
  val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
  val okhttpLogs = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

  val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
  val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
  val threeTen = "org.threeten:threetenbp:${Versions.threeTen}"
  val slf4jApi = "org.slf4j:slf4j-api:${Versions.slf4j}"
  val guava = "com.google.guava:guava:${Versions.guava}"
  val okio = "com.squareup.okio:okio:${Versions.okio}"

  val bouncyProv = "org.bouncycastle:bcprov-jdk15on:${Versions.bouncyProv}"

  val errorProne = "com.google.errorprone:error_prone_core:${Versions.errorProne}"
}

object TestLibs {
  val kotlinTest = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
  val kotlinJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"

  val slf4jSimple = "org.slf4j:slf4j-simple:${Versions.slf4j}"
  val kluent = "org.amshove.kluent:kluent:${Versions.kluent}"
  val mockServer = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"
}
