## 环境准备

|主机名|配置|角色|内网ip|虚拟ip|
|:-|:-|:-|:-|:-|
|k8s-master-01.cluster.local|2核4G|etcd-01,master-01|10.88.20.191|10.88.20.90|
|k8s-master-02.cluster.local|2核4G|etcd-02,master-02|10.88.20.192|-|
|k8s-master-03.cluster.local|2核4G|etcd-03,master-03|10.88.20.193|-|

#### 更新系统
    #在所有机器上执行
	yum update -y && yum upgrade -y
#### 安装常用工具和必要依赖
    #在所有机器上执行
    yum install -y epel-release \
	               vim \
	               curl \
	               wget \
	               telnet \
	               net-tools \
	               ntpdate \
	               yum-utils \
	               device-mapper-persistent-data \
	               lvm2
#### 设置主机名和内网地址
###### 【k8s-master-01】设置主机名
    hostname k8s-master-01.cluster.local
    hostnamectl set-hostname  k8s-master-01.cluster.local
    #注销，重新登录
    logout
###### 【k8s-master-02】设置主机名
    hostname k8s-master-02.cluster.local
    hostnamectl set-hostname  k8s-master-02.cluster.local
    #注销，重新登录
    logout
###### 【k8s-master-03】设置主机名
    hostname k8s-master-03.cluster.local
    hostnamectl set-hostname  k8s-master-03.cluster.local
    #注销，重新登录
    logout

#### 设置时区
	#设置时区
	ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
	#查看时间
	date

#### 互通测试
    #在所有机器上执行
    ping 10.88.20.191 -c 4 ; ping 10.88.20.192 -c 4 ; ping 10.88.20.193 -c 4
#### 关闭Swap
    #在所有机器上执行
    swapoff -a 
    sed -i 's/.*swap.*/#&/' /etc/fstab
    cat /etc/fstab
#### 设置内核参数
    #在所有机器上执行
    modprobe br_netfilter
    cat << EOF > /etc/sysctl.d/k8s.conf
    net.bridge.bridge-nf-call-ip6tables = 1
    net.bridge.bridge-nf-call-iptables = 1
    EOF
    sysctl -p /etc/sysctl.d/k8s.conf
    ls /proc/sys/net/bridge
#### 设置文件描述符选项
    #在所有机器上执行
    echo "* soft nofile 1048576" >> /etc/security/limits.conf
    echo "* hard nofile 1048576" >> /etc/security/limits.conf
    echo "* soft nproc 1048576"  >> /etc/security/limits.conf
    echo "* hard nproc 1048576"  >> /etc/security/limits.conf
    echo "* soft  memlock  unlimited"  >> /etc/security/limits.conf
    echo "* hard memlock  unlimited"  >> /etc/security/limits.conf
#### 关闭Selinux
    #在所有机器上执行
    setenforce  0 
    sed -i "s/^SELINUX=enforcing/SELINUX=disabled/g" /etc/sysconfig/selinux 
    sed -i "s/^SELINUX=enforcing/SELINUX=disabled/g" /etc/selinux/config 
    sed -i "s/^SELINUX=permissive/SELINUX=disabled/g" /etc/sysconfig/selinux 
    sed -i "s/^SELINUX=permissive/SELINUX=disabled/g" /etc/selinux/config
    getenforce
    /usr/sbin/sestatus -v
    #最好重启一下彻底关闭selinux
    shutdown -r now
#### 移除CentOS默认防火墙
    #在所有机器上执行
    systemctl disable firewalld
    systemctl stop firewalld
    yum erase -y firewalld
#### 设置NTP时间服务器
    #在所有机器上执行
    systemctl enable ntpdate
    systemctl restart ntpdate
    systemctl status ntpdate
## 安装配置etcd集群
#### 安装etcd
    #在所有机器上执行
    yum install -y etcd
#### 配置etcd集群
###### 【k8s-master-01】配置
    cat << EOF > /etc/etcd/etcd.conf
	#[Member]
	ETCD_DATA_DIR="/var/lib/etcd/default.etcd"
	ETCD_LISTEN_PEER_URLS="http://0.0.0.0:2380"
	ETCD_LISTEN_CLIENT_URLS="http://0.0.0.0:2379"
	ETCD_NAME="etcd-01"
	#[Clustering]
	ETCD_INITIAL_ADVERTISE_PEER_URLS="http://10.88.20.191:2380"
	ETCD_ADVERTISE_CLIENT_URLS="http://10.88.20.191:2379"
	ETCD_INITIAL_CLUSTER="etcd-01=http://10.88.20.191:2380,etcd-02=http://10.88.20.192:2380,etcd-03=http://10.88.20.193:2380"
	ETCD_INITIAL_CLUSTER_TOKEN="etcd-cluster"
	ETCD_INITIAL_CLUSTER_STATE="new"
	EOF
###### 【k8s-master-02】配置
    cat << EOF > /etc/etcd/etcd.conf
	#[Member]
	ETCD_DATA_DIR="/var/lib/etcd/default.etcd"
	ETCD_LISTEN_PEER_URLS="http://0.0.0.0:2380"
	ETCD_LISTEN_CLIENT_URLS="http://0.0.0.0:2379"
	ETCD_NAME="etcd-02"
	#[Clustering]
	ETCD_INITIAL_ADVERTISE_PEER_URLS="http://10.88.20.192:2380"
	ETCD_ADVERTISE_CLIENT_URLS="http://10.88.20.192:2379"
	ETCD_INITIAL_CLUSTER="etcd-01=http://10.88.20.191:2380,etcd-02=http://10.88.20.192:2380,etcd-03=http://10.88.20.193:2380"
	ETCD_INITIAL_CLUSTER_TOKEN="etcd-cluster"
	ETCD_INITIAL_CLUSTER_STATE="new"
	EOF
###### 【k8s-master-03】配置
	cat << EOF > /etc/etcd/etcd.conf
	#[Member]
	ETCD_DATA_DIR="/var/lib/etcd/default.etcd"
	ETCD_LISTEN_PEER_URLS="http://0.0.0.0:2380"
	ETCD_LISTEN_CLIENT_URLS="http://0.0.0.0:2379"
	ETCD_NAME="etcd-03"
	#[Clustering]
	ETCD_INITIAL_ADVERTISE_PEER_URLS="http://10.88.20.193:2380"
	ETCD_ADVERTISE_CLIENT_URLS="http://10.88.20.193:2379"
	ETCD_INITIAL_CLUSTER="etcd-01=http://10.88.20.191:2380,etcd-02=http://10.88.20.192:2380,etcd-03=http://10.88.20.193:2380"
	ETCD_INITIAL_CLUSTER_TOKEN="etcd-cluster"
	ETCD_INITIAL_CLUSTER_STATE="new"
	EOF
#### 启动etcd集群
    #在所有机器上执行
    systemctl daemon-reload
    systemctl enable etcd
    systemctl restart etcd
    systemctl status etcd
	
	#查看集群状态
    etcdctl cluster-health
<blockquote>
<pre>
member 7171f1e64ac1f09f is healthy: got healthy result from http://10.88.20.192:2379
member 84a5367dd99bc86f is healthy: got healthy result from http://10.88.20.193:2379
member c197a4e8bb0aaa42 is healthy: got healthy result from http://10.88.20.191:2379
cluster is healthy
</pre>
</blockquote>

## 安装Docker
#### 卸载旧版Docker
    #在所有机器上执行
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
#### 添加仓库
    #在所有机器上执行
    yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
    #查看docker版本
    yum list docker-ce --showduplicates
#### 安装Docker CE 17.03.2
    #因为Docker仓库的一些问题，所以这里直接通过指定rpm地址的方式安装特定版本
###### 通过Docker官网方式安装
    #在所有机器上执行
    yum install -y https://download.docker.com/linux/centos/7/x86_64/stable/Packages/docker-ce-selinux-17.03.2.ce-1.el7.centos.noarch.rpm
    yum install -y https://download.docker.com/linux/centos/7/x86_64/stable/Packages/docker-ce-17.03.2.ce-1.el7.centos.x86_64.rpm
###### 通过阿里云镜像方式安装
    #在所有机器上执行
    yum install -y https://mirrors.aliyun.com/docker-ce/linux/centos/7/x86_64/stable/Packages/docker-ce-selinux-17.03.2.ce-1.el7.centos.noarch.rpm
    yum install -y https://mirrors.aliyun.com/docker-ce/linux/centos/7/x86_64/stable/Packages/docker-ce-17.03.2.ce-1.el7.centos.x86_64.rpm
#### 配置docker开启TCP监听、docker镜像仓库（加速器）以及私有仓库（非安全访问）
    #在所有机器上执行
	vim /lib/systemd/system/docker.service
<blockquote>
<pre>
修改
ExecStart=/usr/bin/dockerd
为
ExecStart=/usr/bin/dockerd -H unix:///var/run/docker.sock -H tcp://0.0.0.0:2375 --registry-mirror=https://lre16p6q.mirror.aliyuncs.com --insecure-registry=docker.agthings.cn:8000
</pre>
</blockquote>

#### 启动Docker
    #在所有机器上执行
    systemctl daemon-reload
    systemctl enable docker
    systemctl restart docker
    systemctl status docker
    
    #查看docker版本
    docker version
## 安装kubeadm
#### 设置K8S的Yum源
###### 设置官方Yum源
    #在所有机器上执行
    cat <<EOF > /etc/yum.repos.d/kubernetes.repo
    [kubernetes]
    name=Kubernetes
    baseurl=https://packages.cloud.google.com/yum/repos/kubernetes-el7-x86_64
    enabled=1
    gpgcheck=1
    repo_gpgcheck=1
    gpgkey=https://packages.cloud.google.com/yum/doc/yum-key.gpg https://packages.cloud.google.com/yum/doc/rpm-package-key.gpg
    EOF

    #查看kubeadm版本
    yum list -y kubeadm --showduplicates
###### 设置阿里云Yum源
    #在所有机器上执行
    cat << EOF > /etc/yum.repos.d/kubernetes.repo
    [kubernetes]
    name=Kubernetes
    baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64/
    enabled=1
    gpgcheck=1
    repo_gpgcheck=1
    gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
    EOF

    #查看kubeadm版本
    yum list -y kubeadm --showduplicates
#### 安装kubelet1.9.3 kubeadm1.9.3 kubectl1.9.3
    #在所有机器上执行
    yum install -y kubeadm-1.9.3 kubectl-1.9.3 kubelet-1.9.3
#### 配置kubelet
    #在所有机器上执行
	vim /etc/systemd/system/kubelet.service.d/10-kubeadm.conf
<blockquote>
<pre>
修改
Environment="KUBELET_CGROUP_ARGS=--cgroup-driver=systemd"
为
Environment="KUBELET_CGROUP_ARGS=--cgroup-driver=cgroupfs"
添加
Environment="KUBELET_EXTRA_ARGS=--v=2 --fail-swap-on=false --pod-infra-container-image=docker.agthings.cn:8000/k8s/pause-amd64:3.0"
</pre>
</blockquote>

#### 启用kubelet（不需要启动）
    #在所有机器上执行
    systemctl daemon-reload
    systemctl enable kubelet
> ## *这个时候kubelet是无法正确启动的，不用担心，在这里只需要启用就行了，kubeadm初始化时会调用系统功能来启动*

## 初始化k8s集群（一阶段）
#### 设置虚拟IP
    #在k8s-master-01上执行
    ip addr add 10.88.20.190/24 dev ens192
    
#### 创建集群初始编排文件
    #在k8s-master-01上执行
    cat << EOF > MasterConfiguration.yml
	apiVersion: kubeadm.k8s.io/v1alpha1
	kind: MasterConfiguration
	etcd:
	  endpoints:
	  - http://10.88.20.191:2379
	  - http://10.88.20.192:2379
	  - http://10.88.20.193:2379
	networking:
	  podSubnet: 10.244.0.0/16
	kubernetesVersion: v1.9.3
	api:
	  advertiseAddress: 10.88.20.190
	apiServerExtraArgs: 
  	  service-node-port-range: 1-65535
	token: eeeeee.eeeeeeeeeeeeeeee
	tokenTTL: 0s
	apiServerCertSANs:
	- 10.88.20.190
	- 10.88.20.191
	- 10.88.20.192
	- 10.88.20.193
	imageRepository: docker.agthings.cn:8000/k8s
	EOF
#### 初始化集群
    #在k8s-master-01上执行
    kubeadm init --config MasterConfiguration.yml

<blockquote>
成功输出
<pre>
Your Kubernetes master has initialized successfully!

To start using your cluster, you need to run the following as a regular user:

  mkdir -p $HOME/.kube
  sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
  sudo chown $(id -u):$(id -g) $HOME/.kube/config

You should now deploy a pod network to the cluster.
Run "kubectl apply -f [podnetwork].yaml" with one of the options listed at:
  https://kubernetes.io/docs/concepts/cluster-administration/addons/

You can now join any number of machines by running the following on each node
as root:

  kubeadm join --token 79cb01.238dfced93bb1781 10.88.20.190:6443 --discovery-token-ca-cert-hash sha256:f1e12f11913abaf0067cf272dc99a5822a097cba57f6a917624d086cfa4b8326
</pre>
</blockquote>

> ## *上面输出的最后一句是用来让节点加入集群的，每台主节点初始化时会出现一次，以后不会再出现，所以必须进行备份*

#### *初始化失败后处理办法*
    kubeadm reset
    #或
    rm -rf /etc/kubernetes/*.conf
    rm -rf /etc/kubernetes/manifests/*.yaml
    docker ps -a |awk '{print $1}' |xargs docker rm -f
    systemctl  stop kubelet

#### 按照成功提示设置相关工具的集群配置文件
    #在k8s-master-01上执行
    mkdir -p $HOME/.kube
    sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
    sudo chown $(id -u):$(id -g) $HOME/.kube/config
#### 安装网络组建（这里选择flannel）
###### 下载编排文件
    #在k8s-master-01上执行
    wget https://raw.githubusercontent.com/coreos/flannel/v0.10.0/Documentation/kube-flannel.yml
###### 修改镜像地址
    #在k8s-master-01上执行
    vim kube-flannel.yml

<blockquote>
<pre>
quay.io/coreos/flannel:v0.10.0-amd64
改为（一共两个地方）
docker.agthings.cn:8000/k8s/flannel:v0.10.0-amd64
</pre>
</blockquote>

###### 安装网络组件
    #在k8s-master-01上执行
    kubectl apply -f kube-flannel.yml
#### 查看集群节点信息和pod信息
    #在k8s-master-01上执行
    kubectl get node -o wide
    kubectl get pods --all-namespaces -o wide

<blockquote>
<pre>
NAME                          STATUS    ROLES     AGE       VERSION   EXTERNAL-IP   OS-IMAGE                KERNEL-VERSION               CONTAINER-RUNTIME
k8s-master-01.cluster.local   Ready     master    24m       v1.9.3    <none>        CentOS Linux 7 (Core)   3.10.0-693.21.1.el7.x86_64   docker://17.3.2
</pre>
<pre>
kube-system   coredns-65dcdb4cf-djzb6                               1/1       Running   0          23m       10.244.0.2     k8s-master-01.cluster.local
kube-system   kube-apiserver-k8s-master-01.cluster.local            1/1       Running   0          23m       10.88.20.191   k8s-master-01.cluster.local
kube-system   kube-controller-manager-k8s-master-01.cluster.local   1/1       Running   0          24m       10.88.20.191   k8s-master-01.cluster.local
kube-system   kube-flannel-ds-2c9fx                                 1/1       Running   0          43s       10.88.20.191   k8s-master-01.cluster.local
kube-system   kube-proxy-n45vr                                      1/1       Running   0          23m       10.88.20.191   k8s-master-01.cluster.local
kube-system   kube-scheduler-k8s-master-01.cluster.local            1/1       Running   0          24m       10.88.20.191   k8s-master-01.cluster.local
</pre>
</blockquote>

> ## *等待STATUS变为Ready之后再继续操作*
## 初始化k8s集群（二阶段）
#### 将其他两个节点加入到集群
    #在k8s-master-02、k8s-master-03上分别执行
    scp -r 10.88.20.191:/etc/kubernetes/pki /etc/kubernetes/
    #如果不复制这些文件，初始化集群时会尝试创建新的集群而不是加入集群
    scp -r 10.88.20.191:~/MasterConfiguration.yml ~/
    #要初始化集群必须有MasterConfiguration.yml文件
    kubeadm init --config ~/MasterConfiguration.yml
    #初始化的结果和k8s-master-01的结果应该完全一样
    
#### 按照成功提示设置相关工具的集群配置文件
    #在k8s-master-02、k8s-master-03上分别执行
    mkdir -p $HOME/.kube
    sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
    sudo chown $(id -u):$(id -g) $HOME/.kube/config
#### 查看集群信息
    #在任意节点上执行
    kubectl get node -o wide
    kubectl get pods --all-namespaces -o wide

<blockquote>
<pre>
[root@k8s-master-03 ~]# kubectl get node -o wide
NAME                          STATUS    ROLES     AGE       VERSION   EXTERNAL-IP   OS-IMAGE                KERNEL-VERSION               CONTAINER-RUNTIME
k8s-master-01.cluster.local   Ready     master    38m       v1.9.3    <none>        CentOS Linux 7 (Core)   3.10.0-693.21.1.el7.x86_64   docker://17.3.2
k8s-master-02.cluster.local   Ready     master    5m        v1.9.3    <none>        CentOS Linux 7 (Core)   3.10.0-693.21.1.el7.x86_64   docker://17.3.2
k8s-master-03.cluster.local   Ready     master    4m        v1.9.3    <none>        CentOS Linux 7 (Core)   3.10.0-693.21.1.el7.x86_64   docker://17.3.2
[root@k8s-master-03 ~]#     kubectl get pods --all-namespaces -o wide
NAMESPACE     NAME                                                  READY     STATUS    RESTARTS   AGE       IP             NODE
kube-system   coredns-65dcdb4cf-djzb6                               1/1       Running   0          37m       10.244.0.2     k8s-master-01.cluster.local
kube-system   kube-apiserver-k8s-master-01.cluster.local            1/1       Running   0          37m       10.88.20.191   k8s-master-01.cluster.local
kube-system   kube-apiserver-k8s-master-02.cluster.local            1/1       Running   0          5m        10.88.20.192   k8s-master-02.cluster.local
kube-system   kube-apiserver-k8s-master-03.cluster.local            1/1       Running   0          4m        10.88.20.193   k8s-master-03.cluster.local
kube-system   kube-controller-manager-k8s-master-01.cluster.local   1/1       Running   0          38m       10.88.20.191   k8s-master-01.cluster.local
kube-system   kube-controller-manager-k8s-master-02.cluster.local   1/1       Running   0          5m        10.88.20.192   k8s-master-02.cluster.local
kube-system   kube-controller-manager-k8s-master-03.cluster.local   1/1       Running   0          4m        10.88.20.193   k8s-master-03.cluster.local
kube-system   kube-flannel-ds-2c9fx                                 1/1       Running   0          14m       10.88.20.191   k8s-master-01.cluster.local
kube-system   kube-flannel-ds-4pc7j                                 1/1       Running   0          4m        10.88.20.193   k8s-master-03.cluster.local
kube-system   kube-flannel-ds-ln49p                                 1/1       Running   0          5m        10.88.20.192   k8s-master-02.cluster.local
kube-system   kube-proxy-mcljt                                      1/1       Running   0          5m        10.88.20.192   k8s-master-02.cluster.local
kube-system   kube-proxy-n45vr                                      1/1       Running   0          37m       10.88.20.191   k8s-master-01.cluster.local
kube-system   kube-proxy-ndsk7                                      1/1       Running   0          4m        10.88.20.193   k8s-master-03.cluster.local
kube-system   kube-scheduler-k8s-master-01.cluster.local            1/1       Running   0          38m       10.88.20.191   k8s-master-01.cluster.local
kube-system   kube-scheduler-k8s-master-02.cluster.local            1/1       Running   0          5m        10.88.20.192   k8s-master-02.cluster.local
kube-system   kube-scheduler-k8s-master-03.cluster.local            1/1       Running   0          4m        10.88.20.193   k8s-master-03.cluster.local

</pre>
</blockquote>

## 允许主节点参与普通pod的调度
    #在任意节点上执行
    kubectl taint nodes --all node-role.kubernetes.io/master-
## 集群apiserver高可用配置
#### 安装keepalived
    #在所有节点上执行
    yum install -y keepalived
#### 配置keepalived
###### 配置k8s-master-01的keepalived
    #在k8s-master-01上执行
<blockquote>
<pre>
cat << EOF > /etc/keepalived/keepalived.conf
global_defs {
    router_id LVS_k8s
}
global_defs {
    router_id LVS_k8s
}
vrrp_script CheckK8sMaster {
    script "curl -k https://10.88.20.190:6443"
    interval 3
    timeout 9
    fall 2
    rise 2
}
vrrp_instance VI_1 {
    state BACKUP
    interface ens192
    virtual_router_id 61
    priority 100
    advert_int 1
    mcast_src_ip 10.88.20.191
    nopreempt
    authentication {
        auth_type PASS
        auth_pass 0123456789
    }
    unicast_peer {
        10.88.20.192
        10.88.20.193
    }
    virtual_ipaddress {
        10.88.20.190/24
    }
    track_script {
        CheckK8sMaster
    }
}
EOF
</pre>
</blockquote>

###### 配置k8s-master-02的keepalived
    #在k8s-master-02上执行
<blockquote>
<pre>
cat << EOF > /etc/keepalived/keepalived.conf
global_defs {
    router_id LVS_k8s
}
global_defs {
    router_id LVS_k8s
}
vrrp_script CheckK8sMaster {
    script "curl -k https://10.88.20.190:6443"
    interval 3
    timeout 9
    fall 2
    rise 2
}
vrrp_instance VI_1 {
    state BACKUP
    interface ens192
    virtual_router_id 61
    priority 90
    advert_int 1
    mcast_src_ip 10.88.20.192
    nopreempt
    authentication {
        auth_type PASS
        auth_pass 0123456789
    }
    unicast_peer {
        10.88.20.191
        10.88.20.193
    }
    virtual_ipaddress {
        10.88.20.190/24
    }
    track_script {
        CheckK8sMaster
    }
}
EOF
</pre>
</blockquote>

###### 配置k8s-master-03的keepalived
    #在k8s-master-01上执行
<blockquote>
<pre>
cat << EOF > /etc/keepalived/keepalived.conf
global_defs {
    router_id LVS_k8s
}
global_defs {
    router_id LVS_k8s
}
vrrp_script CheckK8sMaster {
    script "curl -k https://10.88.20.190:6443"
    interval 3
    timeout 9
    fall 2
    rise 2
}
vrrp_instance VI_1 {
    state BACKUP
    interface ens192
    virtual_router_id 61
    priority 80
    advert_int 1
    mcast_src_ip 10.88.20.193
    nopreempt
    authentication {
        auth_type PASS
        auth_pass 0123456789
    }
    unicast_peer {
        10.88.20.191
        10.88.20.192
    }
    virtual_ipaddress {
        10.88.20.190/24
    }
    track_script {
        CheckK8sMaster
    }
}
EOF
</pre>
</blockquote>

#### 启动keepalived
    #依次启动k8s-master-01、k8s-master-02、k8s-master-03上的keepalived
    systemctl daemon-reload
    systemctl enable keepalived
    systemctl restart keepalived
    systemctl status keepalived
#### 验证keepalived
    #依次重启k8s-master-01、k8s-master-02、k8s-master-03，查看ip是否按预期在漂移
    shutdown -r now
    #到其他主机查看ip漂移情况
    ip addr
    
> ## *至此k8s集群的核心部分已经搭建完毕，之后是Dashboard、heapster等其他组件的安装和设置可以参考k8s使用的其他文章*