plugins {
  kotlin("jvm")
  id(GradlePlugins.errorProne)
  application
}

application {
  mainClassName = "ch.decent.sdk.poet.MainKt"
}

dependencies {
  implementation(Libs.kotlin)
  implementation(Libs.kotlinReflect)
  implementation(Libs.kpoet)
  implementation("com.github.cretz.kastree:kastree-ast-psi:0.4.0")
}
