package cn.nj.elasticsearch.manager;

import cn.nj.elasticsearch.pojo.Use;
import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
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

    private final Logger logger = LoggerFactory.getLogger(getClass());


    private final static  String  RESULT_NULL="查询无结果";

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
            if(response.isExists()){
                result = response.getSourceAsString();
            }else {
                result=RESULT_NULL;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 通过ID查询指定文档  ---异步方式(由于是异步 ，无法直接返回结果集，只能在onResponse、onFailure方法中实现逻辑)
     * @param indexName  索引名称
     * @param documentId 文档ID
     */
    public  void    getDocumentAsync(String indexName ,String documentId){

        GetRequest request = new GetRequest();

        request.index(indexName);
        request.id(documentId);

        final String[] result = new String[1];
        //配置监听器
        ActionListener<GetResponse> listener = new ActionListener<GetResponse>() {
            @Override
            public void onResponse(GetResponse getResponse) {
                logger.info("异步查询成功:{}",JSON.toJSONString(getResponse));
                if(getResponse.isExists()){
                    result[0] =getResponse.getSourceAsString();
                }else {
                    result[0] =RESULT_NULL;
                }
                logger.info("result[0]:{}",result[0]);
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                logger.info("异步查询失败");
            }
        };

        //执行异步查询
        restHighLevelClient.getAsync(request,RequestOptions.DEFAULT,listener);
    }


    /**
     * 判断文档是否存在---同步方式
     * @param indexName  索引名称
     * @param documentId  文档ID
     * @return 存在 true   不存在 false
     */
    public boolean existDocument(String indexName,String documentId){
        //构建get请求
        GetRequest getRequest = new GetRequest(indexName, documentId);
        //因为只需要查询是否存在文档   因此建议 关闭提取源 和任何存储字段 使请求的构建是轻量级别的
        //关闭提取源
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        //禁用提取存储字段
        getRequest.storedFields("_none_");

        try {
            return restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        }catch (Exception e){
            logger.info("查询文档是否存在失败:{}",e.getCause().getMessage());
            throw new RuntimeException("查询文档是否存在失败");
        }

    }

    /**
     * 查询指定文档是否存在---异步方式
     * @param indexName  索引名称
     * @param documentId  文档ID
     */
    public void existDocumentAsync(String indexName,String documentId){

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
                logger.info("查询是否存在:{}",aBoolean);
            }

            @Override
            public void onFailure(Exception e) {
                logger.info("查询是否存在报错:{}",e.getMessage());
            }
        };

        //查询
        restHighLevelClient.existsAsync(getRequest,RequestOptions.DEFAULT,listener);
    }


    /**
     * 删除指定的文档---同步方式
     * @param indexName 索引名称
     * @param documentId  文档ID
     */
    public  void  deleteDocument(String indexName,String documentId){
        //构建删除请求
        DeleteRequest deleteRequest = new DeleteRequest(indexName, documentId);
        try {
            DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            logger.info("删除结果:{}",JSON.toJSONString(delete));
        }catch (Exception e){
            e.printStackTrace();
            logger.info("删除失败");
        }

    }


    /**
     * 删除指定的文档---异步方式
     * @param indexName 索引名称
     * @param documentId  文档ID
     */
    public  void  deleteDocumentAsync(String indexName,String documentId){
        //构建删除请求
        DeleteRequest deleteRequest = new DeleteRequest(indexName, documentId);

        //2.创建监听器
        ActionListener<DeleteResponse> listener = new ActionListener<DeleteResponse>() {
            @Override
            public void onResponse(DeleteResponse deleteResponse) {
            logger.info("删除结果异步通知:{}",JSON.toJSONString(deleteResponse));
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                logger.info("异步删除失败");
            }
        };

        //3.异步删除
        restHighLevelClient.deleteAsync(deleteRequest,RequestOptions.DEFAULT,listener);

    }








    /**
     * 更新指定文档
     * @param indexName  索引名称
     * @param documentId  文档ID
     */
    public void updateDocument(String indexName, String documentId){
        //构建更新请求
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(indexName);
        updateRequest.id(documentId);
        //更新的内容
        Use use = new Use("麒麟123",21);

        updateRequest.doc(use.toString(),XContentType.JSON);
        try {
            UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            logger.info("更新文档结果:{}",JSON.toJSONString(update));
        }catch (Exception e){
            e.printStackTrace();
            logger.info("更新文档失败");
        }


    }


    /**
     * 更新指定文档---异步方式
     * @param indexName  索引名称
     * @param documentId  文档ID
     */
    public void updateDocumentAsync(String indexName,String documentId){
        //构建更新请求
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(indexName);
        updateRequest.id(documentId);
        //更新的内容
        Use use = new Use(1006L,"火漆",21);
        updateRequest.doc(use.toString(),XContentType.JSON);
        //构建监听器
        ActionListener<UpdateResponse> actionListener = new ActionListener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse updateResponse) {
                logger.info("异步更新文档结果通知:{}",JSON.toJSONString(updateResponse));
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                logger.info("异步更新文档失败");
            }
        };

        // 执行异步更新
        restHighLevelClient.updateAsync(updateRequest,RequestOptions.DEFAULT,actionListener);
    }





































}
