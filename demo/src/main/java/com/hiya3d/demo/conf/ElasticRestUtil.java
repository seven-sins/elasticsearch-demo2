package com.hiya3d.demo.conf;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ElasticRestUtil {
	@Autowired
	RestHighLevelClient restHighLevelClient;

	public Object query(String text) throws IOException{
		//模糊查询
//        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("name", text);
//        matchQueryBuilder.fuzziness(Fuzziness.AUTO); // 启动模糊查询
//        matchQueryBuilder.prefixLength(3); // 在匹配查询上设置前缀长度选项
//        matchQueryBuilder.maxExpansions(10); // 设置最大扩展选项以控制查询的模糊过程
        
        // BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("name", "xxx"));

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.boolQuery()//
				.should(QueryBuilders.wildcardQuery("name", "*" + text + "*"))//
				.should(QueryBuilders.matchQuery("age", 100).analyzer("ik_max_word"))//
		).from(0).size(100);
        
        
        
//        searchSourceBuilder.query(matchQueryBuilder);//.query(boolQueryBuilder);

        //排序
        searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));//
        searchSourceBuilder.sort(new FieldSortBuilder("_id").order(SortOrder.ASC));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        return search;
	}
	
//	public Result<List<JSONObject>> complexQuery(
//			String index, 
//			String type, 
//			JSONObject must, 
//			JSONObject should, 
//			JSONObject range, 
//			int pageNum, 
//			int pageSize) {
//		int start = (pageNum - 1) * pageSize;
//		
//		SearchRequest searchRequest = new SearchRequest("user");
//		// 
//		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//		/**
//		 * 1.must
//		 */
//		if(must != null) {
//			for (String key : must.keySet()) {
//				if(must.get(key) != null) {
//					boolQueryBuilder.must(QueryBuilders.termQuery(key, must.get(key)));
//				}
//			}
//		}
//		/**
//		 * 2.should
//		 */
//		if(should != null) {
//			for (String key : should.keySet()) {
//				if(should.get(key) != null) {
//					boolQueryBuilder.should(QueryBuilders.matchPhraseQuery(key, should.get(key)));
//				}
//			}
//		}
//		/**
//		 * 3.range
//		 * {"key": "visitTime", "type": "eq", "value": "2019-01-01 00:00:00"}
//		 * {"key": "visitTime", "type": "lt-gt", "begin": "2019-01-01 00:00:00", "end": "2019-01-02 00:00:00"}
//		 */
//		if(range != null) {
//			for (String key : range.keySet()) {
//				if(range.get(key) != null) {
//					try {
//						JSONObject node1 = range.getJSONObject(key);
//						String rangeType = node1.getString("type");
//						if(ElasticConstant.EQ.equals(rangeType)) {
//							// String v2 
//							
//						} else if(ElasticConstant.LT_GT.equals(rangeType)) {
//							String begin = node1.getString("begin");
//							String end = node1.getString("end");
//							if(ElasticConstant.VISIT_TIME.equals(key)) {
//								RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("sorting");
//								boolean isValid = false;
//								SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//								if(!StringUtils.isEmpty(begin)) {
//									Date date = simpleDateFormat.parse(begin);
//									rangeQuery = rangeQuery.from(date.getTime());
//									isValid = true;
//								}
//								if(!StringUtils.isEmpty(end)) {
//							        Date date = simpleDateFormat.parse(end);
//									rangeQuery = rangeQuery.to(date.getTime());
//									isValid = true;
//								}
//								if(isValid) {
//									boolQueryBuilder.must(rangeQuery);
//								}
//							} else {
//								RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(key);
//								boolean isValid = false;
//								SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//								if(!StringUtils.isEmpty(begin)) {
//									Date date = simpleDateFormat.parse(begin);
//									rangeQuery = rangeQuery.from(date.getTime());
//									isValid = true;
//								}
//								if(!StringUtils.isEmpty(end)) {
//							        Date date = simpleDateFormat.parse(end);
//									rangeQuery = rangeQuery.to(date.getTime());
//									isValid = true;
//								}
//								if(isValid) {
//									boolQueryBuilder.must(rangeQuery);
//								}
//								// boolQueryBuilder.must(QueryBuilders.rangeQuery(key).from(begin).to(end));
//							}
//						}
//					} catch(Exception e) {
//						// LOG.error("=============range解析错误: ", range);
//					}
//				}
//			}
//		}
//		
//		
//		SearchResponse sr = restHighLevelClient.prepareSearch(index, type)
//							.setQuery(boolQueryBuilder)
//							.setFrom(start)
//							.setSize(pageSize)
//							.addSort(SortBuilders.fieldSort("sorting").order(SortOrder.DESC))
//							.get();
//		
//		SearchHits hits = sr.getHits();
//		List<JSONObject> list = new ArrayList<>();
//		for(SearchHit hit: hits) {
//			list.add(JSONObject.parseObject(hit.getSourceAsString()));
//		}
//		
//		Result<List<JSONObject>> response = new Result<>();
//		if(list != null && !list.isEmpty()) {
//			response.total(hits.getTotalHits());
//			response.data(list);
//		} 
//		
//		return response;
//	}
	
	
}
