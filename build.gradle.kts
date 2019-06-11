buildscript {
  repositories {
    mavenCentral()
    jcenter()
    maven(url = Repos.m2)
  }
}

plugins {
  base
  kotlin("jvm") version Versions.kotlin apply false
  id(GradlePlugins.errorProne) version Versions.errorPronePlugin apply false
  id(GradlePlugins.dokka) version Versions.dokka apply false
  id(GradlePlugins.detekt) version Versions.detekt apply false
  id(GradlePlugins.dockerCompose) version Versions.dockerCompose apply false
}

allprojects {
  repositories {
    mavenCentral()
    jcenter()
  }
}
