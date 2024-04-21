import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"

    id("org.jooq.jooq-codegen-gradle") version "3.19.3"
}

group = "rest.mjis"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

val postgresqlVersion = "42.7.2"
val r2dbcPostgresqlVersion = "1.0.4.RELEASE"
val minioVersion = "8.5.9"
val jooqVersion = "3.19.7"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springframework.hateoas:spring-hateoas")

    runtimeOnly("org.postgresql:postgresql:$postgresqlVersion")
    runtimeOnly("org.postgresql:r2dbc-postgresql:$r2dbcPostgresqlVersion")

    implementation("org.jooq:jooq:${jooqVersion}")
    implementation("org.jooq:jooq-kotlin:${jooqVersion}")
    jooqCodegen("jakarta.xml.bind:jakarta.xml.bind-api:4.0.2")
    jooqCodegen("org.jooq:jooq-meta-extensions:${jooqVersion}")
    jooqCodegen("org.jooq:jooq-meta-kotlin:${jooqVersion}")
    jooqCodegen("org.postgresql:postgresql:$postgresqlVersion")

    implementation("org.hashids:hashids:1.0.3")

    implementation("io.minio:minio:$minioVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jooq {
    configuration { }

    executions {
        create("main") {
            configuration {
                logging = org.jooq.meta.jaxb.Logging.DEBUG
                jdbc {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://127.0.0.1:35432/indarest"
                    user = "postgres"
                    password = "my-first-nest-pw"
                }
                generator {
                    name = "org.jooq.codegen.KotlinGenerator"
                    database {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "mjis"
                    }
                    generate {
                        this.isImplicitJoinPathsAsKotlinProperties = false
                    }
                    target {
                        packageName = "rest.mjis.indarest.infrastructure.database.jooq"
                        directory = "$projectDir/generated/jooq/src/main"
                    }
                    strategy {
                        name = "org.jooq.codegen.DefaultGeneratorStrategy"
                    }
                }
            }
        }
    }
}