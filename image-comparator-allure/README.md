[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/image-comparator-allure/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/image-comparator-allure)
[![javadoc](https://javadoc.io/badge2/com.github.ngoanh2n/image-comparator-allure/javadoc.svg)](https://javadoc.io/doc/com.github.ngoanh2n/image-comparator-allure)
[![badge-jdk](https://img.shields.io/badge/jdk-8-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-blueviolet.svg)](https://opensource.org/licenses/MIT)

# Image Comparator for Allure
When using Allure as a report framework, `image-comparator-allure` should be used.

![](images/allure-report.png)

# Declaration
### Gradle
Add to `build.gradle`.
```gradle
implementation("com.github.ngoanh2n:image-comparator-allure:1.1.0")
```

### Maven
Add to `pom.xml`.
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>image-comparator-allure</artifactId>
    <version>1.1.0</version>
</dependency>
```

# System Property
| Name                          | Description                                                                            | Default |
|:------------------------------|:---------------------------------------------------------------------------------------|:--------|
| `ngoanh2n.img.includeSource`  | Indicate which attaches image sources to Allure report.                                | `true`  |
| `ngoanh2n.img.includeResult`  | Indicate which attaches comparison result (diff image and deviation) to Allure report. | `true`  |
