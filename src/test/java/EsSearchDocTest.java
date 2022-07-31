import com.macbook.pro.utils.EsClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;

/**
 * @Desc:
 * @Author: PEACEMAKER
 * @Date: 2022/6/21
 */
public class EsSearchDocTest {
    private static final String INDEX = "sms-logs-index";
    private static final String TYPE = "sms-logs-type";
    private static final RestHighLevelClient CLIENT = EsClient.getClient();

    /**
     * 查询之term
     *
     * @throws IOException
     */
    @Test
    public void termTest() throws IOException {
        // 1. 构建查询对象request
        SearchRequest searchRequest = new SearchRequest(INDEX).types(TYPE);
        // 2. 构建查询条件searchSourceBuilder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("province", "北京"));
        // 3. 把查询条件封装到request中
        searchRequest.source(searchSourceBuilder);
        // 4. 执行查询
        SearchResponse searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        // 5. 处理结果
        long totalHits = searchResponse.getHits().getTotalHits();
        System.out.println("总命中数==>" + totalHits);

        System.out.println("===========================");

        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    /**
     * 查询之terms
     *
     * @throws IOException
     */
    @Test
    public void termsSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX).types(TYPE);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termsQuery("province", "北京", "上海"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = CLIENT.search(searchRequest, RequestOptions.DEFAULT);

        System.out.println("总命中数为==>" + response.getHits().getTotalHits());
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsMap());
        }
    }


    /**
     * 查询之match
     *
     * @throws IOException
     */
    @Test
    public void testMatch() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX).types(TYPE);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("smsContent", "李四"));

        searchRequest.source(searchSourceBuilder);

        SearchResponse search = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = search.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    /**
     * 查询之多条件选择operator_match
     * @throws IOException
     */
    @Test
    public void testOperatorMatch() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX).types(TYPE);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("smsContent","中国 健康").operator(Operator.AND));

        searchRequest.source(searchSourceBuilder);

        SearchResponse response = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("命中条数为==>"+response.getHits().getTotalHits());
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    /**
     * 查询之多域查询multi_match
     * @throws IOException
     */
    @Test
    public void testMultiMatch() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX).types(TYPE);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("北京","province","smsContent"));

        searchRequest.source(searchSourceBuilder);

        SearchResponse response = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("命中条数==>"+ response.getHits().getTotalHits());
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsMap());
        }


    }

}
