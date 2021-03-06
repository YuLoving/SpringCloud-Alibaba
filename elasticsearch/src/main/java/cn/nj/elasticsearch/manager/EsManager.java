package cn.nj.elasticsearch.manager;

import cn.nj.elasticsearch.pojo.Use;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.fieldcaps.FieldCapabilities;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesRequest;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ???zty
 * @date ???Created in 2021/5/26 18:10
 * @description ???
 */
@Component
public class EsManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    private final static String RESULT_NULL = "???????????????";

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    //+++++++++++++++++++?????????????????????++++++++++++++++++++++++

    /**
     * ????????????--????????????
     *
     * @param indexName ????????????
     * @param document  ????????????????????????
     */
    public void createDocument(String indexName, String document) {

        //1.??????????????????
        IndexRequest request = new IndexRequest(indexName);
        //???????????? json??????
        request.source(document, XContentType.JSON);
        //####????????????????????????
        //??????ID  ??????????????????
        //request.id()

        //?????????
        //request.routing()
        //??????????????????
        //request.timeout(TimeValue.timeValueSeconds(1))
        //??????????????????
        //request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL)

        //????????????
        //request.version()
        //??????????????????
        //request.versionType(VersionType.EXTERNAL)
        //??????????????????
        //request.opType(DocWriteRequest.OpType.CREATE)
        //??????????????????????????????????????????????????????
        //request.setPipeline()

        //2.????????????????????????
        try {
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            String id = indexResponse.getId();
            logger.info("?????????????????????????????????ID:{}", id);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * ????????????--????????????
     *
     * @param indexName ????????????
     * @param document  ????????????????????????
     */
    public void createDocumentAsync(String indexName, String document) {
        //1.??????????????????
        IndexRequest request = new IndexRequest(indexName);
        //2.???????????? json??????
        request.source(document, XContentType.JSON);
        //3.???????????????
        ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                logger.info("????????????????????????,id???{}:", indexResponse.getId());
            }

            @Override
            public void onFailure(Exception e) {
                logger.info("????????????????????????");
            }
        };
        //4.????????????
        restHighLevelClient.indexAsync(request, RequestOptions.DEFAULT, listener);

    }

    /**
     * ??????????????????---????????????
     *
     * @param indexName  ????????????
     * @param documentId ??????ID
     */
    public String getDocument(String indexName, String documentId) {
        //??????????????????
        GetRequest request = new GetRequest();
        //????????????
        request.index(indexName);
        //??????ID
        request.id(documentId);


        String result = "";
        //????????????
        try {
            GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
            logger.info("??????????????????:{}", JSON.toJSONString(response));
            //??????????????????
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
     * ??????ID??????????????????  ---????????????(??????????????? ??????????????????????????????????????????onResponse???onFailure?????????????????????)
     *
     * @param indexName  ????????????
     * @param documentId ??????ID
     */
    public void getDocumentAsync(String indexName, String documentId) {

        GetRequest request = new GetRequest();

        request.index(indexName);
        request.id(documentId);

        final String[] result = new String[1];
        //???????????????
        ActionListener<GetResponse> listener = new ActionListener<GetResponse>() {
            @Override
            public void onResponse(GetResponse getResponse) {
                logger.info("??????????????????:{}", JSON.toJSONString(getResponse));
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
                logger.info("??????????????????");
            }
        };

        //??????????????????
        restHighLevelClient.getAsync(request, RequestOptions.DEFAULT, listener);
    }


    /**
     * ????????????????????????---????????????
     *
     * @param indexName  ????????????
     * @param documentId ??????ID
     * @return ?????? true   ????????? false
     */
    public boolean existDocument(String indexName, String documentId) {
        //??????get??????
        GetRequest getRequest = new GetRequest(indexName, documentId);
        //???????????????????????????????????????   ???????????? ??????????????? ????????????????????? ????????????????????????????????????
        //???????????????
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        //????????????????????????
        getRequest.storedFields("_none_");

        try {
            return restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            logger.info("??????????????????????????????:{}", e.getCause().getMessage());
            throw new RuntimeException("??????????????????????????????");
        }

    }

    /**
     * ??????????????????????????????---????????????
     *
     * @param indexName  ????????????
     * @param documentId ??????ID
     */
    public void existDocumentAsync(String indexName, String documentId) {

        //??????get??????
        GetRequest getRequest = new GetRequest(indexName, documentId);
        //???????????????????????????????????????   ???????????? ??????????????? ????????????????????? ????????????????????????????????????
        //???????????????
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        //??????????????????
        getRequest.storedFields("_none_");


        //???????????????
        ActionListener<Boolean> listener = new ActionListener<Boolean>() {

            @Override
            public void onResponse(Boolean aBoolean) {
                logger.info("??????????????????:{}", aBoolean);
            }

            @Override
            public void onFailure(Exception e) {
                logger.info("????????????????????????:{}", e.getMessage());
            }
        };

        //??????
        restHighLevelClient.existsAsync(getRequest, RequestOptions.DEFAULT, listener);
    }


    /**
     * ?????????????????????---????????????
     *
     * @param indexName  ????????????
     * @param documentId ??????ID
     */
    public void deleteDocument(String indexName, String documentId) {
        //??????????????????
        DeleteRequest deleteRequest = new DeleteRequest(indexName, documentId);
        try {
            DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            logger.info("????????????:{}", JSON.toJSONString(delete));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("????????????");
        }

    }


    /**
     * ?????????????????????---????????????
     *
     * @param indexName  ????????????
     * @param documentId ??????ID
     */
    public void deleteDocumentAsync(String indexName, String documentId) {
        //??????????????????
        DeleteRequest deleteRequest = new DeleteRequest(indexName, documentId);

        //2.???????????????
        ActionListener<DeleteResponse> listener = new ActionListener<DeleteResponse>() {
            @Override
            public void onResponse(DeleteResponse deleteResponse) {
                logger.info("????????????????????????:{}", JSON.toJSONString(deleteResponse));
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                logger.info("??????????????????");
            }
        };

        //3.????????????
        restHighLevelClient.deleteAsync(deleteRequest, RequestOptions.DEFAULT, listener);

    }


    /**
     * ??????????????????
     *
     * @param indexName  ????????????
     * @param documentId ??????ID
     */
    public void updateDocument(String indexName, String documentId) {
        //??????????????????
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(indexName);
        updateRequest.id(documentId);
        //???????????????
        Use use = new Use("??????123", 21);

        updateRequest.doc(use.toString(), XContentType.JSON);
        try {
            UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            logger.info("??????????????????:{}", JSON.toJSONString(update));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("??????????????????");
        }


    }


    /**
     * ??????????????????---????????????
     *
     * @param indexName  ????????????
     * @param documentId ??????ID
     */
    public void updateDocumentAsync(String indexName, String documentId) {
        //??????????????????
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(indexName);
        updateRequest.id(documentId);
        //???????????????
        Use use = new Use(1006L, "??????", 21);
        updateRequest.doc(use.toString(), XContentType.JSON);
        //???????????????
        ActionListener<UpdateResponse> actionListener = new ActionListener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse updateResponse) {
                logger.info("??????????????????????????????:{}", JSON.toJSONString(updateResponse));
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                logger.info("????????????????????????");
            }
        };

        // ??????????????????
        restHighLevelClient.updateAsync(updateRequest, RequestOptions.DEFAULT, actionListener);
    }


    /**
     * ?????????????????????
     *
     * @param indexName  ????????????
     * @param documentId ??????ID
     * @param field      ?????????????????????
     * @return str
     */
    public String termVector(String indexName, String documentId, String field) {

        //?????????????????????
        TermVectorsRequest termVectorsRequest = new TermVectorsRequest(indexName, documentId);
        termVectorsRequest.setFields(field);

        try {
            TermVectorsResponse vectorsResponse = restHighLevelClient.termvectors(termVectorsRequest, RequestOptions.DEFAULT);
            return JSON.toJSONString(vectorsResponse);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("???????????????????????????");
        }

    }


    //################################?????????????????????##########################################################################

    /**
     * ????????????????????????
     *
     * @param indexName ????????????
     * @param lists     ????????????
     */
    public void bulkCreateIndex(String indexName, List<Use> lists) {

        //1.?????????????????????
        BulkRequest bulkRequest = new BulkRequest();

        //2.???????????????????????????
        lists.forEach(a -> {
            IndexRequest indexRequest = new IndexRequest();
            indexRequest.index(indexName);
            indexRequest.source(a.toString(), XContentType.JSON);
            bulkRequest.add(indexRequest);
        });

        logger.info("bulkRequest:{}", JSON.toJSONString(bulkRequest));
        //3.????????????
        try {
            BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            //??????????????????????????????
            logger.info("?????????????????????:{}", JSON.toJSONString(bulk));
            if (null == bulk) {
                throw new RuntimeException("bulk???null,??????????????????");
            }
            for (BulkItemResponse itemResponse : bulk) {
                //??????????????????response
                DocWriteResponse docWriteResponse = itemResponse.getResponse();

                //????????????
                switch (itemResponse.getOpType()) {
                    case INDEX:
                        IndexResponse indexResponse = (IndexResponse) docWriteResponse;
                        logger.info("INDEX????????????:{}", JSON.toJSONString(indexResponse));
                        break;
                    case CREATE:
                        IndexResponse response = (IndexResponse) docWriteResponse;
                        logger.info("CREATE????????????:{}", JSON.toJSONString(response));
                        break;
                    case UPDATE:
                        UpdateResponse updateResponse = (UpdateResponse) docWriteResponse;
                        logger.info("UPDATE????????????:{}", JSON.toJSONString(updateResponse));
                        break;
                    case DELETE:
                        DeleteResponse deleteResponse = (DeleteResponse) docWriteResponse;
                        logger.info("DELETE????????????:{}", JSON.toJSONString(deleteResponse));
                        break;
                    default:
                        logger.info("????????????????????????:{}", JSON.toJSONString(docWriteResponse));
                }
            }

        } catch (Exception e) {
            logger.info("??????????????????");
            e.printStackTrace();
        }


    }

    /**
     * ?????????????????????bulkProcessor------?????????????????????  ????????????????????????
     *
     * @param indexName ????????????
     * @param list      ????????????
     */
    public void bulkProcessor(String indexName, List<Use> list) {

        //??????BulkProcessor??????
        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                //????????????????????????
                int numberOfActions = request.numberOfActions();
                logger.info("execution bulk:{},?????????:{},????????????:{}", executionId, numberOfActions, JSON.toJSONString(request));
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                //????????????????????????

                if (response.hasFailures()) {
                    logger.info("bulk???????????? executionId:{},response:{}", executionId, JSON.toJSONString(response));
                } else {
                    logger.info("????????????:{}", response.getTook().getMillis());
                    logger.info("bulk?????? executionId:{},response:{}", executionId, JSON.toJSONString(response));
                }

            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                //??????????????????????????????
                logger.error("execute is error:", failure);
            }
        };

        //????????????
        BulkProcessor bulkProcessor = BulkProcessor.builder((request, bulkListener) ->
                restHighLevelClient.bulkAsync(request, RequestOptions.DEFAULT, bulkListener), listener
        ).build();

        //????????????
        list.forEach(a -> {
            IndexRequest indexRequest = new IndexRequest(indexName);
            indexRequest.source(a.toString(), XContentType.JSON);
            bulkProcessor.add(indexRequest);
        });

    }

    /**
     * ?????????????????????????????????
     *
     * @param indexName ??????
     * @param ids       ??????ID??????
     * @return ?????????
     */
    public String multiGet(String indexName, List<String> ids) {
        List<String> list = new ArrayList<>();

        //??????MultiGet??????
        MultiGetRequest multiGetRequest = new MultiGetRequest();
        //????????????ID?????? ???????????????
        ids.forEach(a -> {
            multiGetRequest.add(new MultiGetRequest.Item(indexName, a));
        });

        //??????????????????
        try {
            MultiGetResponse multiGetResponse = restHighLevelClient.mget(multiGetRequest, RequestOptions.DEFAULT);
            //???????????????
            MultiGetItemResponse[] responses = multiGetResponse.getResponses();
            if (ArrayUtils.isEmpty(responses)) {
                throw new RuntimeException("??????????????????");
            }
            logger.info("multiGet??????????????????:{}", responses.length);
            logger.info("multiGet??????????????????:{}", JSON.toJSONString(responses));
            for (MultiGetItemResponse getItemResponse : responses) {
                GetResponse response = getItemResponse.getResponse();
                //?????????????????????list
                if (response.isExists()) {
                    list.add(response.getSourceAsString());
                }
            }
            if (CollectionUtils.isEmpty(list)) {
                throw new RuntimeException("??????????????????");
            }
            return JSON.toJSONString(list);
        } catch (IOException e) {
            throw new RuntimeException("????????????multiGet??????:" + e.getMessage());
        }
    }


    /**
     * ???????????????????????????????????????
     *
     * @param indexName ????????????
     * @param ids       ??????ID??????
     */
    public void multiGetAsync(String indexName, List<String> ids) {

        //??????multiGet??????
        MultiGetRequest multiGetRequest = new MultiGetRequest();
        //???????????????
        ActionListener<MultiGetResponse> listener = new ActionListener<MultiGetResponse>() {
            @Override
            public void onResponse(MultiGetResponse multiGetItemResponses) {
                logger.info("????????????:{}", JSON.toJSONString(multiGetItemResponses));
                MultiGetItemResponse[] responses = multiGetItemResponses.getResponses();
                if (ArrayUtils.isEmpty(responses)) {
                    logger.info("??????????????????");
                }
                List<Object> list = new ArrayList<>();
                for (MultiGetItemResponse itemResponse : responses) {
                    GetResponse response = itemResponse.getResponse();
                    if (response.isExists()) {
                        list.add(response.getSourceAsString());
                    } else {
                        logger.info("??????ID???" + response.getId() + "????????????????????????");
                    }
                }
                logger.info("???????????????????????????:{}", JSON.toJSONString(list));
            }

            @Override
            public void onFailure(Exception e) {
                logger.info("??????????????????:" + e.getMessage());
            }
        };

        //??????????????????ID??????
        ids.forEach(a -> {
            multiGetRequest.add(new MultiGetRequest.Item(indexName, a));
        });
        //???????????????
        restHighLevelClient.mgetAsync(multiGetRequest, RequestOptions.DEFAULT, listener);
    }


    /**
     * ???fromIndex ????????????????????????toIndex?????????
     *
     * @param fromIndex ?????????
     * @param toIndex   ????????????
     */
    public void reIndex(String fromIndex, String toIndex) {

        //??????reIndex??????
        ReindexRequest reindexRequest = new ReindexRequest();
        //??????????????????????????????
        reindexRequest.setSourceIndices(fromIndex);
        //??????????????????
        reindexRequest.setDestIndex(toIndex);

        //??????????????????
        try {
            BulkByScrollResponse bulkByScrollResponse = restHighLevelClient.reindex(reindexRequest, RequestOptions.DEFAULT);
            //??????bulkByScrollResponse
            logger.info("?????????????????????:{}", JSON.toJSONString(bulkByScrollResponse));
            if (null == bulkByScrollResponse) {
                logger.error("bulkByScrollResponse is null");
            }
            logger.info("?????????:{}", bulkByScrollResponse.getTook().getMillis());
            logger.info("??????????????????:{}", bulkByScrollResponse.isTimedOut());
            logger.info("??????????????????????????????:{}", bulkByScrollResponse.getTotal());
            logger.info("????????????????????????:{}", bulkByScrollResponse.getUpdated());
            logger.info("????????????????????????:{}", bulkByScrollResponse.getCreated());
            logger.info("????????????????????????:{}", bulkByScrollResponse.getDeleted());
            logger.info("?????????????????????:{}", bulkByScrollResponse.getBatches());
            logger.info("??????????????????:{}", bulkByScrollResponse.getNoops());
            logger.info("???????????????:{}", bulkByScrollResponse.getVersionConflicts());
            logger.info("?????????????????????????????????:{}", bulkByScrollResponse.getBulkRetries());
            logger.info("???????????????????????????:{}", bulkByScrollResponse.getSearchRetries());
            logger.info("????????????????????????,????????????????????????????????????????????????:{}", bulkByScrollResponse.getStatus().getThrottled());
            logger.info("??????????????????:{}", bulkByScrollResponse.getSearchFailures().size());
            logger.info("????????????????????????:{}", bulkByScrollResponse.getBulkFailures().size());

        } catch (IOException e) {
            logger.error("ReIndex??????");
            e.printStackTrace();

        }
    }

    //##############################################search ??????#############################################################

    /**
     * ?????????????????????
     *
     * @param field ??????
     * @param value ???
     * @return ??????
     */
    public String search(String field, String value) {

        List<String> list = new ArrayList<>();
        //??????searchRequest??????
        SearchRequest searchRequest = new SearchRequest();

        //searchRequest?????? searchSourceBuilder  ????????????????????????????????? searchSourceBuilder??? ???????????????????????????????????????????????????setter

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //???searchSourceBuilder??? ??????????????????????????????
        //searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //???searchSourceBuilder ????????? searchRequest
        //  searchRequest.source(searchSourceBuilder);

        //??????????????????(??????????????????)
        searchSourceBuilder.query(QueryBuilders.matchQuery(field, value));
        //???????????????????????????????????????  ?????????0
        searchSourceBuilder.from(0);
        //???????????????????????????????????????????????????????????????
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));


        //???searchSourceBuilder ????????? searchRequest
        // searchRequest.source(searchSourceBuilder);

        //????????????????????????
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //????????????????????????????????????
        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field(field);
        //???????????????????????????({@code unified}, {@code plain} and {@code fvh}.)  ????????????unified
        highlightTitle.highlighterType("unified");
        //???highlightTitle?????? highlightBuilder???
        highlightBuilder.field(highlightTitle);
        //?????? searchSourceBuilder
        searchSourceBuilder.highlighter(highlightBuilder);
        //???searchSourceBuilder ????????? searchRequest
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            logger.info("searchResponse ??????:{}", JSON.toJSONString(searchResponse));
            if (null == searchResponse) {
                logger.info("??????????????????");
            }
            logger.info("searchResponse status is:{}", searchResponse.status());
            logger.info("searchResponse ????????????:{}", searchResponse.getTook().getMillis());
            logger.info("searchResponse ???????????????????????????:{}", searchResponse.getTotalShards());
            logger.info("searchResponse ???????????????????????????:{}", searchResponse.getSuccessfulShards());
            logger.info("searchResponse ???????????????????????????:{}", searchResponse.getFailedShards());
            for (ShardSearchFailure failure : searchResponse.getShardFailures()) {
                logger.info("fail is:{}", JSON.toJSONString(failure.toString()));
            }

            //????????????
            SearchHits hits = searchResponse.getHits();
            logger.info("????????????:{}", hits.getTotalHits().value);
            logger.info("????????????:{}", hits.getMaxScore());
            //??????SearchHits
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                logger.info("???????????????:{}", hit.getIndex());
                logger.info("??????ID:{}", hit.getId());
                logger.info("????????????:{}", hit.getScore());
                //????????????
                logger.info("????????????:{}", hit.getSourceAsString());
                list.add(hit.getSourceAsString());

                //??????????????????
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                HighlightField highlightField = highlightFields.get(field);
                //??????????????????????????????????????????????????????????????????
                Text[] fragments = highlightField.fragments();
                String stringFragment = fragments[0].string();
                logger.info("????????????:{}", stringFragment);
            }
            return JSON.toJSONString(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return JSON.toJSONString(list);

    }

    /**
     * ????????????
     *
     * @param indexName ????????????
     * @param size      ??????
     */
    public void scrollSearch(String indexName, int size, String field, int value) {

        //??????searchRequest
        SearchRequest searchRequest = new SearchRequest(indexName);

        //??????searchRequest????????? searchSourceBuilder
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery(field, value));

        //????????????????????????
        sourceBuilder.size(size);
        //sourceBuilder????????? searchRequest
        searchRequest.source(sourceBuilder);

        //??????????????????
        searchRequest.scroll(TimeValue.timeValueMinutes(1L));


        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            //?????????????????????ID ???ID??????????????????????????????????????????  ??????????????????????????????????????????
            String scrollId = searchResponse.getScrollId();

            logger.info("first scrollId is:{}", scrollId);
            //?????????????????????
            SearchHits hits = searchResponse.getHits();
            while (null != hits && ArrayUtils.isNotEmpty(hits.getHits())) {
                //?????????????????????
                SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId);
                searchScrollRequest.scroll(TimeValue.timeValueSeconds(30));
                SearchResponse response = restHighLevelClient.scroll(searchScrollRequest, RequestOptions.DEFAULT);

                //??????????????????ID  ???ID??????????????????????????????????????????  ????????????????????????????????????
                scrollId = response.getScrollId();
                //??????????????????????????????
                hits = response.getHits();

                logger.info("next scrollId is:{}", scrollId);
                logger.info("hits is:{}", JSON.toJSONString(hits));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * ???????????? ?????????????????????
     *
     * @param indexName ????????????
     * @return ???
     */
    public String multiSearch(String indexName, List<String> names) {

        //????????????????????????
        MultiSearchRequest multiSearchRequest = new MultiSearchRequest();

        //??????map
        names.forEach(a -> {
            //????????????????????????
            SearchRequest searchRequest = new SearchRequest(indexName);
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchQuery("name", a));
            searchRequest.source(sourceBuilder);
            //????????????????????????????????????????????????
            multiSearchRequest.add(searchRequest);
        });
        List<String> list = new ArrayList<>();
        try {
            MultiSearchResponse multiSearchResponse = restHighLevelClient.msearch(multiSearchRequest, RequestOptions.DEFAULT);
            logger.info("????????????:{}", JSON.toJSONString(multiSearchResponse));
            //????????????????????????
            MultiSearchResponse.Item[] items = multiSearchResponse.getResponses();

            if (ArrayUtils.isEmpty(items)) {
                logger.info("items  is   null ");
                return "null";
            }

            for (MultiSearchResponse.Item item : items) {

                Exception failure = item.getFailure();
                if (null != failure) {
                    logger.info("Exception is:{}", failure.toString());
                }
                SearchResponse response = item.getResponse();
                SearchHits hits = response.getHits();
                if (hits.getTotalHits().value <= 0) {
                    logger.info("hits.getTotalHits().value is 0");
                    continue;
                }
                SearchHit[] searchHits = hits.getHits();
                for (SearchHit searchHit : searchHits) {
                    if (StringUtils.isNotBlank(searchHit.getSourceAsString())) {
                        logger.info("???:{}", searchHit.getSourceAsString());
                        list.add(searchHit.getSourceAsString());
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return JSON.toJSONString(list);

    }

    /**
     * ?????????????????????
     *
     * @param field  ??????
     * @param indexs ????????????
     * @return ??????
     */
    public void fieldCaps(String field, List<String> indexs) {
        String[] indices = new String[indexs.size()];
        FieldCapabilitiesRequest request = new FieldCapabilitiesRequest().fields(field).indices(indices);
        //?????????????????? IndicesOptions  ???????????????????????????????????????????????????
        request.indicesOptions(IndicesOptions.lenientExpandOpen());

        try {
            FieldCapabilitiesResponse response = restHighLevelClient.fieldCaps(request, RequestOptions.DEFAULT);
            logger.info("????????????:{}",JSON.toJSONString(response));
            //?????????????????????????????????????????????
            Map<String, FieldCapabilities> responseField = response.getField(field);

            //???????????????key
            //Set<String> set = responseField.keySet();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //#########################################################????????????##########################################################




























}
