[![GitHub forks](https://img.shields.io/github/forks/ngoanh2n/image-comparator.svg?style=social&label=Fork&maxAge=2592000)](https://github.com/ngoanh2n/image-comparator/network/members/)
[![GitHub stars](https://img.shields.io/github/stars/ngoanh2n/image-comparator.svg?style=social&label=Star&maxAge=2592000)](https://github.com/ngoanh2n/image-comparator/stargazers/)
[![GitHub watchers](https://img.shields.io/github/watchers/ngoanh2n/image-comparator.svg?style=social&label=Watch&maxAge=2592000)](https://github.com/ngoanh2n/image-comparator/watchers/)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/image-comparator/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/image-comparator)
[![javadoc](https://javadoc.io/badge2/com.github.ngoanh2n/image-comparator/javadoc.svg)](https://javadoc.io/doc/com.github.ngoanh2n/image-comparator)
[![GitHub release](https://img.shields.io/github/release/ngoanh2n/image-comparator.svg)](https://github.com/ngoanh2n/image-comparator/releases/)
[![badge-jdk](https://img.shields.io/badge/jdk-8-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-blueviolet.svg)](https://opensource.org/licenses/MIT)

# Image Comparator
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
implementation("com.github.ngoanh2n:image-comparator:1.0.1")
```

## Maven
Add dependency to `pom.xml`.
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>image-comparator</artifactId>
    <version>1.0.1</version>
</dependency>
```

# Usage
## Comparison
```java
ImageComparisonOptions options = ImageComparisonOptions
        .builder()
        .setAllowedDeviation(0.05)      // Default to 0.0
        .setDiffColor(Color.CYAN)       // Default to Color.RED
        .build();
ImageComparisonResult result = ImageComparator.compare(expectedImage, actualImage, options);
```

## Result
`ImageComparisonResult` is the result of `ImageComparator`.
```java
boolean isDifferent = ImageComparisonResult.isDifferent();
int diffSize = ImageComparisonResult.getDiffSize();
BufferedImage diffImage = ImageComparisonResult.getDiffImage();
double allowedDeviation = ImageComparisonResult.getAllowedDeviation();
double currentDeviation = ImageComparisonResult.getCurrentDeviation();
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
         //.setLocation(Paths.get("build/custom"))  // Default to build/ngoanh2n/img
         .build();
  ImageComparisonOptions options = ImageComparisonOptions
         .builder()
         .setResultOptions(resultOptions)           // Default to ImageComparisonResultOptions.defaults()
         .build();
  ```

## Allure Report
When using Allure as a report framework, should use
<a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/image-comparator-allure">com.github.ngoanh2n:image-comparator-allure</a>.<br>
`image-comparator-allure` [README](image-comparator-allure#readme).
