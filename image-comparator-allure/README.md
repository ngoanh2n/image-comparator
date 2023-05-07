[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/image-comparator-allure/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/image-comparator-allure)
[![javadoc](https://javadoc.io/badge2/com.github.ngoanh2n/image-comparator-allure/javadoc.svg)](https://javadoc.io/doc/com.github.ngoanh2n/image-comparator-allure)

# Image Comparator for Allure
Your project is using Allure as a report framework, `image-comparator-allure` should be used.

# Declarations
## Gradle
Add to `build.gradle`
```gradle
implementation("com.github.ngoanh2n:image-comparator-allure:1.0.0")
```

## Maven
Add to `pom.xml`
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>image-comparator-allure</artifactId>
    <version>1.0.0</version>
</dependency>
```

# System Properties
- `ngoanh2n.img.includeSource`: Indicate which attaches image sources to Allure report.
- `ngoanh2n.img.includeResult`: Indicate which attaches comparison result (diff image and deviation) to Allure report.