[![Java](https://img.shields.io/badge/Java-17-orange)](https://adoptium.net)
[![Maven](https://img.shields.io/maven-central/v/com.github.ngoanh2n/image-comparator?label=Maven)](https://mvnrepository.com/artifact/com.github.ngoanh2n/image-comparator)
[![GitHub Actions](https://img.shields.io/github/actions/workflow/status/ngoanh2n/image-comparator/test.yml?logo=github&label=GitHub%20Actions)](https://github.com/ngoanh2n/image-comparator/actions/workflows/test.yml)
[![CircleCI](https://img.shields.io/circleci/build/github/ngoanh2n/image-comparator?token=CCIPRJ_V9AVYTzVyEF9A9GMsVD9oF_2ce0fb3410ce42dfee9d8d854bae69d56f206df6&logo=circleci&label=CircleCI)
](https://dl.circleci.com/status-badge/redirect/gh/ngoanh2n/image-comparator/tree/master)

**Table of Contents**
<!-- TOC -->
* [Declaration](#declaration)
  * [Gradle](#gradle)
  * [Maven](#maven)
* [Usage](#usage)
  * [Comparison](#comparison)
  * [Result](#result)
  * [Visitor](#visitor)
  * [Output](#output)
  * [Allure Report](#allure-report)
<!-- TOC -->

# Declaration
## Gradle
Add dependency to `build.gradle`.
```gradle
implementation("com.github.ngoanh2n:image-comparator:1.5.0")
```

## Maven
Add dependency to `pom.xml`.
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>image-comparator</artifactId>
    <version>1.5.0</version>
</dependency>
```

# Usage
## Comparison
1. Compare 2 images
    ```java
    ImageComparisonOptions options = ImageComparisonOptions
            .builder()
            .allowedDeviation(0.05)         // Default to 0.0
            .diffColor(Color.CYAN)          // Default to Color.RED
            .build();
    ImageComparisonResult result = ImageComparator.compare(expectedImage, actualImage, options);
    ```
2. Compare 2 image directories
    ```java
    Path expectedImageDir = Paths.get("data/expected");
    Path actualImageDir = Paths.get("data/actual");
            
    ImageComparisonOptions options = ImageComparisonOptions
            .builder()
            .allowedDeviation(0.05)         // Default to 0.0
            .diffColor(Color.CYAN)          // Default to Color.RED
            .build();
    ImageBulkComparisonResult result = ImageComparator.compare(expectedImageDir, actualImageDir, options);
    ```

## Result
`ImageComparisonResult` is the result of `ImageComparator.compare(expectedImage, actualImage, options)`.
```java
boolean isDifferent = ImageComparisonResult.isDifferent();
int diffSize = ImageComparisonResult.getDiffSize();
BufferedImage diffImage = ImageComparisonResult.getDiffImage();
double allowedDeviation = ImageComparisonResult.getAllowedDeviation();
double currentDeviation = ImageComparisonResult.getCurrentDeviation();
```

`ImageBulkComparisonResult` is the result of `ImageComparator.compare(expectedImageDir, actualImageDir, options)`.
```java
boolean hasDiff = ImageBulkComparisonResult.hasDiff();
int diffTotal = ImageBulkComparisonResult.getDiffTotal();
List<ImageComparisonResult> diffResults = ImageBulkComparisonResult.getDiffResults();
```

## Visitor
`ImageComparisonVisitor` for walking through `ImageComparator`.
- `ImageComparisonVisitor.comparisonStarted(ImageComparisonOptions, BufferedImage, BufferedImage)`
- `ImageComparisonVisitor.comparisonFinished(ImageComparisonOptions, BufferedImage, BufferedImage, ImageComparisonResult)`

## Output
`ImageComparisonOutput` for writing comparison output files to specified location.<br>
An implementation of `ImageComparisonVisitor`.
- The output is always created at `build/ngoanh2n/img/{yyyyMMdd.HHmmss.SSS}` by default
- Use `ImageComparisonResultOptions` to adjust the output behaviors. And set to `ImageComparisonOptions`
  ```java
  ImageComparisonResultOptions resultOptions = ImageComparisonResultOptions
         .builder()
         .writeOutputs(false)                       // Default to true
         //.location(Paths.get("build/custom"))     // Default to build/ngoanh2n/img
         .build();
  ImageComparisonOptions options = ImageComparisonOptions
         .builder()
         .resultOptions(resultOptions)              // Default to ImageComparisonResultOptions.defaults()
         .build();
  ```

## Allure Report
When using Allure as a report framework, should use
<a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/image-comparator-allure">com.github.ngoanh2n:image-comparator-allure</a>.<br>
`image-comparator-allure` [README](image-comparator-allure#readme).
