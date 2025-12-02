plugins {
    id("java")
    id("application")
}

group = "dev.vk.jfc.m1"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set("dev.training.Concurrency")
    applicationDefaultJvmArgs = listOf("--enable-preview")
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileJava {
    options.compilerArgs.add("--enable-preview")
}
