[English](readme.md)|[中文](readme_zh.md)
# Unicode字符表
这个Java小程序是unicode字符查看和输入器。

直观简化查找字符对应的unicode码时的工作。
![sample1](/screenshot/sampleform1.png)
# 安装

下载Release中的jar文件。

## Java 环境(JRE)
Java可以运行在很多种操作系统中，包括Windows, Linux, MacOS等等。<br/>
由于程序使用了JavaFX作为界面引擎，某些版本需要额外配置。

### JRE 7 - 10
安装JRE1.7到JAVA10版本(Java 8 即为 1.8 )，不需要另外安装其他模块即可执行。<br/>

指令：
```bash
java -jar {jar执行程序位置}\codechart.jar 
```

一般情况，直接在终端使用Java指令即可
Linux需要执行上述指令。
Windows下环境（JRE 7 ~ 10 ）配好双击即可打开。

### JAVA 11 以上

除了需要安装JRE（目前可用开源为OpenJDK）以外，还要下载javaFX模块（SDK）。<br/>
可以先配置好JAVA_HOME环境变量。<br/>

执行指令
```bash
java -jar --module-path "{javafx模块解压路径}\lib" --add-modules javafx.controls,javafx.fxml {jar执行程序位置}\codechart.jar 
```

>[JAVAFX SDK下载 平均约40M，解压后约90M](https://gluonhq.com/products/javafx/)
>[华为云 JDK 下载 平均约180M，解压后约300M](https://mirrors.huaweicloud.com/java/jdk/)
>[OpenJDK 19 下载 平均约180M。解压后约300M](https://jdk.java.net/19/)

***


# 字体

某些字符需要另外安装字体才能显示。<br/>
推荐使用[天珩全字库(TH-Tshyn)](http://cheonhyeong.com/Simplified/download.html)

注意：安装字体时务必对**所有用户**安装才能在程序中正确显示。

# Unicode 相关链接

[Unicode 15.0 码表（英语）](https://www.unicode.org/charts/)<br/>

[从Unicode 官方网站下载完整 Unicode 15 码表（英语，2022-09-09） PDF 103M](https://www.unicode.org/Public/15.0.0/charts/CodeCharts.pdf)