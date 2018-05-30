# SpringBoot 框架与短信解决方案

## 课程目标

- 目标 1：掌握 Spring Boot 框架的搭建方法
- 目标 2：能够使用阿里大于发送短信
- 目标 3：运用 SpringBoot、阿里大于和 ActiveMQ 开发短信微服务
- 目标 4：完成品优购用户注册功能（短信验证码认证）



## Spring Boot 入门

### springboot


Spring 诞生时是 Java 企业版（Java Enterprise Edition，JEE，也称 J2EE）的轻量级代替品。无需开发重量级的 Enterprise JavaBean（EJB），Spring 为企业级 Java 开发提供了一种相对简单的方法，通过依赖注入和面向切面编程，用简单的 Java 对象（Plain Old Java Object，POJO）实现了 EJB 的功能。
虽然 Spring 的组件代码是轻量级的，但它的配置却是重量级的。一开始，Spring 用 XML配置，而且是很多 XML 配置。Spring 2.5 引入了基于注解的组件扫描，这消除了大量针对应用程序自身组件的显式 XML 配置。Spring 3.0 引入了基于 Java 的配置，这是一种类型安全的可重构配置方式，可以代替 XML。所有这些配置都代表了开发时的损耗。因为在思考Spring 特性配置和解决业务问题之间需要进行思维切换，所以写配置挤占了写应用程序逻辑的时间。和所有框架一样，Spring 实用，但与此同时它要求的回报也不少。
除此之外，项目的依赖管理也是件吃力不讨好的事情。决定项目里要用哪些库就已经够让人头痛的了，你还要知道这些库的哪个版本和其他库不会有冲突，这难题实在太棘手。并且，依赖管理也是一种损耗，添加依赖不是写应用程序代码。一旦选错了依赖的版本，随之而来的不兼容问题毫无疑问会是生产力杀手。

Spring Boot 让这一切成为了过去。

Spring Boot 是 Spring 社区较新的一个项目。该项目的目的是帮助开发者更容易的创建基于 Spring 的应用程序和服务，让更多人的人更快的对 Spring 进行入门体验，为 Spring生态系统提供了一种固定的、约定优于配置风格的框架。

Spring Boot 具有如下特性：

（1）为基于 Spring 的开发提供更快的入门体验

（2）开箱即用，没有代码生成，也无需 XML 配置。同时也可以修改默认值来满足特定的需求。
（3）提供了一些大型项目中常见的非功能性特性，如嵌入式服务器、安全、指标，健康检测、外部配置等。
（4）Spring Boot 并不是不对 Spring 功能上的增强，而是提供了一种快速使用 Spring的方式。



### Spring Boot 入门小 Demo

#### 起步依赖

创建 Maven 工程 springboot_demo（打包方式 jar）

在 pom.xml 中添加如下依赖

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.4.0.RELEASE</version>
</parent>
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

我们会惊奇地发现，我们的工程自动添加了好多好多 jar 包

![52755314309](H:\itheima大数据项目班\项目班\0529\images\1527553143091.png)





而这些 jar 包正式我们做开发时需要导入的 jar 包。因为这些 jar 包被我们刚才引入的spring-boot-starter-web 所引用了，所以我们引用 spring-boot-starter-web 后会自动把依赖传递过来



####  变更 JDK 版本

我们发现默认情况下工程的 JDK 版本是 1.6 ,而我们通常用使用 1.7 的版本，所以我们需要在pom.xml 中添加以下配置

```html
<properties>
	<java.version>1.7</java.version>
</properties
```

添加后更新工程，会发现版本已经变更为 1.7

####  引导类

只需要创建一个引导类 .

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }
}
```



#### Spring MVC 实现 Hello World 输出



我们现在开始使用 spring MVC 框架，实现 json 数据的输出。如果按照我们原来的做法，需要在 web.xml 中添加一个 DispatcherServlet 的配置，再添加一个 spring 的配置文件，配置文件中需要添加如下配置

```xml
<!-- 使用组件扫描，不用将 controller 在 spring 中配置 -->
<context:component-scan base-package="cn.itcast.demo.controller" />
<!-- 使用注解驱动不用在下边定义映射器和适配置器 -->
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
```

但是我们用 SpringBoot，这一切都省了。我们直接写 Controller 类

```java
@RestController
public class HelloWorldController {
    @RequestMapping("/info")
    public String info(){
    	return "HelloWorld";
    } 
}    
```

我们运行启动类来运行程序
在浏览器地址栏输入 http://localhost:8080/info 即可看到运行结果

####  修改 tomcat 启动端口

在 src/main/resources 下创建 application.properties

```properties
server.port=8088
```

重新运行引导类。地址栏输入

http://localhost:8088/info

#### 读取配置文件信息

在 src/main/resources 下的 application.properties 增加配置

```properties
url=http://www.itcast.cn
```

我要在类中读取这个配置信息，修改 HelloWorldController

```java
@Autowired
private Environment env;
@RequestMapping("/info")
public String info(){
	return "HelloWorld~~"+env.getProperty("url");
}
```

####  热部署

我们在开发中反复修改类、页面等资源，每次修改后都是需要重新启动才生效，这样每次启动都很麻烦，浪费了大量的时间，能不能在我修改代码后不重启就能生效呢？可以，在pom.xml 中添加如下配置就可以实现这样的功能，我们称之为热部署。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
</dependency>
```

赶快试试看吧，是不是很爽。

### Spring Boot 与 ActiveMQ 整合

#### 使用内嵌服务

（1）在 pom.xml 中引入 ActiveMQ 起步依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-activemq</artifactId>
</dependency>
```

（2）创建消息生产者

```java
/**
* 消息生产者
* @author Administrator
*/
@RestController
public class QueueController {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @RequestMapping("/send")
    public void send(String text){
    	jmsMessagingTemplate.convertAndSend("itcast", text);
    }
}
```

（3）创建消息消费者

```java
@Component
public class Consumer {
    @JmsListener(destination="itcast")
    public void readMessage(String text){
    	System.out.println("接收到消息："+text);
    } 
}
```



测试：启动服务后，在浏览器执行
http://localhost:8088/send.do?text=aaaaa
即可看到控制台输出消息提示。Spring Boot 内置了 ActiveMQ 的服务，所以我们不用单独启动也可以执行应用程序。

#### 使用外部服务



在 src/main/resources 下的 application.properties 增加配置, 指定 ActiveMQ 的地址

```properties
spring.activemq.broker-url=tcp://192.168.25.135:61616
```

运行后，会在 activeMQ 中看到发送的 queue

![52755377923](H:\itheima大数据项目班\项目班\0529\images\1527553779238.png)

#### 发送 Map 信息

（1）修改 QueueController.java

```java
@RequestMapping("/sendmap")
public void sendMap(){
    Map map=new HashMap<>();
    map.put("mobile", "13900001111");
    map.put("content", "恭喜获得 10 元代金券");
    jmsMessagingTemplate.convertAndSend("itcast_map",map);
}
```

（2）修改 Consumer.java

```java
@JmsListener(destination="itcast_map")
public void readMap(Map map){
	System.out.println(map);
}	
```

## 短信发送平台-阿里大于

### 阿里大于简介

阿里大于是 阿里云旗下产品，融合了三大运营商的通信能力，通过将传统通信业务和能力与互联网相结合，创新融合阿里巴巴生态内容，全力为中小企业和开发者提供优质服务阿里大于提供包括短信、语音、流量直充、私密专线、店铺手机号等个性化服务。通过阿里大于打通三大运营商通信能力，全面融合阿里巴巴生态，以开放 API 及 SDK 的方式向开发者提供通信和数据服务，更好地支撑企业业务发展和创新服务。

### 准备工作

#### 注册账户

首先我们先进入“阿里大于” www.alidayu.com （https://dayu.aliyun.com/）

![52755394274](H:\itheima大数据项目班\项目班\0529\images\1527553942740.png)

![52755397965](H:\itheima大数据项目班\项目班\0529\images\1527553979654.png)

####  登陆系统

使用刚才注册的账号进行登陆。

![52755402069](H:\itheima大数据项目班\项目班\0529\images\1527554020699.png)



![52755404226](H:\itheima大数据项目班\项目班\0529\images\1527554042260.png)



![52755405178](H:\itheima大数据项目班\项目班\0529\images\1527554051786.png)



点击使用



![52755406449](H:\itheima大数据项目班\项目班\0529\images\1527554064490.png)



#### 申请签名



![52755408170](H:\itheima大数据项目班\项目班\0529\images\1527554081705.png)

![52755408996](H:\itheima大数据项目班\项目班\0529\images\1527554089963.png)

![52755413000](H:\itheima大数据项目班\项目班\0529\images\1527554130007.png)

#### 申请模板

![52755415469](H:\itheima大数据项目班\项目班\0529\images\1527554154691.png)

![52755416478](H:\itheima大数据项目班\项目班\0529\images\1527554164782.png)

#### 创建 accessKey



![52755418265](H:\itheima大数据项目班\项目班\0529\images\1527554182655.png)



![52755419170](H:\itheima大数据项目班\项目班\0529\images\1527554191708.png)



![52755419966](H:\itheima大数据项目班\项目班\0529\images\1527554199664.png)

![52755420964](H:\itheima大数据项目班\项目班\0529\images\1527554209640.png)

![52755421694](H:\itheima大数据项目班\项目班\0529\images\1527554216943.png)

![52755422688](H:\itheima大数据项目班\项目班\0529\images\1527554226889.png)



### SDK 安装

从阿里云通信官网上下载 Demo 工程

![52755424713](H:\itheima大数据项目班\项目班\0529\images\1527554247134.png)



解压后导入 

![52755426257](H:\itheima大数据项目班\项目班\0529\images\1527554262575.png)

红线框起来的两个工程就是阿里云通信的依赖 jar 源码，我们将其安装到本地仓库（删除 aliyun-java-sdk-core 的单元测试类）本地 jar 包安装后alicom-dysms-api 工程引入依赖

```xml
<dependencies>
<dependency>
    <groupId>com.aliyun</groupId>
    <artifactId>aliyun-java-sdk-dysmsapi</artifactId>
<version>1.0.0-SNAPSHOT</version>
</dependency>
<dependency>
<groupId>com.aliyun</groupId>
<artifactId>aliyun-java-sdk-core</artifactId>
<version>3.2.5</version>
</dependency>
</dependencies>
```

红叉消失了 :-)

![52755433955](H:\itheima大数据项目班\项目班\0529\images\1527554339555.png)

#### 发送短信测试

（1）打开 SmsDemo

替换下列几处代码

![52755437386](H:\itheima大数据项目班\项目班\0529\images\1527554373860.png)

这个 accessKeyId 和 accessSecret 到刚才申请过的
手机号，短信签名和模板号

![52755439669](H:\itheima大数据项目班\项目班\0529\images\1527554396692.png)

模板参数

![52755441046](H:\itheima大数据项目班\项目班\0529\images\1527554410463.png)

number 是我们申请模板时写的参数
执行 main 方法我们就可以在手机收到短信啦



## 短信微服务

### 需求分析

构建一个通用的短信发送服务（独立于品优购的单独工程），接收 activeMQ 的消息（MAP类型） 消息包括手机号（mobile）、短信模板号（template_code）、签名（sign_name）、参数字符串（param ）

### 代码实现

####  工程搭建

（1）创建工程 itcast_sms （JAR 工程），POM 文件引入依赖

```xml
<properties>
<java.version>1.7</java.version>
</properties>
<parent>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-parent</artifactId>
<version>1.4.0.RELEASE</version>
</parent>
<dependencies>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-activemq</artifactId>
</dependency>
<dependency>
<groupId>com.aliyun</groupId>
<artifactId>aliyun-java-sdk-dysmsapi</artifactId>
<version>1.0.0-SNAPSHOT</version>
</dependency>
<dependency>
<groupId>com.aliyun</groupId>
<artifactId>aliyun-java-sdk-core</artifactId>
<version>3.2.5</version>
</dependency>
</dependencies>    
```

（2）创建引导类

```java 
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
   		SpringApplication.run(Application.class, args);
    }
}
```

（3）创建配置文件 application.properties

```properties
server.port=9003
spring.activemq.broker-url=tcp://192.168.25.135:61616
accessKeyId=不告诉你
accessKeySecret=不告诉你
```

#### 短信工具类

参照之前的短信 demo 创建短信工具类

```java
/**
* 短信工具类
* @author Administrator
*
*/
@Component
public class SmsUtil {
//产品名称:云通信短信 API 产品,开发者无需替换
static final String product = "Dysmsapi";
//产品域名,开发者无需替换
static final String domain = "dysmsapi.aliyuncs.com";
@Autowired
private Environment env;
// TODO 此处需要替换成开发者自己的 AK(在阿里云访问控制台寻找)
/**
* 发送短信
* @param mobile 手机号
* @param template_code 模板号
* @param sign_name 签名
* @param param 参数
* @return
* @throws ClientException
*/
public SendSmsResponse sendSms(String mobile,String template_code,String sign_name,String param) throws ClientException {
String accessKeyId =env.getProperty("accessKeyId");
String accessKeySecret = env.getProperty("accessKeySecret");
//可自助调整超时时间
System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//初始化 acsClient,暂不支持 region 化
IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
accessKeySecret);
DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
IAcsClient acsClient = new DefaultAcsClient(profile);
//组装请求对象-具体描述见控制台-文档部分内容
SendSmsRequest request = new SendSmsRequest();
//必填:待发送手机号
request.setPhoneNumbers(mobile);
//必填:短信签名-可在短信控制台中找到
request.setSignName(sign_name);
//必填:短信模板-可在短信控制台中找到
request.setTemplateCode(template_code);
//可选:模板中的变量替换 JSON 串,如模板内容为"亲爱的${name},您的验证码为${code}"时,
此处的值为
request.setTemplateParam(param);
//选填-上行短信扩展码(无特殊需求用户请忽略此字段)
//request.setSmsUpExtendCode("90997");
//可选:outId 为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
request.setOutId("yourOutId");
//hint 此处可能会抛出异常，注意 catch
SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
return sendSmsResponse;
}
public QuerySendDetailsResponse querySendDetails(String mobile,String bizId)
throws ClientException {
String accessKeyId =env.getProperty("accessKeyId");    
String accessKeySecret = env.getProperty("accessKeySecret");
//可自助调整超时时间
System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//初始化 acsClient,暂不支持 region 化
IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
accessKeySecret);
DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
IAcsClient acsClient = new DefaultAcsClient(profile);
//组装请求对象
QuerySendDetailsRequest request = new QuerySendDetailsRequest();
//必填-号码
request.setPhoneNumber(mobile);
//可选-流水号
request.setBizId(bizId);
//必填-发送日期 支持 30 天内记录查询，格式 yyyyMMdd
SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
request.setSendDate(ft.format(new Date()));
//必填-页大小
request.setPageSize(10L);
//必填-当前页码从 1 开始计数
request.setCurrentPage(1L);
//hint 此处可能会抛出异常，注意 catch
QuerySendDetailsResponse querySendDetailsResponse =    
acsClient.getAcsResponse(request);
return querySendDetailsResponse;
}
}    
```

#### 消息监听类

创建 SmsListener.java

```java
/**
* 消息监听类
* @author Administrator
*/
@Component
public class SmsListener {
    @Autowired
    private SmsUtil smsUtil;
    @JmsListener(destination="sms")
    public void sendSms(Map<String,String> map){ 
        try {
            SendSmsResponse response = smsUtil.sendSms(
                map.get("mobile"),
                map.get("template_code"),
                map.get("sign_name"),
                map.get("param") );
            System.out.println("Code=" + response.getCode());
            System.out.println("Message=" + response.getMessage());
            System.out.println("RequestId=" + response.getRequestId());
            System.out.println("BizId=" + response.getBizId());
        } catch (ClientException e) {
            e.printStackTrace();
        } 
    }
}   
```

###  代码测试

修改 springboot-demo 的 QueueController.java

```java
@RequestMapping("/sendsms")
public void sendSms(){
Map map=new HashMap<>();
map.put("mobile", "13900001111");
map.put("template_code", "SMS_85735065");
map.put("sign_name", "黑马");
map.put("param", "{\"number\":\"102931\"}");
jmsMessagingTemplate.convertAndSend("sms",map);
}    
```

启动 itcast_sms
启动 springboot-demo
地址栏输入：http://localhost:8088/sendsms.do
观察控制台输出

![52755585401](H:\itheima大数据项目班\项目班\0529\images\1527555854014.png)



随后短信也成功发送到你的手机上

## 用户注册

### 需求分析

完成用户注册功能

![52755590361](H:\itheima大数据项目班\项目班\0529\images\1527555903610.png)

### 工程搭建

#### 用户服务接口层

（1）创建 pinyougou-user-interface（jar）
（2）引入 pojo 依赖

####  用户服务实现层

（1）创建 pinyougou-user-service（war）
（2）引入 spring dubbox activeMQ 相关依赖，引入依赖（ pinyougou-user-interface pinyougou-dao pinyougou-common），运行端口为 9006
（3）添加 web.xml
（4）创建 Spring 配置文件 applicationContext-service.xml 和 applicationContent-tx.xml

```xml 
<dubbo:protocol name="dubbo" port="20886" />
<dubbo:annotation package="com.pinyougou.user.service.impl" />
<dubbo:application name="pinyougou-user-service"/>
<dubbo:registry address="zookeeper://192.168.25.135:2181"/>
```

#### 用户中心 WEB 层

创建 war 工程 pinyougou-user-web 我们将注册功能放入此工程
（1）添加 web.xml
（2）引入依赖 pinyougou-user-interface 、spring 相关依赖（参照其它 web 工程）,tomcat 运行端口 9106
（3）添加 spring 配置文件
（4）拷贝静态原型页面 register.html 及相关资源

### 基本注册功能实现

####  生成和拷贝代码

![52755601643](H:\itheima大数据项目班\项目班\0529\images\1527556016436.png)



![52755603509](H:\itheima大数据项目班\项目班\0529\images\1527556035092.png)



![52755604320](H:\itheima大数据项目班\项目班\0529\images\1527556043207.png)

![52755607702](H:\itheima大数据项目班\项目班\0529\images\1527556077021.png)



#### 后端服务实现层

修改 pinyougou-user-service 的 UserServiceImpl.java

```java
/**
* 增加
*/
@Override
public void add(TbUser user) {
    user.setCreated(new Date());//创建日期
    user.setUpdated(new Date());//修改日期
    String password = DigestUtils.md5Hex(user.getPassword());//对密码加密
    user.setPassword(password);
    userMapper.insert(user);
}
```



#### 前端控制层

修改 userController.js

```js
//控制层
app.controller('userController' ,function($scope,$controller ,userService){ 
    //注册
    $scope.reg=function(){ 
        if($scope.entity.password!=$scope.password) {
            alert("两次输入的密码不一致，请重新输入"); 
            return ;
            }
        userService.add( $scope.entity ).success(
            function(response){
                alert(response.message);
            } 
        );
    }
});
```



####  修改页面

修改页面 register.html ，引入 js

```html
<script type="text/javascript" src="plugins/angularjs/angular.min.js"></script>
<script type="text/javascript" src="js/base.js"></script>
<script type="text/javascript" src="js/service/userService.js"></script>
<script type="text/javascript" src="js/controller/userController.js"></script>
```

指令

```html
<body ng-app="pinyougou" ng-controller="userController" >
```

绑定表单

```html
<form class="sui-form form-horizontal">
    <div class="control-group">
        <label class="control-label">用户名：</label>
        <div class="controls">
            <input type="text" placeholder=" 请输入你的用户名 "
                   ng-model="entity.username" class="input-xfat input-xlarge">
        </div>
    </div>
    <div class="control-group">
        <label for="inputPassword" class="control-label">登录密码：</label>
        <div class="controls">
            <input type="password" placeholder=" 设置登录密码 "
                   ng-model="entity.password" class="input-xfat input-xlarge">
        </div>
    </div>
    <div class="control-group">
        <label for="inputPassword" class="control-label">确认密码：</label>
        <div class="controls">
            <input type="password" placeholder=" 再次确认密码 "
                   ng-model="password" class="input-xfat input-xlarge">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">手机号：</label>
        <div class="controls">
            <input type="text" placeholder=" 请输入你的手机号 "
                   ng-model="entity.phone" class="input-xfat input-xlarge">
        </div>
    </div>
    <div class="control-group">
        <label for="inputPassword" class="control-label">短信验证码：</label>
        <div class="controls">
            <input type="text" placeholder=" 短信验证码 " class="input-xfat
                                                            input-xlarge"> <a href="#">获取短信验证码</a>
        </div>
    </div>
    <div class="control-group">
        <label for="inputPassword"
               class="control-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
        <div class="controls">
            <input name="m1" type="checkbox" value="2" checked=""><span>同意协议
            并注册《品优购用户协议》</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label"></label>
        <div class="controls btn-reg">
            <a class="sui-btn btn-block btn-xlarge btn-danger" ng-click="reg()"
               target="_blank">完成注册</a>
        </div>
    </div>
</form>    
```

### 注册判断短信验证码

#### 实现思路

点击页面上的”获取短信验证码”连接，向后端传递手机号。后端随机生成 6 位数字作为短信验证码，将其保存在 redis 中（手机号作为 KEY），并发送到短信网关。

用户注册时，后端根据手机号查询 redis 中的验证码与用户填写的验证码是否相同，如果不同则提示用户不能注册。

####  生成验证码

（1）修改 pinyougou-user-interface 工程 UserService.java ，增加方法

```java
/**
* 生成短信验证码
* @return
*/
public void createSmsCode(String phone);
```

（2）修改 pinyougou-user-service 工程的 UserServiceImpl.java

```java
/**
* 生成短信验证码
*/
public void createSmsCode(String phone){
    //生成 6 位随机数
    String code = (long) (Math.random()*1000000)+"";
    System.out.println("验证码："+code);
    //存入缓存
    redisTemplate.boundHashOps("smscode").put(phone, code);
    //发送到 activeMQ ....
}
```

（3）在 pinyougou-common 添加工具类 PhoneFormatCheckUtils.java，用于验证手机号
（4）修改 pinyougou-user-web 的 UserController.java

```java
/**
* 发送短信验证码
* @param phone
* @return
*/
@RequestMapping("/sendCode")
public Result sendCode(String phone){
    //判断手机号格式
    if(!PhoneFormatCheckUtils.isPhoneLegal(phone)){
        return new Result(false, "手机号格式不正确");
    } 
    try {
        userService.createSmsCode(phone);//生成验证码
        return new Result(true, "验证码发送成功");
    } catch (Exception e) {
        e.printStackTrace();
        return new Result(true, "验证码发送失败");
    } 
}

```

（5）pinyougou-user-web 的 userService.js

```js
//发送验证码
this.sendCode=function(phone){
    return $http.get("../user/sendCode.do?phone="+phone);
}
```

（6）pinyougou-user-web 的 userController.js

```js
//发送验证码
$scope.sendCode=function(){
    if($scope.entity.phone==null){
        alert("请输入手机号！");
        return ;
    } 
    userService.sendCode($scope.entity.phone).success(
        function(response){
            alert(response.message);
        } 
    );
}    
```

（7）修改页面 register.html

```html
<a ng-click="sendCode()" >获取短信验证码</a>
```

####  用户注册判断验证码

（1）修改 pinyougou-user-interface 的 UserService.java

```java
/**
* 判断短信验证码是否存在
* @param phone
* @return
*/
public boolean checkSmsCode(String phone,String code);
```

（2）修改 pinyougou-user-service 的 UserServiceImpl.java

```java
/**
* 判断验证码是否正确
*/
public boolean checkSmsCode(String phone,String code){
    //得到缓存中存储的验证码
    String sysCode = (String) redisTemplate.boundHashOps("smscode").get(phone);
    if(sysCode==null){
        return false;
    }
    if(!sysCode.equals(code)){
        return false;
    }
    return true;
}    
```

（3）修改 pinyougou-user-web 的 UserController.java

```java
/**
* 增加
* @param user
* @return
*/
@RequestMapping("/add")
public Result add(@RequestBody TbUser user,String smscode){
    boolean checkSmsCode = userService.checkSmsCode(user.getPhone(), smscode);
    if(checkSmsCode==false){
        return new Result(false, "验证码输入错误！"); 
    } 
    try {
        userService.add(user);
        return new Result(true, "增加成功");
    } catch (Exception e) {
        e.printStackTrace();
        return new Result(false, "增加失败");
    }
}    
```

（4）修改 pinyougou-user-web 的 userService.js

```js
//增加
this.add=function(entity,smscode){
    return $http.post('../user/add.do?smscode='+smscode ,entity );
}
```

（5）修改 pinyougou-portal-web 的 UserController.java

```js
//保存
$scope.reg=function(){ 
    userService.add( $scope.entity, $scope.smscode ).success(
        function(response){
            alert(response.message);
        } 
    );
}
```

（6）修改页面，绑定变量

```html
<input type="text" placeholder=" 短信验证码 " ng-model="smscode" class="input-xfat                                                                  input-xlarge"> <a href="#" ng-click="sendCode()">获取短信验证码</a>
```

####  短信验证码发送到手机

（1）在 pinyougou-user-service 添加配置文件 applicationContext-activemq.xml

```xml
<!-- 真正可以产生 Connection 的 ConnectionFactory，由对应的 JMS 服务厂商提供-->
<bean id="targetConnectionFactory"
      class="org.apache.activemq.ActiveMQConnectionFactory">
    <property name="brokerURL" value="tcp://192.168.25.135:61616"/>
</bean>
<!-- Spring 用于管理真正的 ConnectionFactory 的 ConnectionFactory -->
<bean id="connectionFactory"
      class="org.springframework.jms.connection.SingleConnectionFactory">
    <!-- 目标 ConnectionFactory 对应真实的可以产生 JMS Connection 的 ConnectionFactory -->
    <property name="targetConnectionFactory" ref="targetConnectionFactory"/>
</bean>
<!-- Spring 提供的 JMS 工具类，它可以进行消息发送、接收等 -->
<bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
    <!-- 这个 connectionFactory 对应的是我们定义的 Spring 提供的那个 ConnectionFactory
对象 -->
    <property name="connectionFactory" ref="connectionFactory"/>
</bean>
<!--这个是点对点消息 -->
<bean id="smsDestination" class="org.apache.activemq.command.ActiveMQQueue">
    <constructor-arg value="sms"/>
</bean>
```

（2）修改 pinyougou-user-service 的 UserServiceImpl.java

```java
@Autowired
private JmsTemplate jmsTemplate;
@Autowired
private Destination smsDestination; 
@Value("${template_code}")
private String template_code;
@Value("${sign_name}")
private String sign_name;
/**
* 生成短信验证码
*/
public void createSmsCode(final String phone){
    //生成 6 位随机数
    final String code = (long) (Math.random()*1000000)+"";
    System.out.println("验证码："+code);
    //存入缓存
    redisTemplate.boundHashOps("smscode").put(phone, code);
    //发送到 activeMQ
    jmsTemplate.send(smsDestination, new MessageCreator() {
        @Override
        public Message createMessage(Session session) throws JMSException {
            MapMessage mapMessage = session.createMapMessage(); 
            mapMessage.setString("mobile", phone);//手机号
            mapMessage.setString("template_code", "SMS_85735065");//模板编号
            mapMessage.setString("sign_name", "黑马");//签名 
            Map m=new HashMap<>();
            m.put("number", code); 
            mapMessage.setString("param", JSON.toJSONString(m));//参数
            return mapMessage;
        }
    });
}   
```

（3）在 pinyougou-common 的 properties 目录下创建配置文件 sms.properties

```properties
template_code=SMS_85735065
sign_name=\u9ED1\u9A6C
```









