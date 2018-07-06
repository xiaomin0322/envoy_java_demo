FROM lyft/envoy:latest
RUN apt-get update
COPY envoy-zipkin.yaml /etc/envoy-zipkin.yaml
CMD /usr/local/bin/envoy -c /etc/envoy-zipkin.yaml --service-cluster service_gate
