import com.macbook.pro.utils.EsClient;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.junit.Test;

import java.io.IOException;

/**
 * @Desc:
 * @Author: PEACEMAKER
 * @Date: 2022/6/20
 */
public class IndexTest {

    private String index = "emp-index";
    private String type = "emp-type";
    RestHighLevelClient client = EsClient.getClient();

    /**
     * 创建索引并指定mapping
     */
    @Test
    public void testCreateIndex() throws IOException {
        // 构建settings
        Settings settings = Settings.builder()
                .put("number_of_shards", 5)
                .put("number_of_replicas", 1)
                .build();
        //构建mappings信息
        XContentBuilder source = JsonXContent.contentBuilder()
                .startObject()
                .startObject("properties")
                .startObject("id")
                .field("type", "integer")
                .endObject()
                .startObject("empName")
                .field("type", "text")
                .field("store", true)
                .endObject()
                .startObject("salary")
                .field("type", "double")
                .endObject()
                .startObject("hireDate")
                .field("type", "date")
                .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                .field("store", true)
                .endObject()
                .endObject()
                .endObject();

        //创建索引对象
        CreateIndexRequest createIndexRequest = new CreateIndexRequest()
                .index(index)
                .settings(settings)
                .mapping(type, source);

        //创建索引
        CreateIndexResponse response = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     *  判断索引是否存在,删除索引
     */
    @Test
    public void isExistsAndDelete() throws IOException {
        String indexName="emp-index";

        GetIndexRequest getIndexRequest = new GetIndexRequest().indices(indexName);
        // 判断索引是否存在
        boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
        if (exists) {
            DeleteIndexRequest request = new DeleteIndexRequest(indexName);
            // 删除索引
            AcknowledgedResponse ack = client.indices().delete(request, RequestOptions.DEFAULT);
            System.out.println("ack ==>"+ack);
        }
    }

    @Test
    public void getIndex() throws IOException {
        String indexName="emp-index";
        GetIndexRequest indices = new GetIndexRequest().indices(indexName);
        //查询索引,查询不到的话则抛出异常
        GetIndexResponse getIndexResponse = client.indices().get(indices, RequestOptions.DEFAULT);
        System.out.println("response==>"+getIndexResponse);
    }
}
