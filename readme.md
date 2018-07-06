## Envoy Proxy with Microservices

Detailed description can be found here: [Envoy Proxy with Microservices](https://piotrminkowski.wordpress.com/2017/10/25/envoy-proxy-with-microservices/) 



參考連接：
https://piotrminkowski.wordpress.com/2017/10/25/envoy-proxy-with-microservices/
http://www.servicemesher.com/envoy/configuration/overview/v2_overview.html
https://github.com/envoyproxy/envoy/blob/master/examples/zipkin-tracing/front-envoy-zipkin.yaml
http://www.servicemesher.com/envoy/start/sandboxes/zipkin_tracing.html#install-sandboxes-zipkin-tracing
https://github.com/envoyproxy/envoy/blob/master//examples/front-proxy/service.py
https://github.com/piomin/sample-envoy-proxy


----------------------官方集成zipkin说明----------------------------------------------------
说明：
根据参考资料案例：http://www.servicemesher.com/envoy/start/sandboxes/zipkin_tracing.html#install-sandboxes-zipkin-tracing
官网的demo是py的简单脚本边切的
$ pwd
envoy/examples/zipkin-tracing
$ docker-compose up --build -d
$ docker-compose ps
        Name                       Command               State      Ports
-------------------------------------------------------------------------------------------------------------
zipkintracing_service1_1      /bin/sh -c /usr/local/bin/ ... Up       80/tcp
zipkintracing_service2_1      /bin/sh -c /usr/local/bin/ ... Up       80/tcp
zipkintracing_front-envoy_1   /bin/sh -c /usr/local/bin/ ... Up       0.0.0.0:8000->80/tcp, 0.0.0.0:8001->8001/tcp



zipkintracing_front-envoy_1
: 为网关入口,就是一个、envoy代理，具体参考：
https://raw.githubusercontent.com/envoyproxy/envoy/master/examples/zipkin-tracing/front-envoy-zipkin.yaml

zipkintracing_service1_1 
: 服务1。容器中有一个envoy服务，有一个py web服务
https://raw.githubusercontent.com/envoyproxy/envoy/master/examples/zipkin-tracing/service1-envoy-zipkin.yaml

zipkintracing_service2_1 
: 服务2 容器中有一个envoy服务，有一个py web服务

https://raw.githubusercontent.com/envoyproxy/envoy/master/examples/zipkin-tracing/service2-envoy-zipkin.yaml



访问流程：curl -v $(docker-machine ip default):8000/trace/1

路由流程：
1.请求先到zipkintracing_front-envoy_1
2.油zipkintracing_front-envoy路由到zipkintracing_front-envoy_1
重点:
3.访问zipkintracing_service1_1服务中的方法，。该方法又访问了服务2中的方法。
访问服务2中的方法不是直接路由到服务2中的envoy代理。而是通过访问本地服务1中的envoy代理，然后通过服务1中的代理。在路由至服务2中的envoy，相当于一个请求的 进和出都要经过该服务的envoy。这样才能记录出各种指标。，这样确实也多转了几趟。性能降低了。有利有弊吧。



---------------------自己集成java版 zipkin说明----------------------------------------------------
按照官方demo模拟

1.先启动envoy_gate容器

2.启动person-service的envoy,再启动person-service服务（可以打到一个容器中一起启动）

3.启动product-service的envoy,再启动product-servicee服务（可以打到一个容器中一起启动）


访问流程：
1.访问 http://192.168.16.201:9999/product/findById?id=1，该请求到envoy_gate，由envoy_gate转发到product-service的envoy

2.该请求访问了person服务中的方法：

 HttpGet get =new HttpGet("http://127.0.0.1:10003/person/findById?id="+id);
 
 该请求先到product-service的envoy，然后再转到person-service的envoy。如果直接转到person-service的envoy。zipkin调用链信息串不起来
 
 
 3.http://192.168.16.201:9411/zipkin 查看zipkin信息。发现一条调用链，有两个span
 
 
 
 
  



