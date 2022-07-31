package com.macbook.pro.utils;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @Desc: 连接es的高级客户端
 * @Author: PEACEMAKER
 * @Date: 2022/6/20
 */
public class EsClient {

    public static  RestHighLevelClient getClient(){
        // es服务器
        HttpHost host = new HttpHost("121.40.116.118",9200);
        RestClientBuilder clientBuilder = RestClient.builder(host);
        RestHighLevelClient client = new RestHighLevelClient(clientBuilder);

        return client;
    }

}

