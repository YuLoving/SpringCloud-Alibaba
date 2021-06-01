package cn.nj.elasticsearch.manager;

import cn.nj.elasticsearch.pojo.Use;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ArrayUtils;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ：zty
 * @date ：Created in 2021/5/26 18:10
 * @description ：
 */
@Component
public class EsManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    private final static String RESULT_NULL = "查询无结果";

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    //+++++++++++++++++++文档索引的使用++++++++++++++++++++++++

    /**
     * 创建文档--同步方式
     *
     * @param indexName 索引名称
     * @param document  要保存的文档内容
     */
    public void createDocument(String indexName, String document) {

        //1.文档内容构建
        IndexRequest request = new IndexRequest(indexName);
        //文档内容 json格式
        request.source(document, XContentType.JSON);
        //####下面都是可选参数
        //文档ID  不传自动生成
        //request.id()

        //路由值
        //request.routing()
        //设置超时时间
        //request.timeout(TimeValue.timeValueSeconds(1))
        //设置超时策略
        //request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL)

        //设置版本
        //request.version()
        //设置版本类型
        //request.versionType(VersionType.EXTERNAL)
        //设置操作类型
        //request.opType(DocWriteRequest.OpType.CREATE)
        //在索引文档之前要执行的接受管道的名称
        //request.setPipeline()

        //2.执行索引文档请求
        try {
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            String id = indexResponse.getId();
            logger.info("创建文档索引自动生成的ID:{}", id);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 创建文档--异步方式
     *
     * @param indexName 索引名称
     * @param document  要保存的文档内容
     */
    public void createDocumentAsync(String indexName, String document) {
        //1.文档内容构建
        IndexRequest request = new IndexRequest(indexName);
        //2.文档内容 json格式
        request.source(document, XContentType.JSON);
        //3.指定监听器
        ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                logger.info("异步创建文档成功,id为{}:", indexResponse.getId());
            }

            @Override
            public void onFailure(Exception e) {
                logger.info("异步创建文档失败");
            }
        };
        //4.开始创建
        restHighLevelClient.indexAsync(request, RequestOptions.DEFAULT, listener);

    }

    /**
     * 查询指定文档---同步方式
     *
     * @param indexName  索引名称
     * @param documentId 文档ID
     */
    public String getDocument(String indexName, String documentId) {
        //构建查询请求
        GetRequest request = new GetRequest();
        //索引名称
        request.index(indexName);
        //文档ID
        request.id(documentId);


        String result = "";
        //开始查询
        try {
            GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
            logger.info("查询的返回值:{}", JSON.toJSONString(response));
            //如果查到结果
            if (response.isExists()) {
                result = response.getSourceAsString();
            } else {
                result = RESULT_NULL;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 通过ID查询指定文档  ---异步方式(由于是异步 ，无法直接返回结果集，只能在onResponse、onFailure方法中实现逻辑)
     *
     * @param indexName  索引名称
     * @param documentId 文档ID
     */
    public void getDocumentAsync(String indexName, String documentId) {

        GetRequest request = new GetRequest();

        request.index(indexName);
        request.id(documentId);

        final String[] result = new String[1];
        //配置监听器
        ActionListener<GetResponse> listener = new ActionListener<GetResponse>() {
            @Override
            public void onResponse(GetResponse getResponse) {
                logger.info("异步查询成功:{}", JSON.toJSONString(getResponse));
                if (getResponse.isExists()) {
                    result[0] = getResponse.getSourceAsString();
                } else {
                    result[0] = RESULT_NULL;
                }
                logger.info("result[0]:{}", result[0]);
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                logger.info("异步查询失败");
            }
        };

        //执行异步查询
        restHighLevelClient.getAsync(request, RequestOptions.DEFAULT, listener);
    }


    /**
     * 判断文档是否存在---同步方式
     *
     * @param indexName  索引名称
     * @param documentId 文档ID
     * @return 存在 true   不存在 false
     */
    public boolean existDocument(String indexName, String documentId) {
        //构建get请求
        GetRequest getRequest = new GetRequest(indexName, documentId);
        //因为只需要查询是否存在文档   因此建议 关闭提取源 和任何存储字段 使请求的构建是轻量级别的
        //关闭提取源
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        //禁用提取存储字段
        getRequest.storedFields("_none_");

        try {
            return restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            logger.info("查询文档是否存在失败:{}", e.getCause().getMessage());
            throw new RuntimeException("查询文档是否存在失败");
        }

    }

    /**
     * 查询指定文档是否存在---异步方式
     *
     * @param indexName  索引名称
     * @param documentId 文档ID
     */
    public void existDocumentAsync(String indexName, String documentId) {

        //构建get请求
        GetRequest getRequest = new GetRequest(indexName, documentId);
        //因为只需要查询是否存在文档   因此建议 关闭提取源 和任何存储字段 使请求的构建是轻量级别的
        //关闭提取源
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        //禁用存储字段
        getRequest.storedFields("_none_");


        //设置监听器
        ActionListener<Boolean> listener = new ActionListener<Boolean>() {

            @Override
            public void onResponse(Boolean aBoolean) {
                logger.info("查询是否存在:{}", aBoolean);
            }

            @Override
            public void onFailure(Exception e) {
                logger.info("查询是否存在报错:{}", e.getMessage());
            }
        };

        //查询
        restHighLevelClient.existsAsync(getRequest, RequestOptions.DEFAULT, listener);
    }


    /**
     * 删除指定的文档---同步方式
     *
     * @param indexName  索引名称
     * @param documentId 文档ID
     */
    public void deleteDocument(String indexName, String documentId) {
        //构建删除请求
        DeleteRequest deleteRequest = new DeleteRequest(indexName, documentId);
        try {
            DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            logger.info("删除结果:{}", JSON.toJSONString(delete));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("删除失败");
        }

    }


    /**
     * 删除指定的文档---异步方式
     *
     * @param indexName  索引名称
     * @param documentId 文档ID
     */
    public void deleteDocumentAsync(String indexName, String documentId) {
        //构建删除请求
        DeleteRequest deleteRequest = new DeleteRequest(indexName, documentId);

        //2.创建监听器
        ActionListener<DeleteResponse> listener = new ActionListener<DeleteResponse>() {
            @Override
            public void onResponse(DeleteResponse deleteResponse) {
                logger.info("删除结果异步通知:{}", JSON.toJSONString(deleteResponse));
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                logger.info("异步删除失败");
            }
        };

        //3.异步删除
        restHighLevelClient.deleteAsync(deleteRequest, RequestOptions.DEFAULT, listener);

    }


    /**
     * 更新指定文档
     *
     * @param indexName  索引名称
     * @param documentId 文档ID
     */
    public void updateDocument(String indexName, String documentId) {
        //构建更新请求
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(indexName);
        updateRequest.id(documentId);
        //更新的内容
        Use use = new Use("麒麟123", 21);

        updateRequest.doc(use.toString(), XContentType.JSON);
        try {
            UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            logger.info("更新文档结果:{}", JSON.toJSONString(update));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("更新文档失败");
        }


    }


    /**
     * 更新指定文档---异步方式
     *
     * @param indexName  索引名称
     * @param documentId 文档ID
     */
    public void updateDocumentAsync(String indexName, String documentId) {
        //构建更新请求
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(indexName);
        updateRequest.id(documentId);
        //更新的内容
        Use use = new Use(1006L, "火漆", 21);
        updateRequest.doc(use.toString(), XContentType.JSON);
        //构建监听器
        ActionListener<UpdateResponse> actionListener = new ActionListener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse updateResponse) {
                logger.info("异步更新文档结果通知:{}", JSON.toJSONString(updateResponse));
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                logger.info("异步更新文档失败");
            }
        };

        // 执行异步更新
        restHighLevelClient.updateAsync(updateRequest, RequestOptions.DEFAULT, actionListener);
    }


    /**
     * 解析词向量请求
     *
     * @param indexName  索引名称
     * @param documentId 文档ID
     * @param field      检索信息的字段
     * @return str
     */
    public String termVector(String indexName, String documentId, String field) {

        //构建词向量请求
        TermVectorsRequest termVectorsRequest = new TermVectorsRequest(indexName, documentId);
        termVectorsRequest.setFields(field);

        try {
            TermVectorsResponse vectorsResponse = restHighLevelClient.termvectors(termVectorsRequest, RequestOptions.DEFAULT);
            return JSON.toJSONString(vectorsResponse);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解析词向量请求失败");
        }

    }


    //################################文档的批量操作##########################################################################

    /**
     * 批量添加索引文档
     *
     * @param indexName 索引名称
     * @param lists     文档集合
     */
    public void bulkCreateIndex(String indexName, List<Use> lists) {

        //1.创建批量构造器
        BulkRequest bulkRequest = new BulkRequest();

        //2.遍历需要生成的文档
        lists.forEach(a -> {
            IndexRequest indexRequest = new IndexRequest();
            indexRequest.index(indexName);
            indexRequest.source(a.toString(), XContentType.JSON);
            bulkRequest.add(indexRequest);
        });

        logger.info("bulkRequest:{}", JSON.toJSONString(bulkRequest));
        //3.构建请求
        try {
            BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            //判断是否全部创建成功
            logger.info("批量创建结果集:{}", JSON.toJSONString(bulk));
            if (null == bulk) {
                throw new RuntimeException("bulk为null,批量创建失败");
            }
            for (BulkItemResponse itemResponse : bulk) {
                //文档写入响应response
                DocWriteResponse docWriteResponse = itemResponse.getResponse();

                //索引状态
                switch (itemResponse.getOpType()) {
                    case INDEX:
                        IndexResponse indexResponse = (IndexResponse) docWriteResponse;
                        logger.info("INDEX创建日志:{}", JSON.toJSONString(indexResponse));
                        break;
                    case CREATE:
                        IndexResponse response = (IndexResponse) docWriteResponse;
                        logger.info("CREATE创建日志:{}", JSON.toJSONString(response));
                        break;
                    case UPDATE:
                        UpdateResponse updateResponse = (UpdateResponse) docWriteResponse;
                        logger.info("UPDATE更新日志:{}", JSON.toJSONString(updateResponse));
                        break;
                    case DELETE:
                        DeleteResponse deleteResponse = (DeleteResponse) docWriteResponse;
                        logger.info("DELETE删除日志:{}", JSON.toJSONString(deleteResponse));
                        break;
                    default:
                        logger.info("默认直接打印日志:{}", JSON.toJSONString(docWriteResponse));
                }
            }

        } catch (Exception e) {
            logger.info("批量创建失败");
            e.printStackTrace();
        }


    }

    /**
     * 利用批量处理器bulkProcessor------该行为并不友好  并且暂时存在问题
     *
     * @param indexName 索引名称
     * @param list      文档集合
     */
    public void bulkProcessor(String indexName, List<Use> list) {

        //设置BulkProcessor监听
        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                //批量处理前的动作
                int numberOfActions = request.numberOfActions();
                logger.info("execution bulk:{},请求数:{},请求参数:{}",executionId,numberOfActions,JSON.toJSONString(request));
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                //批量处理后的动作

                if(response.hasFailures()){
                    logger.info("bulk存在失败 executionId:{},response:{}",executionId,JSON.toJSONString(response));
                }else{
                    logger.info("成功耗时:{}",response.getTook().getMillis());
                    logger.info("bulk成功 executionId:{},response:{}",executionId,JSON.toJSONString(response));
                }

            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                //批量处理出现异常动作
                logger.error("execute is error:",failure);
            }
        };

        //创建请求
        BulkProcessor bulkProcessor = BulkProcessor.builder((request, bulkListener) ->
                restHighLevelClient.bulkAsync(request, RequestOptions.DEFAULT, bulkListener), listener
        ).build();

        //添加索引
        list.forEach(a->{
            IndexRequest indexRequest = new IndexRequest(indexName);
            indexRequest.source(a.toString(),XContentType.JSON);
            bulkProcessor.add(indexRequest);
        });

    }

    /**
     * 批量获取指定集合的文档
     * @param indexName 索引
     * @param ids  文档ID集合
     * @return 结果集
     */
    public String multiGet(String indexName,List<String> ids){
        List<String> list = new ArrayList<>();

        //构建MultiGet请求
        MultiGetRequest multiGetRequest = new MultiGetRequest();
        //遍历文档ID集合 放到请求中
        ids.forEach(a->{
            multiGetRequest.add(new MultiGetRequest.Item(indexName,a));
        });

        //执行批量查询
        try {
            MultiGetResponse multiGetResponse = restHighLevelClient.mget(multiGetRequest, RequestOptions.DEFAULT);
            //解析结果集
            MultiGetItemResponse[] responses = multiGetResponse.getResponses();
            if(ArrayUtils.isEmpty(responses)){
                throw new RuntimeException("查询不到结果");
            }
            logger.info("multiGet查询的个数为:{}",responses.length);
            logger.info("multiGet查询的结果集:{}",JSON.toJSONString(responses));
            for (MultiGetItemResponse getItemResponse:responses) {
                GetResponse response = getItemResponse.getResponse();
                //如果存在则放入list
                if(response.isExists()){
                    list.add(response.getSourceAsString());
                }
            }
            if(CollectionUtils.isEmpty(list)){
                throw new RuntimeException("查询结果为空");
            }
            return JSON.toJSONString(list);
        } catch (IOException e) {
            throw new RuntimeException("批量查询multiGet失败:"+e.getMessage());
        }
    }


    /**
     * 异步批量查询指定的文档集合
     * @param indexName  索引名称
     * @param ids  文档ID集合
     */
    public  void  multiGetAsync(String indexName,List<String> ids){
        
        //构建multiGet请求
        MultiGetRequest multiGetRequest = new MultiGetRequest();
        //构建监听器
        ActionListener<MultiGetResponse> listener = new ActionListener<MultiGetResponse>() {
            @Override
            public void onResponse(MultiGetResponse multiGetItemResponses) {
                logger.info("查询结果:{}",JSON.toJSONString(multiGetItemResponses));
                MultiGetItemResponse[] responses = multiGetItemResponses.getResponses();
                if(ArrayUtils.isEmpty(responses)){
                    logger.info("查询结果为空");
                }
                List<Object> list = new ArrayList<>();
                for (MultiGetItemResponse itemResponse:responses) {
                    GetResponse response = itemResponse.getResponse();
                    if(response.isExists()){
                        list.add(response.getSourceAsString());
                    }else {
                        logger.info("文档ID为"+response.getId()+"没有找到对应文档");
                    }
                }
                logger.info("最后的查询结果集合:{}",JSON.toJSONString(list));
            }

            @Override
            public void onFailure(Exception e) {
                logger.info("异步查询报错:"+e.getMessage());
            }
        };
        
        //遍历组装文档ID集合
        ids.forEach(a->{
            multiGetRequest.add(new MultiGetRequest.Item(indexName,a));
        });
        //异步去请求
        restHighLevelClient.mgetAsync(multiGetRequest,RequestOptions.DEFAULT,listener);
    }


}
