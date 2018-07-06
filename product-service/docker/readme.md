start:


1.

docker build -t envoy:v2_product .


2.docker run -d --name envoy_product -v /home/caoyujing/envoy/sample-envoy-proxy-master/v2_product/envoy-zipkin.yaml:/etc/envoy-zipkin.yaml -v /usr/java/jdk1.8.0_152:/root/java -v /home/caoyujing/envoy/sample-envoy-proxy-master/v2_product:/root/v2 -p 9901:9901 -p 10001:10001  envoy:v2_product 


3.docker exec it id bash


4. nohup /root/java/bin/java -jar /root/v2/product-service.jar  >> /root/log.out  &