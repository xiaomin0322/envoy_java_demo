FROM lyft/envoy:latest
RUN apt-get update
CMD /usr/local/bin/envoy -c /etc/envoy-zipkin.yaml --service-cluster service_product
