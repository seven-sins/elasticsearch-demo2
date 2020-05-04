package com.hiya3d.demo.conf;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfRestClient {

	@Value("${elasticsearch.ip1}")
	String ipAddress;
	
	@Bean("highLevelClient")
	public RestHighLevelClient highLevelClient() {
		String[] address = ipAddress.split(":");
		String ip = address[0];
		int port = Integer.parseInt(address[1]);
		HttpHost httpHost = new HttpHost(ip, port, "http");
		
		return new RestHighLevelClient(RestClient.builder(new HttpHost(httpHost)));
	}
}
