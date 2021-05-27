package cn.nj.elasticsearch.manager;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ：zty
 * @date ：Created in 2021/5/26 18:10
 * @description ：
 */
@Component
public class EsManager {

    private final Logger logger= LoggerFactory.getLogger(getClass());

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
            logger.info("创建文档索引自动生成的ID:{}",id);
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
    public  void createDocumentAsync(String indexName, String document){
        //1.文档内容构建
        IndexRequest request = new IndexRequest(indexName);
        //2.文档内容 json格式
        request.source(document,XContentType.JSON);
        //3.指定监听器
        ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                logger.info("异步创建文档成功,id为{}:",indexResponse.getId());
            }

            @Override
            public void onFailure(Exception e) {
                logger.info("异步创建文档失败");
            }
        };
        //4.开始创建
        restHighLevelClient.indexAsync(request,RequestOptions.DEFAULT,listener);

    }

    /**
     * 查询指定文档
     * @param indexName  索引名称
     * @param documentId  文档ID
     */
    public void getDocument(String indexName,String documentId){
        //构建查询请求
        GetRequest request = new GetRequest();
        //索引名称
        request.index(indexName);
        //文档ID
        request.id(documentId);

        //#########下面可选参数###############
        //禁用源检索，在默认情况下启用
        request.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        //










    }







}
