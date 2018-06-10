import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlin_version: String by extra

buildscript {
  val kotlin_version: String by extra

  repositories {
    mavenCentral()
  }
  dependencies {
    classpath(kotlin("gradle-plugin", kotlin_version))
  }
}

plugins {
  `java-library`
}

group = "therealfarfetchd.extmath"

apply {
  from("publish.gradle")
  plugin("kotlin")
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib-jdk8", kotlin_version))

  testImplementation("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
  sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}