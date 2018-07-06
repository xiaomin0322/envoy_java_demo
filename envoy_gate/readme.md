start:


1.docker build -t envoy:v2_gate .

2.docker run -d --name envoy_gate -p 9903:9901  -p 9999:9999 envoy:v2_gate