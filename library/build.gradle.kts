import org.jetbrains.dokka.gradle.DokkaTask

plugins {
  kotlin("jvm")
  id(GradlePlugins.errorProne)
  id(GradlePlugins.dokka)
  id(GradlePlugins.mavenPublish)
  id(GradlePlugins.detekt)
  id(GradlePlugins.dockerCompose)
  jacoco
}

dependencies {
  implementation(Libs.kotlin)
  implementation(Libs.kotlinReflect)

  api(Libs.rxKotlin)
  api(Libs.rxJava)

  implementation(Libs.bouncyProv)
  implementation(Libs.okio)
  api(Libs.slf4jApi)
  api(Libs.threeTen)
  api(Libs.guava)

  implementation(Libs.retrofit)
  implementation(Libs.retrofitGson)
  implementation(Libs.retrofitRx)
  implementation(Libs.okhttpLogs)
  api(Libs.gson)
  api(Libs.okhttp)

  testImplementation(TestLibs.kotlinTest)
  testImplementation(TestLibs.kotlinJunit)
  testImplementation(TestLibs.slf4jSimple)
  testImplementation(TestLibs.kluent)
  testImplementation(TestLibs.mockServer)

  errorprone(Libs.errorProne)
  errorproneJavac(Libs.errorProneJavac)
}

detekt {
  toolVersion = Versions.detekt
  input = files("src/main/java")
  config = files("detekt-config.yml")
}

dockerCompose {
  isRequiredBy(project.tasks.test.get())
  useComposeFiles = listOf("../test-dcore-node/docker-compose.yml")
}

val dokka by tasks.getting(DokkaTask::class) {
  outputFormat = "javadoc"
  outputDirectory = "$buildDir/javadoc"
}

val dokkaJar by tasks.creating(Jar::class) {
  group = JavaBasePlugin.DOCUMENTATION_GROUP
  description = "Assembles Kotlin docs with Dokka"
  classifier = "javadoc"
  from(dokka)
}

val sourcesJar by tasks.creating(Jar::class) {
  group = JavaBasePlugin.DOCUMENTATION_GROUP
  description = "Assembles sources JAR"
  classifier = "sources"
  from(sourceSets["main"].allSource)
}

artifacts {
  add("archives", sourcesJar)
  add("archives", dokkaJar)
}

publishing {
  publications {
    register("mavenJava", MavenPublication::class) {
      from(components["java"])
      artifact(sourcesJar)
      artifact(dokkaJar)
    }
  }
}

tasks.jacocoTestReport {
  reports {
    xml.isEnabled = true
    csv.isEnabled = false
  }
}

tasks.test {
  exclude("**/api/", "**/Scratchpad.class")
  testLogging {
    events("passed", "skipped", "failed")
    setExceptionFormat("full")
  }
}
