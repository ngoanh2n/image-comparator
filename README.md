[![GitHub forks](https://img.shields.io/github/forks/ngoanh2n/image-comparator.svg?style=social&label=Fork&maxAge=2592000)](https://github.com/ngoanh2n/image-comparator/network/members/)
[![GitHub stars](https://img.shields.io/github/stars/ngoanh2n/image-comparator.svg?style=social&label=Star&maxAge=2592000)](https://github.com/ngoanh2n/image-comparator/stargazers/)
[![GitHub watchers](https://img.shields.io/github/watchers/ngoanh2n/image-comparator.svg?style=social&label=Watch&maxAge=2592000)](https://github.com/ngoanh2n/image-comparator/watchers/)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/image-comparator/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/image-comparator)
[![javadoc](https://javadoc.io/badge2/com.github.ngoanh2n/image-comparator/javadoc.svg)](https://javadoc.io/doc/com.github.ngoanh2n/image-comparator)
[![GitHub release](https://img.shields.io/github/release/ngoanh2n/image-comparator.svg)](https://github.com/ngoanh2n/image-comparator/releases/)
[![badge-jdk](https://img.shields.io/badge/jdk-8-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-blueviolet.svg)](https://opensource.org/licenses/MIT)

# Image Comparator

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [Declarations](#declarations)
  - [Gradle](#gradle)
  - [Maven](#maven)
- [Usages](#usages)
  - [Comparison](#comparison)
  - [Comparison Result](#comparison-result)
  - [Allure Report](#allure-report)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Declarations
## Gradle
_Add dependency to `build.gradle`_
```gradle
implementation("com.github.ngoanh2n:image-comparator:1.0.0")
```

## Maven
_Add dependency to `pom.xml`_
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>image-comparator</artifactId>
    <version>1.0.0</version>
</dependency>
```

# Usages
## Comparison
```java
// Build comparison options to navigate behaviors of comparison process
ImageComparisonOptions options = ImageComparisonOptions.builder()
        //.setDeviation(0.015) // 0.015 = 1.5%
        .setDisregardColor(Color.GRAY)
        .setDifferenceColor(Color.RED)
        .build();

// Do comparison
ImageComparisonResult result = ImageComparator.compare(expectedImage, actualImage, options);
```

## Comparison Result
```java
ImageComparisonResult.isDifferent()
ImageComparisonResult.getDiffSize()
ImageComparisonResult.getDifImage()
ImageComparisonResult.getAllowedDeviation()
ImageComparisonResult.getCurrentDeviation()
```

_By default, the diff image which is created after comparison is saved as name `build/ngoanh2n/img/{yyyyMMdd.HHmmss.SSS}.png`_

## Allure Report
Your project is using Allure as a report framework, `image-comparator-allure` should be used. ([README](image-comparator-allure#readme))
