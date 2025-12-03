group = "areteruhiro"

patches {
    about {
        name = "a"
        description = "Patches for ReVanced"
        source = "git@github.com:revanced/revanced-patches.git"
        author = "LIMEs"
        contact = "contact@revanced.app"
        website = "https://your.homepage"
        license = "GNU General Public License v3.0"
    }
}

dependencies {
    // Required due to smali, or build fails. Can be removed once smali is bumped.
    implementation(libs.guava)

    implementation(libs.apksig)

    // Android API stubs defined here.
    compileOnly(project(":patches:stub"))
}

tasks {
    register<JavaExec>("preprocessCrowdinStrings") {
        description = "Preprocess strings for Crowdin push"

        dependsOn(compileKotlin)

        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set("app.revanced.util.CrowdinPreprocessorKt")

        args = listOf(
            "src/main/resources/addresources/values/strings.xml",
            // Ideally this would use build/tmp/crowdin/strings.xml
            // But using that does not work with Crowdin pull because
            // it does not recognize the strings.xml file belongs to this project.
            "src/main/resources/addresources/values/strings.xml"
        )
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
}

publishing {
    publications {
        create<MavenPublication>("revanced-patches-publication") {
            from(components["java"])

            pom {
                name = "LIMEs"
                description = "Patches for ReVanced."
                url = "https://your.homepage"

                licenses {
                    license {
                        name = "GNU General Public License v3.0"
                        url = "https://www.gnu.org/licenses/gpl-3.0.en.html"
                    }
                }
                developers {
                    developer {
                        id = "Your ID"
                        name = "LIMEs"
                        email = "contact@your.homepage"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/areteruhiro/revanced-patches.git"
                    developerConnection = "scm:git:git@github.com:areteruhiro/revanced-patches.git"
                    url = "https://github.com/crimera/piko"
                }
            }
        }
    }
}
