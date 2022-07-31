import com.fasterxml.jackson.databind.ObjectMapper;
import com.macbook.pro.pojo.Employee;
import com.macbook.pro.utils.EsClient;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;

/**
 * @Desc: 批量操作
 * @Author: PEACEMAKER
 * @Date: 2022/6/21
 */
public class BulkTest {

    private static final String INDEX = "emp-index";
    private static final String TYPE = "emp-type";
    private static final RestHighLevelClient CLIENT = EsClient.getClient();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    @Test
    public void bulkAdd() throws IOException {

        BulkRequest bulkRequest = new BulkRequest();

        for (int i = 1001; i < 1200; i++) {
            Employee employee = new Employee();
            employee.setId(i);
            employee.setEmpName("jack"+i);
            employee.setBirth(LocalDate.now());
            employee.setSalary(i+100D);

            String empJson = OBJECT_MAPPER.writeValueAsString(employee);

            IndexRequest indexRequest=new IndexRequest(INDEX,TYPE,i+"");
            indexRequest.source(empJson, XContentType.JSON);

            bulkRequest.add(indexRequest);
        }
        BulkResponse response = CLIENT.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println("response==>"+response);
    }
}
