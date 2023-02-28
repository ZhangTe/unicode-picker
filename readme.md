[English](readme.md)|[中文](readme_zh.md)
# Unicode Picker

This is a unicode character viewer & picker in java.
A alternative way to search in character map.
![sample1](/screenshot/sampleform1.png)
# Installation
Download the released `jar file` that fit for your screen and environment.
## Java Environment
Java is available in many different system platforms, including Windows, Linux, MacOs etc.<br/>
Install Oracle java runtime 1.7 or later(skip if you already have a Java Runtime Environment)
The program require javafx as ui-engine, javafx is not directly come with jdk after java 11.

### JRE 7 - 10
Download and Install jdk.<br/>
Run

```bash
java -jar {jar file path}\codechart.jar 
```

### JAVA 11 or later

Download and setup JRE(OpenJDK),
then Download and unzip JAVAFX module.<br/>

Setup the java path and run:

```bash
java -jar --module-path "{javafx module path}\lib" --add-modules javafx.controls,javafx.fxml {jar file path}\codechart.jar 
```

>Downloads:
>[JAVAFX SDK](https://gluonhq.com/products/javafx/)
>[华为云 JDK](https://mirrors.huaweicloud.com/java/jdk/)
>[OpenJDK 19](https://jdk.java.net/19/)

***

# Fonts

Some characters require additional fonts to be displayed.<br/>

This font may meet the needs:<br/>
> [TH-Tshyn](http://cheonhyeong.com/English/download.html)
>[TH-Tshyn Font full(Chinese)](http://cheonhyeong.com/Simplified/download.html)
Notice: Fonts should always be installed for **all-user** then can be loaded by this program.

# Unicode Relative Links

[Unicode 15.0 Character Code Charts](https://www.unicode.org/charts/)<br/>

[Download Full CodeCharts 15 (English, 2022-09-09) PDF 103M from Unicode.org](https://www.unicode.org/Public/15.0.0/charts/CodeCharts.pdf)