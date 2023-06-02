plugins {
    kotlin("jvm") version "1.8.20"
    application
    id("org.jetbrains.kotlinx.kover") version "0.5.1"
    idea
}

group = "com.techzealot"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    maven("https://repo1.maven.org/maven2/")
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
    // kotlin coroutine 确保与kotlin("jvm")使用相同版本的标准库 便于调试
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.0")
    //kotlin script
    implementation(kotlin("script-runtime"))
    //kotlin html dsl
    val kotlinxHtmlVersion = "0.7.5"
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinxHtmlVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-html:$kotlinxHtmlVersion")
    //===test====
    testImplementation(kotlin("test"))
    val kotestVersion = "5.3.1"
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    //mockk
    testImplementation("io.mockk:mockk:1.12.4")
    //Arrow
    implementation("io.arrow-kt:arrow-core:1.1.2")
    //guava
    implementation("com.google.guava:guava:31.1-jre")
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("MainKt")
}