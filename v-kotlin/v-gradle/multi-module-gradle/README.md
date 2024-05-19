# multi-module-gradle

这是一个Gradle的`多模块`和`插件`以及`依赖统一管理`的示例，参考以下两篇官方教程。

- [多模块 & 插件](https://docs.gradle.org/current/userguide/partr1_gradle_init.html)
- [依赖统一管理](https://docs.gradle.org/current/userguide/platforms.html)

## 1. Gradle 插件

关于插件写了两个例子:

- greeting 打印一行文本
- 给每个kt文件加上license说明

以下是相关命令: 

```bash
# 验证插件
./gradlew build

# 执行插件 greeting
./gradlew :mmg-app:greeting

# 执行插件 license
./gradlew :mmg-app:license
```

插件源码位置在`gradle/license-plugin`下。初始化的方式是:

```bash
cd gradle
mkdir license-plugin && cd license-plugin
gradle init --dsl kotlin --type kotlin-gradle-plugin --project-name license
```

可能会缺少依赖，如:

```kts
dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.9.23")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.2")
}
```

## 2. Gradle 多模块

注意看项目根目录的`settings.gradle.kts`与各个子模块的`build.gradle.kts`文件。

`settings.gradle.kts`文件部分:

```kts
rootProject.name = "multi-module-gradle"
include("mmg-foo")
include("mmg-bar")
include("mmg-common")
include("mmg-app")
```

以最顶层的子模块`mmg-app`的`build.gradle.kts`文件为例子:

```kts
dependencies {
    implementation(project(":mmg-foo"))
    implementation(project(":mmg-bar"))
    // ...
}
```

## 2. Gradle 依赖统一管理

注意看项目根目录的`settings.gradle.kts`与各个子模块的`build.gradle.kts`文件。

`settings.gradle.kts`文件，设置依赖版本:

```kts
dependencyResolutionManagement {
    versionCatalogs {
        // test
        create("libs") {
            val test = version("test", "1.9.24")
            library("kotlin-test", "org.jetbrains.kotlin", "kotlin-test").versionRef(test)
        }
    }
}
```

以最顶层的子模块`mmg-app`的`build.gradle.kts`文件为例子:

```kts
dependencies {
  // ...
  testImplementation(libs.kotlin.test)
}
```
