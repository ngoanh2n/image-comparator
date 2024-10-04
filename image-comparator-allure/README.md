[![Java](https://img.shields.io/badge/Java-17-orange)](https://adoptium.net)
[![Maven](https://img.shields.io/maven-central/v/com.github.ngoanh2n/image-comparator-allure?label=Maven)](https://mvnrepository.com/artifact/com.github.ngoanh2n/image-comparator-allure)
[![GitHub Actions](https://img.shields.io/github/actions/workflow/status/ngoanh2n/image-comparator/test.yml?logo=github&label=GitHub%20Actions)](https://github.com/ngoanh2n/image-comparator/actions/workflows/test.yml)
[![CircleCI](https://img.shields.io/circleci/build/github/ngoanh2n/image-comparator?token=CCIPRJ_V9AVYTzVyEF9A9GMsVD9oF_2ce0fb3410ce42dfee9d8d854bae69d56f206df6&logo=circleci&label=CircleCI)
](https://dl.circleci.com/status-badge/redirect/gh/ngoanh2n/image-comparator/tree/master)

# Image Comparator for Allure
<!-- TOC -->
* [Declaration](#declaration)
    * [Gradle](#gradle)
    * [Maven](#maven)
* [Description](#description)
* [System Property](#system-property)
<!-- TOC -->
When using Allure as a report framework, `image-comparator-allure` should be used.

![](images/allure-report.png)

# Declaration
### Gradle
Add to `build.gradle`.
```gradle
implementation("com.github.ngoanh2n:image-comparator-allure:1.5.0")
```

### Maven
Add to `pom.xml`.
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>image-comparator-allure</artifactId>
    <version>1.5.0</version>
</dependency>
```

# Description
You can change comparison description on Allure by creating file `image-comparator-allure.properties` in folder `resources`.<br>
Default description as below.
```properties
subject=Image Comparison
expImage=Source: Expected Image
actImage=Source: Actual Image
resultImage=Result: Diff Image
resultDeviation=Result: Deviation
```

# System Property
| Name                          | Description                                                                            | Default |
|:------------------------------|:---------------------------------------------------------------------------------------|:--------|
| `ngoanh2n.img.includeSource`  | Indicate which attaches image sources to Allure report.                                | `true`  |
| `ngoanh2n.img.includeResult`  | Indicate which attaches comparison result (diff image and deviation) to Allure report. | `true`  |
