FROM node:8-alpine AS builder
WORKDIR /workspace
COPY . /workspace
RUN npm config set unsafe-perm true
RUN npm config set sass_binary_site https://npm.taobao.org/mirrors/node-sass/
RUN npm install @agrithings/cmap \
                @agrithings/common \
                @vehiclecloud/commons \
                @vehiclecloud/grpc-websocket-client \
                @vehiclecloud/json-schema \
                @vehiclecloud/json-schema-vuetify \
                @vehiclecloud/mongo-rest-client \
                @vehiclecloud/redis-rest-client \
                @vehiclecloud/oauth2-client \
                --registry=http://nexus.agthings.cn:8081/repository/npm-all/
RUN npm install --registry=https://registry.npm.taobao.org
RUN npm run build

FROM nginx:alpine
RUN rm -rf /usr/share/nginx/html /usr/share/nginx/dist /etc/nginx/conf.d/*
COPY --from=builder /workspace/dist /usr/share/nginx/dist
COPY --from=builder /workspace/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /workspace/run.sh /run.sh
CMD ["/bin/sh", "-c","chmod +x /run.sh && /run.sh"]