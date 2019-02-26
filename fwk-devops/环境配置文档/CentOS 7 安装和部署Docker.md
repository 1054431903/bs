### 前言
Docker 要求 CentOS 系统的内核版本高于 3.10 ，查看本页面的前提条件来验证你的CentOS 版本是否支持 Docker 。通过 uname -r 命令查看你当前的内核版本

```
uname -r 
```

### 一、初步安装和启动docker
1、使用 root 权限登录 Centos。确保 yum 包更新到最新

```
yum update -y
```
2、卸载旧版本(如果安装过旧版本的话)

```
yum remove -y docker \
              docker-client \
              docker-client-latest \
              docker-common \
              docker-latest \
              docker-latest-logrotate \
              docker-logrotate \
              docker-selinux \
              docker-engine-selinux \
              docker-engine
```
3、安装docker

```
yum -y install docker-io
```
4、启动docker服务

```
systemctl enable docker
systemctl start docker
```

二、设置镜像加速及Nexus的docker仓库地址


```
{
  "registry-mirrors": ["https://jra463dj.mirror.aliyuncs.com"],
  "insecure-registries":["192.168.16.129:8000"]
}

```

### 三、开放管理端口映射
1、修改docker的配置文件：

文件位置：/etc/sysconfig/docker

```
vim /etc/sysconfig/docker
```
在OPTIONS参数后加入以下代码：

```
-H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock
```
![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/5C6813D06BFC40478FCFFC08B177C0B4/6371)

2、防火墙打开2375端口

```
firewall-cmd --add-port=2375/tcp --permanent
```



### 四、重启docker

```
systemctl daemon-reload
systemctl restart docker
```

### 五、测试docker是否正常安装和运行

```
docker run hello-world
```
### 六、查看结果


```
Hello from Docker!
This message shows that your installation appears to be working correctly.
```


