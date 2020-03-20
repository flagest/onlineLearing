package com.xuecheng.search;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wu on 2020/2/16 0016
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSearch {
    //注入高等级的客户端
    @Autowired
    RestHighLevelClient restHighLevelClient;

    //注入低等级的客户端
    @Autowired
    RestClient restClient;


    @Test
    public void testSerach() throws IOException, ParseException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //指定类型
        searchRequest.types("doc");
        //搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置搜索方式
        //matchAll搜索方式 设置搜索条件
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //设置源字段过滤,第一个参数是结果集包含哪些字段，第二个参数是不包含哪些结果集
        searchSourceBuilder.fetchSource(new String[]{"name", "timestamp", "studymodel", "price"}, new String[]{});
        //向搜索请球对对象设置搜索资源
        searchRequest.source(searchSourceBuilder);
        //执行搜索，向ES发起请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度高的文档
        SearchHit[] hits1 = hits.getHits();
        //日期格式化对象
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (SearchHit documentFields : hits1) {
            //文档主键
            String id = documentFields.getId();
            //源文档类容
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            Date timestamp = simpleDateFormat.parse((String) sourceAsMap.get("timestamp"));
            Double price = (Double) sourceAsMap.get("price");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(timestamp);
            System.out.println(price);
        }
    }

    //分页查询
    @Test
    public void testSerachPage() throws IOException, ParseException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //指定类型
        searchRequest.types("doc");
        //搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置搜索方式
        int from = 1;
        int size = 1;
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);
        //matchAll搜索方式 设置搜索条件
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //设置源字段过滤,第一个参数是结果集包含哪些字段，第二个参数是不包含哪些结果集
        searchSourceBuilder.fetchSource(new String[]{"name", "timestamp", "studymodel", "price"}, new String[]{});
        //向搜索请球对对象设置搜索资源
        searchRequest.source(searchSourceBuilder);
        //执行搜索，向ES发起请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度高的文档
        SearchHit[] hits1 = hits.getHits();
        //日期格式化对象
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (SearchHit documentFields : hits1) {
            //文档主键
            String id = documentFields.getId();
            //源文档类容
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            Date timestamp = simpleDateFormat.parse((String) sourceAsMap.get("timestamp"));
            Double price = (Double) sourceAsMap.get("price");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(timestamp);
            System.out.println(price);
        }
    }

    //根据tremQuery查询
    @Test
    public void testSerachTremQuery() throws IOException, ParseException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //指定类型
        searchRequest.types("doc");
        //搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
      /*  //设置搜索方式 分页搜索
        int from=1;
        int size=1;
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);*/
        //设置id
        String[] ids = new String[]{"1", "2"};
        //termQuery搜索方式 设置搜索条件
        searchSourceBuilder.query(QueryBuilders.termsQuery("_id", ids));
        //设置源字段过滤,第一个参数是结果集包含哪些字段，第二个参数是不包含哪些结果集
        searchSourceBuilder.fetchSource(new String[]{"name", "timestamp", "studymodel", "price"}, new String[]{});
        //向搜索请球对对象设置搜索资源
        searchRequest.source(searchSourceBuilder);
        //执行搜索，向ES发起请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度高的文档
        SearchHit[] hits1 = hits.getHits();
        //日期格式化对象
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (SearchHit documentFields : hits1) {
            //文档主键
            String id = documentFields.getId();
            //源文档类容
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            Date timestamp = simpleDateFormat.parse((String) sourceAsMap.get("timestamp"));
            Double price = (Double) sourceAsMap.get("price");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(timestamp);
            System.out.println(price);
        }
    }

    //根据matchQuery查询
    @Test
    public void testmatchQuery() throws IOException, ParseException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //指定类型
        searchRequest.types("doc");
        //搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置搜索方式
        //termQuery搜索方式 设置搜索条件
        searchSourceBuilder.query(QueryBuilders.matchQuery("description", "spring开发框架")
                .minimumShouldMatch("80%"));
        //设置源字段过滤,第一个参数是结果集包含哪些字段，第二个参数是不包含哪些结果集
        searchSourceBuilder.fetchSource(new String[]{"name", "timestamp", "studymodel", "price"}, new String[]{});
        //向搜索请球对对象设置搜索资源
        searchRequest.source(searchSourceBuilder);
        //执行搜索，向ES发起请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度高的文档
        SearchHit[] hits1 = hits.getHits();
        //日期格式化对象
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (SearchHit documentFields : hits1) {
            //文档主键
            String id = documentFields.getId();
            //源文档类容
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            Date timestamp = simpleDateFormat.parse((String) sourceAsMap.get("timestamp"));
            Double price = (Double) sourceAsMap.get("price");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(timestamp);
            System.out.println(price);
        }
    }

    //根据MultmatchQuery查询
    @Test
    public void testmultiMatchQuery() throws IOException, ParseException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //指定类型
        searchRequest.types("doc");
        //搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置搜索方式
        //termQuery搜索方式 设置搜索条件
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("spring css", "description", "name")
                .minimumShouldMatch("80%")
                .field("name", 10));
        //设置源字段过滤,第一个参数是结果集包含哪些字段，第二个参数是不包含哪些结果集
        searchSourceBuilder.fetchSource(new String[]{"name", "timestamp", "studymodel", "price"}, new String[]{});
        //向搜索请球对对象设置搜索资源
        searchRequest.source(searchSourceBuilder);
        //执行搜索，向ES发起请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度高的文档
        SearchHit[] hits1 = hits.getHits();
        //日期格式化对象
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (SearchHit documentFields : hits1) {
            //文档主键
            String id = documentFields.getId();
            //源文档类容
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            Date timestamp = simpleDateFormat.parse((String) sourceAsMap.get("timestamp"));
            Double price = (Double) sourceAsMap.get("price");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(timestamp);
            System.out.println(price);
        }
    }


    //根据BooleQuery查询
    @Test
    public void testBooleQuery() throws IOException, ParseException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //指定类型
        searchRequest.types("doc");
        //搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置搜索方式
        //BooleQuery
        //multMatchQuery
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("spring css", "description", "name")
                .minimumShouldMatch("80%")
                .field("name", 10);
        //定义个tremQuery
        TermsQueryBuilder studymodelQuery = QueryBuilders.termsQuery("studymodel", "20010");
        //定义一个boolean
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(multiMatchQueryBuilder);
        boolQueryBuilder.must(studymodelQuery);
        //termQuery搜索方式 设置搜索条件
        searchSourceBuilder.query();
        //设置源字段过滤,第一个参数是结果集包含哪些字段，第二个参数是不包含哪些结果集
        searchSourceBuilder.fetchSource(new String[]{"name", "timestamp", "studymodel", "price"}, new String[]{});
        //向搜索请球对对象设置搜索资源
        searchRequest.source(searchSourceBuilder);
        //执行搜索，向ES发起请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度高的文档
        SearchHit[] hits1 = hits.getHits();
        //日期格式化对象
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (SearchHit documentFields : hits1) {
            //文档主键
            String id = documentFields.getId();
            //源文档类容
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            Date timestamp = simpleDateFormat.parse((String) sourceAsMap.get("timestamp"));
            Double price = (Double) sourceAsMap.get("price");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(timestamp);
            System.out.println(price);
        }
    }

    //根据FilterQuery查询
    @Test
    public void testFilterQuery() throws IOException, ParseException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //指定类型
        searchRequest.types("doc");
        //搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置搜索方式
        //BooleQuery
        //multMatchQuery
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("spring css", "description", "name")
                .minimumShouldMatch("80%")
                .field("name", 10);
        //定义个tremQuery
        TermsQueryBuilder studymodelQuery = QueryBuilders.termsQuery("studymodel", "20010");
        //定义一个boolean
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(multiMatchQueryBuilder);
        //定义过滤器
        //价格正在100到400之间
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(100).lte(400));
        boolQueryBuilder.must(studymodelQuery);
        //termQuery搜索方式 设置搜索条件
        searchSourceBuilder.query();
        //设置源字段过滤,第一个参数是结果集包含哪些字段，第二个参数是不包含哪些结果集
        searchSourceBuilder.fetchSource(new String[]{"name", "timestamp", "studymodel", "price"}, new String[]{});
        //向搜索请球对对象设置搜索资源
        searchRequest.source(searchSourceBuilder);
        //执行搜索，向ES发起请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度高的文档
        SearchHit[] hits1 = hits.getHits();
        //日期格式化对象
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (SearchHit documentFields : hits1) {
            //文档主键
            String id = documentFields.getId();
            //源文档类容
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            Date timestamp = simpleDateFormat.parse((String) sourceAsMap.get("timestamp"));
            Double price = (Double) sourceAsMap.get("price");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(timestamp);
            System.out.println(price);
        }
    }

    //根据查询出来的结果进行排序
    @Test
    public void testOrder() throws IOException, ParseException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //指定类型
        searchRequest.types("doc");
        //搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置搜索方式
        //BooleQuery
        //multMatchQuery
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("spring css", "description", "name")
                .minimumShouldMatch("80%")
                .field("name", 10);
        //定义个tremQuery
        TermsQueryBuilder studymodelQuery = QueryBuilders.termsQuery("studymodel", "20010");
        //定义一个boolean
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(multiMatchQueryBuilder);
        //定义过滤器
        //价格正在100到400之间
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(100).lte(400));
        boolQueryBuilder.must(studymodelQuery);
        //termQuery搜索方式 设置搜索条件
        searchSourceBuilder.query();
        //设置源字段过滤,第一个参数是结果集包含哪些字段，第二个参数是不包含哪些结果集
        searchSourceBuilder.fetchSource(new String[]{"name", "timestamp", "studymodel", "price"}, new String[]{});
        //向搜索请球对对象设置搜索资源
        searchRequest.source(searchSourceBuilder);
        //添加排序
        searchSourceBuilder.sort("price", SortOrder.ASC); //价格升序
        searchSourceBuilder.sort("studeymodel", SortOrder.DESC);//学习降序
        //执行搜索，向ES发起请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度高的文档
        SearchHit[] hits1 = hits.getHits();
        //日期格式化对象
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (SearchHit documentFields : hits1) {
            //文档主键
            String id = documentFields.getId();
            //源文档类容
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            Date timestamp = simpleDateFormat.parse((String) sourceAsMap.get("timestamp"));
            Double price = (Double) sourceAsMap.get("price");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(timestamp);
            System.out.println(price);
        }
    }

    //HightNigth
    @Test
    public void testHightNigth() throws IOException, ParseException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //指定类型
        searchRequest.types("doc");
        //搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置搜索方式
        //BooleQuery
        //multMatchQuery
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("开发框架", "description", "name")
                .minimumShouldMatch("80%")
                .field("name", 10);
        //定义个tremQuery
        TermsQueryBuilder studymodelQuery = QueryBuilders.termsQuery("studymodel", "20010");
        //定义一个boolean
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(multiMatchQueryBuilder);
        //定义过滤器
        //价格正在100到400之间
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(100).lte(400));
        boolQueryBuilder.must(studymodelQuery);
        //termQuery搜索方式 设置搜索条件
        searchSourceBuilder.query();
        //设置源字段过滤,第一个参数是结果集包含哪些字段，第二个参数是不包含哪些结果集
        searchSourceBuilder.fetchSource(new String[]{"name", "timestamp", "studymodel", "price"}, new String[]{});
        //设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<tag>");
        highlightBuilder.postTags("</tag>");
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        //将highlight设置进去
        searchSourceBuilder.highlighter(highlightBuilder);
        //向搜索请球对对象设置搜索资源
        searchRequest.source(searchSourceBuilder);
        //添加排序
        searchSourceBuilder.sort("price", SortOrder.ASC); //价格升序
        searchSourceBuilder.sort("studeymodel", SortOrder.DESC);//学习降序
        //执行搜索，向ES发起请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度高的文档
        SearchHit[] hits1 = hits.getHits();
        //日期格式化对象
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (SearchHit documentFields : hits1) {
            //文档主键
            String id = documentFields.getId();
            //源文档类容
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            String name1 = (String) sourceAsMap.get("name");
            //取出高亮
            Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
            if (!highlightFields.isEmpty()) {
                //'取出name高亮字段
                HighlightField highlightField = highlightFields.get("name");
                if (highlightBuilder != null) {
                    Text[] fragments = highlightField.getFragments();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (Text fragment : fragments) {
                        stringBuffer.append(fragment);

                    }
                    name1 = stringBuffer.toString();
                }
            }

            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            Date timestamp = simpleDateFormat.parse((String) sourceAsMap.get("timestamp"));
            Double price = (Double) sourceAsMap.get("price");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(timestamp);
            System.out.println(price);
        }
    }
}
