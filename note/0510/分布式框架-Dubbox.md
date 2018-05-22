# 分布式框架-Dubbox

## 课程目标

- 目标 1：了解电商行业特点以及理解电商的模式
- 目标 2：了解整体品优购的架构特点
- 目标 3：能够运用 Dubbox+SSM 搭建分布式应用
- 目标 4：搭建工程框架，完成品牌列表后端代码

## 主要电商模式

### B2B--企业对企业

**B2B （ Business to Business）**是指进行电子商务交易的供需双方都是商家（或企业、公司），她（他）们使用了互联网的技术或各种商务网络平台，完成商务交易的过程。电子商务是现代 B2B marketing 的一种具体主要的表现形式。

###  C2C--个人对个人

**C2C 即 Customer（Consumer） to Customer（Consumer）**，意思就是消费者个人间的电子商务行为。比如一个消费者有一台电脑，通过网络进行交易，把它出售给另外一个消费者，此种交易类型就称为 C2C 电子商务

###  B2C-- 企业对 个人

**B2C 是 Business-to-Customer 的缩写**，而其中文简称为“商对客”。“商对客”是电子商务的一种模式，也就是通常说的直接面向消费者销售产品和服务商业零售模式。这种形式的电子商务一般以网络零售业为主，主要借助于互联网开展在线销售活动。B2C 即企业通过互联网为消费者提供一个新型的购物环境——网上商店，消费者通过网络在网上购物、网上支付等消费行为。

### C2B--个人对企业

**C2B（Consumer to Business，即消费者到企业）**，是互联网经济时代新的商业模式。这一模式改变了原有生产者（企业和机构）和消费者的关系，是一种消费者贡献价值（CreateValue）， 企业和机构消费价值（Consume Value）。
C2B 模式和我们熟知的供需模式（DSM, Demand Supply Model）恰恰相反，真正的C2B 应该先有消费者需求产生而后有企业生产，即先有消费者提出需求，后有生产企业按需求组织生产。通常情况为消费者根据自身需求定制产品和价格，或主动参与产品设计、生产和定价，产品、价格等彰显消费者的个性化需求，生产企业进行定制化生产。

### O2O--线上到线下

**O2O 即 Online To Offline（在线离线/线上到线下）**，是指将线下的商务机会与互联网结合，让互联网成为线下交易的平台，这个概念最早来源于美国。O2O 的概念非常广泛，既可涉及到线上，又可涉及到线下,可以通称为 O2O。主流商业管理课程均对 O2O 这种新型的商业模式有所介绍及关注。

### F2C--工厂到个人

**F2C 指的是 Factory to customer**，即从厂商到消费者的电子商务模式。

### B2B2C - 企业- 企业- 个人

**B2B2C 是一种电子商务类型的网络购物商业模式，B是 BUSINESS的简称，C 是 CUSTOMER的简称，第一个 指的是商品或服务的供应商，第二个 B 指的是从事电子商务的企业，C 则是表示消费者。**
第一个 BUSINESS，并不仅仅局限于品牌供应商、影视制作公司和图书出版商，任何的商品供应商或服务供应商都能可以成为第一个 BUSINESS；第二 B 是 B2B2C 模式的电子商务业，通过统一的经营管理对商品和服务、消费者终端同时进行整合，是广大供应商和消费者之间的桥梁，为供应商和消费者提供优质的服务，是互联网电子商务服务供应商。C 表示消费者，在第二个 B 构建的统一电子商务平台购物的消费者；B2B2C 的来源于目前的 B2B、B2C 模式的演变和完善，把 B2C 和 C2C 完美地结合起来，通过 B2B2C 模式的电子商务企业构建自己的物流供应链系统，提供统一的服务。



## 品优购-  需求分析与系统设计

### 品优购简介

品优购网上商城是一个综合性的 B2B2C 平台，类似京东商城、天猫商城。网站采用商家入驻的模式，商家入驻平台提交申请，有平台进行资质审核，审核通过后，商家拥有独立的管理后台录入商品信息。商品经过平台审核后即可发布。

品优购网上商城主要分为网站前台、运营商后台、商家管理后台三个子系统。

#### 网站前台

主要包括网站首页、商家首页、商品详细页、、搜索页、会员中心、订单与支付相关页面、秒杀频道等



![52690213805](H:\itheima大数据项目班\项目班\0510\image\1526902138058.png)

#### 运营商后台

是运营商的运营人员的管理后台。 主要包括商家审核、品牌管理、规格管理、模板管理、商品分类管理、商品审核、广告类型管理、广告管理、订单查询、商家结算等。

![52690217194](H:\itheima大数据项目班\项目班\0510\image\1526902171944.png)

#### 商家管理后台

入驻的商家进行管理的后台，主要功能是对商品的管理以及订单查询统计、资金结算等功能。

![52690220779](H:\itheima大数据项目班\项目班\0510\image\1526902207798.png)



###  系统架构

####  SOA  架构

SOA 是 Service-Oriented Architecture 的首字母简称，它是一种支持面向服务的架构样式。从服务、基于服务开发和服务的结果来看，面向服务是一种思考方式。其实 SOA 架构更多应用于互联网项目开发。

为什么互联网项目会采用 SOA 架构呢？随着互联网的发展，网站应用的规模不断扩大，常规的垂直应用架构已无法应对，分布式服务架构以及流动计算架构势在必行，迫切需一个治理系统确保架构有条不紊的演进。

#### 品优购架构分析

![52690240851](H:\itheima大数据项目班\项目班\0510\image\1526902408515.png)



### 数据库表结构

| 表名称                  | 含义                         |
| ----------------------- | ---------------------------- |
| tb_brand                | 品牌                         |
| tb_specification        | 规格                         |
| tb_specification_option | 规格选项                     |
| tb_type_temple          | 类型模版：用于关联品牌和规格 |
| tb_item_cat             | 商品分类                     |
| tb_seller               | 商家                         |
| tb_goods                | 商品                         |
| tb_goods_desc           | 商品详情                     |
| tb_item                 | 商品明细                     |
| tb_content              | 商品广告                     |
| tb_content_category     | 商品广告分类                 |
| tb_user                 | 用户                         |
| tb_order                | 订单                         |
| tb_order_item           | 订单明细                     |
| tb_pay_log              | 支付日志                     |

###   框架组合

品优购采用当前流行的前后端编程架构。
后端框架采用 Spring +SpringMVC+mybatis +Dubbox 。前端采用 angularJS + Bootstrap。

## Dubbox  框架

### Dubbox  简介

Dubbox 是一个分布式服务框架，其前身是阿里巴巴开源项目 Dubbo ，被国内电商及互联网项目中使用，后期阿里巴巴停止了该项目的维护，当当网便在 Dubbo 基础上进行优化，并继续维护，为了与原有的 Dubbo 分，故将其命名为 Dubbox。多说一句，现在阿里巴巴一已经恢复了对Dubbo的支持，可以在apache下载，有中文文档支持。

Dubbox 致力于提供高性能和透明化的 RPC 远程服务调用方案，以及 SOA 服务治理方案。简单的说，dubbox 就是个服务框架，如果没有分布式的需求，其实是不需要用的，只有在分布式的时候，才有 dubbox 这样的分布式服务框架的需求，并且本质上是个服务调用的东东，说白了就是个远程服务调用的分布式框架



![52690309475](H:\itheima大数据项目班\项目班\0510\image\1526903094754.png)





- 节点角色说明：
  - Provider: 暴露服务的服务提供方。
  - Consumer: 调用远程服务的服务消费方。
  - Registry: 服务注册与发现的注册中心。
  - Monitor: 统计服务的调用次调和调用时间的监控中心。
  - Container: 服务运行容器。



- 调用关系说明：

  0. 服务容器负责启动，加载，运行服务提供者。

  1. 服务提供者在启动时，向注册中心注册自己提供的服务。

  2. 服务消费者在启动时，向注册中心订阅自己所需的服务。

  3. 注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推
     送变更数据给消费者

  4. 服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，
     如果调用失败，再选另一台调用

  5. 服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计
     数据到监控中心

     ​



### 注册中心 Zookeeper

####  Zookeeper 介绍

官方推荐使用 zookeeper 注册中心。注册中心负责服务地址的注册与查找，相当于目录服务，服务提供者和消费者只在启动时与注册中心交互，注册中心不转发请求，压力较小。

Zookeeper 是 Apacahe Hadoop 的子项目，是一个树型的目录服务，支持变更推送，适合作为 Dubbox 服务的注册中心，工业强度较高，可用于生产环境。

#### Zookeeper 在 在 Linux 系统的安装



##### 安装步骤：

- 安装 jdk 1.8
- 把 zookeeper 的压缩包（资源\配套软件\dubbox\zookeeper-3.4.6.tar.gz）上传到 linux

系统。 Alt+P 进入 SFTP ，输入 put d:\zookeeper-3.4.6.tar.gz 上传

- 解压缩压缩包

```shell
tar -zxvf zookeeper-3.4.6.tar.gz
```

- 进入 zookeeper-3.4.6 目录，创建 data 文件夹

```shell
mkdir data
```

- 进入 conf 目录 ，把 zoo_sample.cfg 改名为 zoo.cfg

```shell
cd conf
mv zoo_sample.cfg zoo.cfg
```

- 打开 zoo.cfg , 修改 data 属性：dataDir=/root/zookeeper-3.4.6/data

##### Zookeeper  服务启动

- 进入 bin 目录，启动服务输入命令

```shell
./zkServer.sh start
```

- 查看状态

```shell
./zkServer.sh status
```

###  Dubbox  本地 JAR  包部署与安装

Dubbox 的 jar 包并没有部署到 Maven 的中央仓库中，大家在 Maven 的中央仓库中可以查找到 Dubbo 的最终版本是 2.5.3 , 阿里巴巴解散了 Dubbo 团队后由当当网继续维护此项目，并改名为 Dubbox ,坐标不变，版本变更了，但是并没有提交到中央仓库。我们现在需要手动将 Dubbox 的 jar 包安装到我的本地仓库中。先将 dubbo-2.8.4.jar 包放到 d:\setup, 然后输入命令(当然 可以使用已经更新了的Dubbo)

```shell
mvn install:install-file -Dfile=d:\setup\dubbo-2.8.4.jar -DgroupId=com.alibaba -DartifactId=dubbo -Dversion=2.8.4 -Dpackaging=jar
```

### 管理中心的部署

我们在开发时，需要知道注册中心都注册了哪些服务，以便我们开发和测试。我们可以通过部署一个管理中心来实现。其实管理中心就是一个 web 应用，部署到 tomcat 即可。



#### 管理端安装

（1）编译源码，得到 war 包

可以在github上下载到 dubbox-master.zip，这个是 dubbox 的源码，我们可以使用 maven
命令编译源码得到“管理端”的 war 包

将此压缩包解压，在命令符下进入 dubbo-admin 目录 ，输入 maven 命令

```shell
mvn package -Dmaven.skip.test=true
```

显示 BUILD SUCCESS 就算是成功了

 （2）进入 target 文件夹，你会看到一个 dubbo-admin-2.8.4.war ， 在 linux 服务器上安tomcat, 将此 war 包上传到 linux 服务器的 tomcat 的 webapps 下。为了访问方便，你可以把版本号去掉。 启动 tomcat 后自动解压。

（3）如果你部署在 zookeeper 同一台主机并且端口是默认的 2181，则无需修改任何配置。如果不是在一台主机上或端口被修改，需要修改 WEB-INF 下的 dubbo.properties ，修改如下配置：

```shell
dubbo.registry.address=zookeeper://127.0.0.1:2181
```

#### 管理端使用

（1）打开浏览器，输入 http://192.168.25.132:8080/dubbo-admin/ ,登录用户名和密码均为root 进入首页。 (192.168.25.132:)是我部署的 linux 主机地址。

![52690430484](H:\itheima大数据项目班\项目班\0510\image\1526904304848.png)





（2）启动服务提供者工程，即可在服务治理-提供者查看到该服务

![52690432811](H:\itheima大数据项目班\项目班\0510\image\1526904328118.png)

点击其中一条数据后可以查看详情。



![52690435451](H:\itheima大数据项目班\项目班\0510\image\1526904354512.png)



（3）启动服务消费者工程，运行页面，观察“消费者”列表

![52690437836](H:\itheima大数据项目班\项目班\0510\image\1526904378367.png)



## 品优购-框架搭建

### 工程结构分析与设计

最终完整的工程结构如下：

![52690459434](H:\itheima大数据项目班\项目班\0510\image\1526904594341.png)



工程说明：

| 项目名                    | 作用           |
| ------------------------- | -------------- |
| pinyougou-parent          | 聚合工程       |
| pinyougou-pojo            | 通用实体类层   |
| pinyougou-dao             | 通用数据访问层 |
| pinyougou-xxxxx-interface | 某服务层接口   |
| pinyougou-xxxxx-service   | 某服务层实现   |
| pinyougou-xxxxx-web       | 某 web 工程    |

### 创建数据库表

执行资源文件夹中 pinyougou-db.sql

### 搭建框架

#### 父工程

创建 Maven 工程 pinyougou-parent （POM） ，groupId 为 com.pinyougou ,artifactId 为pinyougou-parent , 在 pom.xml 中 添 加 锁 定 版 本 信 息 dependencyManagement 与pluginManagement，详见“pom.xml”。

#### 通用实体类模块

创建通用实体类模块-pinyougou-pojo

#### 通用数据访问模块

创建通用数据访问模块 pinyougou-dao .添加依赖 Mybatis 和 pinyougou-pojo

```xml
<dependencies>
<!-- Mybatis -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
    </dependency>
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis-spring</artifactId> 
    </dependency>
    <dependency>
        <groupId>com.github.miemiedev</groupId>
        <artifactId>mybatis-paginator</artifactId>
    </dependency> 
    <!-- MySql -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    <!-- 连接池 -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
    </dependency> 
    <dependency>
    <groupId>com.pinyougou</groupId>
        <artifactId>pinyougou-pojo</artifactId>
    	<version>0.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
```

#### 通用工具类模块

创建通用工具类模块 pinyougou-common

#### 商家商品服务接口模块

创建 maven（jar）模块 pinyougou-sellergoods-interface , pom.xml 添加依赖

```xml
<dependencies>
    <dependency>
        <groupId>com.pinyougou</groupId>
        <artifactId>pinyougou-pojo</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
```

#### 商家商品服务模块

创建 maven（war）模块 pinyougou-sellergoods-service ，pom.xml 引入依赖

```xml
<dependencies>
    <!-- Spring -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId> 
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-beans</artifactId>
     </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-aspects</artifactId> 
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jms</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
    	<artifactId>spring-context-support</artifactId> 
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-test</artifactId>
    </dependency>
    <!-- dubbo 相关 -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>dubbo</artifactId> 
    </dependency>
    <dependency>
        <groupId>org.apache.zookeeper</groupId>
        <artifactId>zookeeper</artifactId> 
    </dependency>
    <dependency>
        <groupId>com.github.sgroschupf</groupId>
        <artifactId>zkclient</artifactId>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
     </dependency>
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
    </dependency>
    <dependency>
        <groupId>javassist</groupId>
        <artifactId>javassist</artifactId> 
    </dependency>
    <dependency>
        <groupId>commons-codec</groupId>
        <artifactId>commons-codec</artifactId>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>servlet-api</artifactId>
    <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>com.pinyougou</groupId>
        <artifactId>pinyougou-common</artifactId>
    	<version>0.0.1-SNAPSHOT</version>
    </dependency>
     <dependency>
        <groupId>com.pinyougou</groupId>
        <artifactId>pinyougou-dao</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>com.pinyougou</groupId>
        <artifactId>pinyougou-sellergoods-interface</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>

<build>
    <plugins>
    <!-- 配置 Tomcat 插件 -->
        <plugin>
            <groupId>org.apache.tomcat.maven</groupId>
            <artifactId>tomcat7-maven-plugin</artifactId>
            <configuration>
            <path>/</path>
            <port>9001</port>
            </configuration>
        </plugin>
    </plugins>
 </build>

```

在 webapps 下创建 WEB-INF/web.xml ，加载 spring 容器

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns="http://java.sun.com/xml/ns/javaee"
xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
version="2.5"> 
<!-- 加载 spring 容器 -->
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath*:spring/applicationContext*.xml</param-value>
</context-param>
<listener>
	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
</web-app>
```

创建包 com.pinyougou.sellergoods.service.impl

在 src/main/resources 下创建 spring/applicationContext-service.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:p="http://www.springframework.org/schema/p"
xmlns:context="http://www.springframework.org/schema/context"
xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
xmlns:mvc="http://www.springframework.org/schema/mvc"
xsi:schemaLocation="http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans.xsd
http://www.springframework.org/schema/mvc
http://www.springframework.org/schema/mvc/spring-mvc.xsd
http://code.alibabatech.com/schema/dubbo
http://code.alibabatech.com/schema/dubbo/dubbo.xsd
http://www.springframework.org/schema/context
http://www.springframework.org/schema/context/spring-context.xsd">
    
    <dubbo:protocol name="dubbo" port="20881"></dubbo:protocol>
    <dubbo:application name="pinyougou-sellergoods-service"/>
    <dubbo:registry address="zookeeper://192.168.25.129:2181"/>
    <dubbo:annotation package="com.pinyougou.sellergoods.service.impl" />

</beans>    
```



#### 运营商管理后台

创建 maven（war）模块 pinyougou-manager-web ， pom.xml 引入依赖

```xml
<dependencies>
<!-- Spring -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId> 
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-beans</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-aspects</artifactId> 
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jms</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context-support</artifactId> 
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-test</artifactId>
    </dependency>
    <!-- dubbo 相关 -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>dubbo</artifactId> 
    </dependency>
    <dependency>
        <groupId>org.apache.zookeeper</groupId>
        <artifactId>zookeeper</artifactId> 
    </dependency>
    <dependency>
        <groupId>com.github.sgroschupf</groupId>
        <artifactId>zkclient</artifactId>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
    </dependency>
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
    </dependency>
    <dependency>
        <groupId>javassist</groupId>
        <artifactId>javassist</artifactId> 
    </dependency>
    <dependency>
        <groupId>commons-codec</groupId>
        <artifactId>commons-codec</artifactId>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>servlet-api</artifactId>
    	<scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>com.pinyougou</groupId>
        <artifactId>pinyougou-common</artifactId>
    	<version>0.0.1-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>com.pinyougou</groupId>
        <artifactId>pinyougou-sellergoods-interface</artifactId>
    	<version>0.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
<build>
	<plugins>
		<!-- 配置 Tomcat 插件 -->
        <plugin>
            <groupId>org.apache.tomcat.maven</groupId>
            <artifactId>tomcat7-maven-plugin</artifactId>
            <configuration>
            <path>/</path>
            <port>9101</port>
            </configuration>
        </plugin>
    </plugins>
</build>
```

在 webapps 下创建 WEB-INF/web.xml ，加载 spring 容器

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns="http://java.sun.com/xml/ns/javaee"
xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
                    http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
version="2.5"> 
<!-- 解决 post 乱码 -->
<filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-cl
ass>
<init-param>
    <param-name>encoding</param-name>
    <param-value>utf-8</param-value>
</init-param>
<init-param>
    <param-name>forceEncoding</param-name>
    <param-value>true</param-value>
</init-param>
</filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping> 
<servlet>
	<servlet-name>springmvc</servlet-name>
	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
<!-- 指定加载的配置文件 ，通过参数 contextConfigLocation 加载-->
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring/springmvc.xml</param-value>
    </init-param>
</servlet>
<servlet-mapping>
    <servlet-name>springmvc</servlet-name>
    <url-pattern>*.do</url-pattern>
</servlet-mapping>
</web-app>
```



创建包 com.pinyougou.manager.controller
在 src/main/resources 下创建 spring/springmvc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:p="http://www.springframework.org/schema/p"
xmlns:context="http://www.springframework.org/schema/context"
xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
xmlns:mvc="http://www.springframework.org/schema/mvc"
xsi:schemaLocation="http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans.xsd
http://www.springframework.org/schema/mvc
http://www.springframework.org/schema/mvc/spring-mvc.xsd
http://code.alibabatech.com/schema/dubbo
http://code.alibabatech.com/schema/dubbo/dubbo.xsd
http://www.springframework.org/schema/context
http://www.springframework.org/schema/context/spring-context.xsd">
<mvc:annotation-driven>
<mvc:message-converters register-defaults="true">
<bean
class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter">
<property name="supportedMediaTypes" value="application/json"/>
<property name="features">
<array>
<value>WriteMapNullValue</value>
<value>WriteDateUseDateFormat</value>
</array>
</property>
</bean>
</mvc:message-converters>
</mvc:annotation-driven>
<!-- 引用 dubbo 服务 -->
<dubbo:application name="pinyougou-manager-web" />
<dubbo:registry address="zookeeper://192.168.25.132:2181"/>
<dubbo:annotation package="com.pinyougou.manager.controller" />
</beans>        
```



#### 商家管理后台

构建 web 模块 pinyougou-shop-web 与运营商管理后台的构建方式类似。区别：
（1）定义 tomcat 的启动端口为 9102
（2）springmvc.xml

```xml
<!-- 引用 dubbo 服务 -->
<dubbo:application name="pinyougou-shop-web" />
<dubbo:registry address="zookeeper://192.168.25.132:2181"/>
<dubbo:annotation package="com.pinyougou.shop.controller" />
```

#### 实体类与数据访问层模块

可以使用idea自带的Mybatis 插件自动生成



## 品牌列表-后端代码

###   需求分析

完成品牌管理的后端代码，在浏览器可查询品牌的数据（json 格式）



### 数据库表

tb_brand 品牌表

| 字段       | 类型    | 长度 | 含义       |
| ---------- | ------- | ---- | ---------- |
| Id         | Bigint  |      | 主键       |
| Name       | varchar | 255  | 品牌名称   |
| First_char | Varchar | 1    | 品牌首字母 |

### 后端代码

#### 服务层接口

在 pinyougou-sellergoods-interface 工程创建 BrandService 接口

```java
/**
* 品牌服务层接口
* @author Administrator
*
*/
public interface BrandService {
    /**
    * 返回全部列表
    * @return
    */
	public List<TbBrand> findAll();
}
```

#### 服务实现类

在 pinyougou-sellergoods-service 工程创建 BrandServiceImpl 类

```java
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private TbBrandMapper brandMapper;
    @Override
    public List<TbBrand> findAll() {
    	return brandMapper.selectByExample(null);
    }
}
```

#### 控制层代码

在 pinyougou-manager-web 工程创建 com.pinyougou.manager.controller 包，包下创建BrandController 类

```java
/**
* 品牌 controller
* @author Administrator
*/
@RestController
@RequestMapping("/brand")
public class BrandController {
    @Reference
    private BrandService brandService;
    /**
    * 返回全部列表
    * @return
    */
     @RequestMapping("/findAll")
    public List<TbBrand> findAll(){ 
   		return brandService.findAll();
    }
}   
```

####   测试

启动 pinyougou-sellergoods-service
启动 pinyougou-manager-web
地址栏输入 http://localhost:9101/brand/findAll.do

得到返回json数据











