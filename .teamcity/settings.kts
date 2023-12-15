import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetBuild
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetTest
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2023.11"

project {

    vcsRoot(HttpsGithubComBurnashevaXunitDotnet6gitRefsHeadsMain)

    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    params {
        param("dotnet.version", "6")
    }

    vcs {
        root(HttpsGithubComBurnashevaXunitDotnet6gitRefsHeadsMain)
    }

    steps {
        dotnetBuild {
            id = "dotnet"
            projects = "XUnitCore.sln"
            sdk = "%dotnet.version%"
        }
        dotnetTest {
            id = "dotnet_1"
            projects = "PrimeService.Tests/PrimeService.Tests.csproj"
            sdk = "%dotnet.version%"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

object HttpsGithubComBurnashevaXunitDotnet6gitRefsHeadsMain : GitVcsRoot({
    name = "https://github.com/burnasheva/xunit_dotnet6.git#refs/heads/main"
    url = "https://github.com/burnasheva/xunit_dotnet6.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
})
