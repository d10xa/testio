# testio

[![Build Status](https://travis-ci.org/d10xa/testio.svg?branch=master)](https://travis-ci.org/d10xa/testio)
[ ![Download](https://api.bintray.com/packages/d10xa/maven/ru.d10xa%3Atestio/images/download.svg) ](https://bintray.com/d10xa/maven/ru.d10xa%3Atestio/_latestVersion)
[![Coveralls](https://img.shields.io/coveralls/d10xa/testio.svg)](https://coveralls.io/github/d10xa/testio?branch=master)

Testio is a simple tool for testing your System.out.println("Hello world") and mocking System.in.
It is directed to use in groovy unit tests.

## Installation and Getting Started

build.gradle

```gradle
repositories {
    jcenter()
    maven {
        url 'https://dl.bintray.com/d10xa/maven'
    }
}

dependencies {
    testCompile "junit:junit:4.12"
    testCompile "org.spockframework:spock-core:1.0-groovy-2.4"
    testCompile "ru.d10xa:testio:0.3.0"
}
```

src/main/java/example/Fibonacci.java

```java
package example;

import java.util.Scanner;

public class Fibonacci {

    public static void main(String[] args) {
        int n = new Scanner(System.in).nextInt();
        System.out.println(fib(n));
    }

    public static long fib(long n) {
        return n <= 2 ? 1 : fib(n - 1) + fib(n - 2);
    }

}
```

src/test/groovy/example/FibonacciSpec.java

```groovy
package example

import org.junit.Rule
import spock.lang.Specification

class FibonacciSpec extends Specification {

    @Rule
    Testio io = new Testio()

    def 'check fibonacci numbers'() {
        given:
        io << input

        when:
        Fibonacci.main(null)

        then:
        io.toString() as Integer == output

        where:
        input || output
        '1'   || 1
        '2'   || 1
        '3'   || 2
        '4'   || 3
        '5'   || 5
    }

}

```
