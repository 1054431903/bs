#### 一、环境
- Sonatype Nexus3 latest
- Docker 18.04.0-ce

#### 二、安装配置
##### Sonatype Nexus3
Sonatype Nexus3 可以直接在docker hub上通过docker 命令拉取安装，这里给出docker hub的网址：https://hub.docker.com

具体的安装配置过程请参考以上网址。

> 1、拉取镜像

```
docker pull sonatype/nexus3
```

> 2、持久化数据

通过挂载卷的方式，把nexus的数据单独管理

```
docker volume create --name nexus-data

docker run -d -p 8081:8081 -p 8000:8000 --name nexus -v nexus-data:/nexus-data sonatype/nexus3
```
> 以上第二句代码中的第二个端口8000是等后续nexus服务启动后需要配置安全的端口

> 3、默认用户名密码

默认的用户名密码为：admin/admin123

> 4、web端访问地址

web端访问地址为：192.168.99.100:8081，

#### 三、创建Docker私有仓库
> 1、通过浏览器访问Nexus，http://服务器IP:8081

我这里是192.168.99.100:8081，默认用户名密码为：admin/admin123

> 2、创建一个docker仓库

- 点击设置界面，选择Repositories，点击Create repository，如下图所示： 

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/92CEBBCC8C9642EE8EBF9F08619BA8AE/5799)

> 点击【Create repository】

- 选择仓库类型，这里Docker有三种类型，分别是group、hosted、proxy。这里只演示hosted类型，所以选择docker(hosted)，如下图：

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/F35CDF41FAFD4B2EB0C160A3A9F64772/5812)

> 配置仓库，按以下图片上做相应输入

- 首先必须为该仓库指定一个唯一的名称，然后是HTTP的端口，最后是docker的api与该仓库进行交互，如下图： 

![image](https://note.youdao.com/yws/public/resource/6ca5a34764ff86c307218f636f90fb1e/xmlnote/24A13B88A36447CCACDD87D9BFD459D6/5849)


```
注意：
1、docker仓库名称规则：【xxx-registry】
2、HTTP端口：默认设置为8000，这里设置过此端口，对应的防火墙也要把此端口打开
3、需要启用Docker V1 API
4、需要允许匿名拉取
5、注意这个HTTP端口号也比较关键，在上面修改docker服务时指定的参数需要用到，后面与该仓库进行交互也需要用到。
```

> 3、安全配置，添加 Docker Bearer Token Realm

【Security】-->【Realms】，把左边的【Docker Bearer Token Realm】添加到右边 -->【Save】

#### 四、上传镜像
- 登录私服仓库
> 注意在push上传之前必须要先登录

```
docker login 192.168.99.100:8000
```
- 打标记
> 在上传镜像之前需要先打一个tag，用于版本标记。 
格式是这样的： 

```
docker tag <imageId or imageName> <nexus-hostname>:<repository-port>/<image>:<tag> 
```
例如：

```
 docker tag busybox 192.168.99.100:8000/busybox:latest
```
- 最后上传镜像：
```
docker push 192.168.99.100:8000/busybox:latest
```

#### 五、pull镜像
从私服中下载镜像也很简单，执行以下命令即可：

```
docker pull 192.168.99.100:8000/busybox
```

#### 六、搜索镜像
搜索镜像也与之前的类似，如下：
```
docker search 192.168.99.100:8000/busybox 
```
就可以搜索带有lusyoe的镜像了。

