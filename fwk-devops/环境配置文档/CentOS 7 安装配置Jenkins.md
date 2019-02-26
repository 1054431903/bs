### 一、Jenkins安装
##### 1、拉取jenkins的镜像
docker 命令：

```
docker pull jenkins/jenkins
```

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/4582711B3E454D0F8866EF48352E7BF0/7188)

看到以上图片显示内容则说明已经成功拉取镜像，也可以用以下命令来查看当前下载的镜像信息：

```
docker images
```

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/3EC2EDA0468343068235BC1B0A19492A/7224)

##### 2、创建数据卷

创建一个数据卷，专门存储jenkins的相关数据，命令如下：

```
docker volume create --name jenkins-data
```

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/DCE50EC4BD0F4913AE0E2EB2F35DD064/7222)

##### 3、运行镜像

运行镜像，并设置对外暴露的端口以及挂载卷，命令如下：

```
docker run --name jenkins -d -p 8080:8080  -v jenkins-data:/var/jenkins_home jenkins/jenkins
```

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/27F1D86DD4C74F1B9318E2506D885E90/7214)

第一次启动以后，会在日志里输入jenkins web端默认登录密码，如下图所示：

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/F1B46D7E322E42DAB6B0C0C296D6C0BD/7205)

查看日志的命令：

```
docker logs jenkins 
```

也可以去【/var/jenkins_home/secrets/initialAdminPassword】这个目录下查看对应的初始密码。

> 注意：【/var/jenkins_home/】为jenkins的工作目录，因为之前我们已经创建过卷并挂载到本地，所以可以先查看jenkins的数据卷的目录，命令：

```
docker volume inspect jenkins-data
```

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/32B5E66385FD4EE3B0F21A93D350F991/7215)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/FC6F7C3E5FFE406DA610AC0E1388ADA8/7207)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/98F18B8B15C54C4998FDC3849F362859/7226)

> 把此密码记录下来，然后登录Web端的时候需要输入此密码。
 至此，jenkins已经安装完成并启动服务，下面需要登录网页端去做相应配置。
 
 
###  二、Jenkins配置

##### 1、登录Web端

> web端地址：http://192.168.16.129:8080

> 输入刚才保存的密码，然后下一步

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/89A79C5DA74C4438A3FBD0C00046AB00/7169)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/13EFBE1735E54F9DA3A790A1CAF9C854/7167)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/65D2C792EBAD4CA4BC87A993ACF18F14/7172)

> 以上插件全部安装成功以后进入以下界面：

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/F9CA74468C324846AFFEC78957C1B8E3/7170)

> 点击保存并完成

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/1D1EF4B82ECC4CD1BEAF83A963CC1C15/7201)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/E28045A5564D49DFA61864466B36A222/7185)

##### 2、安装需要的插件

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/08EF86019AF14699A38BA953FCF0097B/7186)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/0A8C8F531BD940B39708012DF27FF7FB/7162)

**安装SSH插件**

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/68465A6D3E5D4B0F81E4CB47E677834B/7166)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/0CB1FDA258FC4C3B8FB8442299DE4A08/7197)

**安装NodeJS插件**

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/11C5D77A0EDB45F282D290F293DC7562/7199)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/E04A7A5B42E44E1A8D074E5C515985FE/7194)

**安装docker-build-step**

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/1A20B87575D34DC8997ADF4E47980EB7/7208)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/598C96EFA3BF4526AFD9A66CC61B8893/7202)

**安装ThinBackup插件**

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/5CEF839A57784F4494D1E7512FD6E800/7206)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/73AC08EED1E34FD69AD856BEB65F7034/7204)

##### 3、安装需要用到的模块

> 在此功能中可以配置jdk、git、maven、nodejs等功能模块

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/08897807D95E4FC98395E8EC1920B855/7160)

**安装JDK**

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/4579020B0F71446AB30F8FA06736B0EB/7171)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/3FD0AFAE040C40B9B1C944F7826DCE04/7216)

**安装Git**

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/E13571CC7F204427A1D8E5FE9C0269A6/7219)

**安装Maven**

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/07F4D977D68A42BC80D861CFFF373A38/7213)

**安装NodeJS**

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/2EA00679A9934BB9B6A72F763246897A/7173)

> 最后点击保存按钮，保存配置。

##### 4、系统设置

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/AA536BA271E54752BD51FC1D289D71D1/7163)

**配置ssh远程站点信息**

> 如果需要用到ssh去远程服务器上执行脚本或命令，则需要配置此功能

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/373ED3C4FBCD476A8A7E078FDF3C16A2/7184)

> 如果此处添加用户信息没反应，并且前面那个下拉框里也没有任何用户，那请根据以下操作来创建用户：

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/BF95B4C7E1FE467E9C1FCA5608AD8464/7181)

> 点击 Credentials--->System--->Global Credentials (unrestricted)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/0099309136284329A3B53B871ED623B9/7193)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/F76B282C4B894DF59181DE96FF6F1C4C/7203)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/D0FCBB9FE6C948A298F46D8582680607/7189)

> 至此新建用户已经完成，现在回到前面配置ssh站点信息

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/2BB0FE4499004D63988A9FE9E53B3E76/7198)

**Docker Builder URL**

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/AF1DCC7F97184E208DC289A59B1602CA/7221)

##### 5、配置ThinBackup

> 在安装好ThinBackup插件后，直接在【系统管理】菜单里找到【ThinBackup】

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/16FFEF91C8FF4EAA816035424896ECC6/7165)

> 点击【Settings】进行设置

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/D8BD7883FAB3421C924CCCC651FD4205/7175)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/417A1F6A6AE34D069395214BF54C36D9/7178)


### 三、创建测试任务

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/5C57C68B61474779B5D683329C43CB4C/7196)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/016F4E116560464EA3DD7B54EA6370BF/7161)

##### 1、通用配置

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/853E1B44D9664241962A0254DF83D706/7182)

##### 2、源码管理

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/073BD67B30124AD5AA55D2966BA63EFF/7176)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/08FF4B6228094F9F9B4DCB41B93AE964/7179)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/AA07A28CC01447908FF13BC7F685D3E3/7187)

##### 3、构建触发器

> 如果需要，则建立相应的触发器，如果不需要，则不配置。

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/2048AC050D15465781110F0274CB5193/7174)

##### 4、构建环境

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/13DAB549EA3B4BCC94F018CA1036A7CA/7191)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/E8B586AB61F74EA493DD53768A757E13/7180)

> 该步骤可以配置很多构建环境，比如需要Node环境，则按第二张图片这样配置。

##### 5、构建

> 在此步骤可以执行很多东西，比如执行shell命令、maven编译、docker命令等等。

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/EC5F13D275F9487C939A5105F7A3E8A3/7212)

**Maven构建**

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/C9D2E3A3A6B74E2EB1186FE6E409BCC1/7223)

**创建镜像**

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/2AF1F45D421944FD83F2D0CFDBAD5EBF/7195)

**镜像打tag**

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/F64EC26E946E41F88C41D49B35A113FC/7192)

**镜像推送**

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/089AAF11B082485297AB7B003842B16F/7177)

**删除本地镜像**

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/77B7688C7986495A9297957F9C782881/7211)

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/10C9701C45F846B4A00DC400127612E2/7220)

**远程执行shell命令**

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/1BC734E4FE974AA5A1429A1B26B56AA5/7200)

> 至此，基本的jenkins的配置已完成。