pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url=uri("https://naver.jfrog.io/artifactory/maven/")
        }
        jcenter()
    }
}

rootProject.name = "My Application"
include(":app")
include(":lib")
include(":lib2")
include(":lib3")
