FROM node:8-alpine
WORKDIR /app
COPY . /app
RUN npm install -g cnpm --registry=https://registry.npm.taobao.org
RUN cnpm install @agrithings/cmap @agrithings/common @vehiclecloud/commons @vehiclecloud/grpc-websocket-client @vehiclecloud/json-schema @vehiclecloud/json-schema-vuetify @vehiclecloud/mongo-rest-client @vehiclecloud/redis-rest-client --registry=http://rc.ami-tek.com.cn:9081/repository/npm-public/
RUN cnpm install
#CMD ["npm", "run", "dev"]
