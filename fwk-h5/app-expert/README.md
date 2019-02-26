## 农事管理系统App地块详情内嵌HTML5模块发布说明
#### 设置工作目录
    export WORKSPACE=`pwd`
#### 获取源码
    git clone -b <发布分支> https://gitee.com/agrithings/fwk.git
#### 编译Docker镜像文件
    #进入源码目录
    cd $WORKSPACE/fwk/app-expert/web
    #构建测试镜像
    docker build . -t docker.agthings.cn:8000/fwk-app:<版本号>
    docker push docker.agthings.cn:8000/fwk-app:<版本号>
#### 复制Docker编排文件到服务器
    #进入文件目录
    cd $WORKSPACE/fwk/app-expert/runtime-prod
    #复制文件到发布服务器
    scp -r * <服务器账号>@<服务器地址>:<发布目录>
#### 执行发布
    #登录服务器
    ssh root@10.88.20.171
    #进入发布目录
    cd <发布目录>
    #修改docker-compose.yml文件中的镜像版本号为实际生成的版本号
    vim docker-compose.yml
    #修改fwk-app.env文件中对应的环境变量设置
    vim fwk-app.env
    #启动服务
    chmod +x ./run.sh
    ./run.sh
    #停止服务
    #chmod +x ./shutdown.sh
    #./shutdown.sh
#### 环境变量说明
    #支撑数据库名
    AG_MONGO_ISS_DATABASE=iov
    #支撑数据库 api访问地址
    AG_MONGO_ISS_BASE=http://localhost:9000
    #物联网数据库名
    AG_MONGO_IOT_DATABASE=iot
    #物联网数据库 api访问地址
    AG_MONGO_IOT_BASE=http://localhost:9000
    #农事系统数据库名（暂未使用）
    AG_MONGO_FWK_DATABASE=fwk
    #农事系统数据库 api访问地址（暂未使用）
    AG_MONGO_FWK_BASE=http://localhost:9000
    #Redis数据库 api访问地址
    AG_REDIS_BASE=http://localhost:9000
    #Grpc api访问地址
    AG_GRPC_WS=ws://localhost:9200/api/access/ws
    #OAuth2 api访问地址
    AG_OAUTH2_SERVER=http://localhost:9100
    #OAuth2 ClientId(要求全环境唯一，与支撑平台定义有关)
    AG_OAUTH2_CLIENT_ID=1
    #文件服务器 api访问地址
    AG_FS_SERVER=http://localhost:9400
    #支撑平台 api访问地址
    AG_ISS_SERVER=http://localhost:8100
    #（不清楚，清和说不用管）
    AG_ISS_PROVINCE_ID=530000000000530000000000
    #（不清楚，清和说不用管）
    AG_ISS_PROVINCE_NAME=云南
    #（不清楚，清和说不用管）
    AG_ISS_PROVINCE_CODE=530000000000
    #（不清楚，清和说不用管）
    AG_ISS_PROVINCE_LAT=24.267
    #（不清楚，清和说不用管）
    AG_ISS_PROVINCE_LNG=101.7554
    #农事系统 api访问地址
    AG_FWK_SERVER=http://localhost:80
    #萤石云 api代理访问地址（要求做跨域处理）
    AG_YS7_SERVER=http://localhost:80
    #萤石云 AppKey
    AG_YS7_APP_KEY=--------------------------------
    #萤石云 AppSecret
    AG_YS7_APP_SECRET=--------------------------------