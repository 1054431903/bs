#### 一、安装jenkins

本次安装使用docker方式安装，具体安装步骤略。安装默认插件，启动好配置完管理员账号。

#### 二、配置集群

**登录系统**

> 点击【系统管理】-->【管理节点】

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/EB9BF7A4E53543169DC96A66A5B9805E/7433)

**节点列表：**

> jenkins服务所在服务器为master节点，master负责分发任务给slave节点

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/0EEC2C403C5D4129AF98C1B215BBFC52/7487)

> 点击左边【新建节点】，选中【固定代理】

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/A66B354D2EC0406FA5B56EBF4F36D5C9/7497)

**配置节点**

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/BADED0416E30428EA80DBD9B20A6F094/7502)

> 这时节点也配置好了,但是slave节点有可能没有连接的状态，这时我们使用slave机器进入Jenkins的管理节点页面

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/2195B1E3E4474C59883B0B35AC8CACEA/7510)

> 点击该节点，进入节点页面，如果出现以下界面，说明slave节点已经正常启动

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/FA4D54CEA03A4CA39B6426602A7A8FEB/7525)

> 现在回到jenkins的管理节点页面，刷新一下界面，发现slave节点的红色 的那个X已经没有了，说明连接成功

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/9C229CE9CE1E4976AAAD2A65C00DDCA8/7530)

**关联job**

> 进入job的配置页面，勾选【限制项目运行的节点(Restrict where this project can be run)】，并且在【标签表达式(】Label Expression)输入从节点的标签名（一个标签有可能对应多台slave节点）,也可以输入slave节点的名称

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/4FDD4D483DB64293A9310226675829ED/7535)


> 这样配置好之后就可以实现分布式构建，在做自动化测试的时候我们只需要将测试服务器配置为slave节点，配置好相应的job来监控开发job或者开发的代码是否有变动或者构建的动作，根据自己的情况执行测试项目的构建动作，从而实现持续集成