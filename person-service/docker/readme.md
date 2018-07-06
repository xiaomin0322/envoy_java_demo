start:


1.

docker build -t envoy:v2_person .


2.docker run -d --name envoy_person -p 9902:9901  -p 10002:10002 envoy:v2_person

