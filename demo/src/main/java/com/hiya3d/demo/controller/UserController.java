package com.hiya3d.demo.controller;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiya3d.demo.conf.ElasticsearchConfRestClient;

@RestController
public class UserController {

	@Autowired
	ElasticsearchConfRestClient restClient;
	
	@GetMapping("/test")
	public Object test(String text) throws IOException {
		SearchRequest searchRequest = new SearchRequest("user");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.matchQuery("name", text));
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
		SearchResponse searchResponse = restClient.highLevelClient().search(searchRequest, RequestOptions.DEFAULT);
		
		return searchResponse.getHits();
	}
}






















