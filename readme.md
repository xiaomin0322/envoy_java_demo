## Envoy Proxy with Microservices

Detailed description can be found here: [Envoy Proxy with Microservices](https://piotrminkowski.wordpress.com/2017/10/25/envoy-proxy-with-microservices/) 



�����B�ӣ�
https://piotrminkowski.wordpress.com/2017/10/25/envoy-proxy-with-microservices/
http://www.servicemesher.com/envoy/configuration/overview/v2_overview.html
https://github.com/envoyproxy/envoy/blob/master/examples/zipkin-tracing/front-envoy-zipkin.yaml
http://www.servicemesher.com/envoy/start/sandboxes/zipkin_tracing.html#install-sandboxes-zipkin-tracing
https://github.com/envoyproxy/envoy/blob/master//examples/front-proxy/service.py
https://github.com/piomin/sample-envoy-proxy


----------------------�ٷ�����zipkin˵��----------------------------------------------------
˵����
���ݲο����ϰ�����http://www.servicemesher.com/envoy/start/sandboxes/zipkin_tracing.html#install-sandboxes-zipkin-tracing
������demo��py�ļ򵥽ű����е�
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
: Ϊ�������,����һ����envoy��������ο���
https://raw.githubusercontent.com/envoyproxy/envoy/master/examples/zipkin-tracing/front-envoy-zipkin.yaml

zipkintracing_service1_1 
: ����1����������һ��envoy������һ��py web����
https://raw.githubusercontent.com/envoyproxy/envoy/master/examples/zipkin-tracing/service1-envoy-zipkin.yaml

zipkintracing_service2_1 
: ����2 ��������һ��envoy������һ��py web����

https://raw.githubusercontent.com/envoyproxy/envoy/master/examples/zipkin-tracing/service2-envoy-zipkin.yaml



�������̣�curl -v $(docker-machine ip default):8000/trace/1

·�����̣�
1.�����ȵ�zipkintracing_front-envoy_1
2.��zipkintracing_front-envoy·�ɵ�zipkintracing_front-envoy_1
�ص�:
3.����zipkintracing_service1_1�����еķ��������÷����ַ����˷���2�еķ�����
���ʷ���2�еķ�������ֱ��·�ɵ�����2�е�envoy��������ͨ�����ʱ��ط���1�е�envoy����Ȼ��ͨ������1�еĴ�����·��������2�е�envoy���൱��һ������� ���ͳ���Ҫ�����÷����envoy���������ܼ�¼������ָ�ꡣ������ȷʵҲ��ת�˼��ˡ����ܽ����ˡ������бװɡ�



---------------------�Լ�����java�� zipkin˵��----------------------------------------------------
���չٷ�demoģ��

1.������envoy_gate����

2.����person-service��envoy,������person-service���񣨿��Դ�һ��������һ��������

3.����product-service��envoy,������product-servicee���񣨿��Դ�һ��������һ��������


�������̣�
1.���� http://192.168.16.201:9999/product/findById?id=1��������envoy_gate����envoy_gateת����product-service��envoy

2.�����������person�����еķ�����

 HttpGet get =new HttpGet("http://127.0.0.1:10003/person/findById?id="+id);
 
 �������ȵ�product-service��envoy��Ȼ����ת��person-service��envoy�����ֱ��ת��person-service��envoy��zipkin��������Ϣ��������
 
 
 3.http://192.168.16.201:9411/zipkin �鿴zipkin��Ϣ������һ����������������span
 
 
 
 
  



