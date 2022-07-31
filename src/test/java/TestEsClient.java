import com.macbook.pro.utils.EsClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

/**
 * @Desc:
 * @Author: PEACEMAKER
 * @Date: 2022/6/20
 */

public class TestEsClient {
    @Test
    public void  testConnection(){
        EsClient esClient = new EsClient();
        RestHighLevelClient client = esClient.getClient();
        System.out.println(client);
    }
}
