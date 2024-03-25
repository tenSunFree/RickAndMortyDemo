@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id(libs.plugins.java.library.get().pluginId)
    id(libs.plugins.kotlin.jvm.get().pluginId)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(projects.core)
    implementation(projects.character.characterDomain)
    implementation(projects.character.characterDatasource)

    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutinesCore)
}