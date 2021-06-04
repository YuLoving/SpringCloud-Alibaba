package cn.nj.elasticsearch.controller;

import cn.nj.elasticsearch.manager.EsManager;
import cn.nj.elasticsearch.pojo.Use;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：zty
 * @date ：Created in 2021/5/27 15:36
 * @description ：
 */
@RestController
@RequestMapping("es")
public class EsController {

    private static final String INDEX="my_test_index";

    @Autowired
    private EsManager esManager;


    @GetMapping("index/put")
    public  void  indexPut(@RequestBody Use use){
        esManager.createDocument(INDEX,use.toString());
    }

    @GetMapping("index/put/async")
    public  void  indexPutAsync(@RequestBody Use use){
        esManager.createDocumentAsync(INDEX,use.toString());
    }

    @GetMapping("index/get")
    public  String  get(String id){
        return esManager.getDocument(INDEX,id);
    }


    @GetMapping("index/get/async")
    public  void   getAsync(String id){
         esManager.getDocumentAsync(INDEX,id);
    }


    @GetMapping("index/exist")
    public  boolean  exist(String id){
        return esManager.existDocument(INDEX,id);
    }

    @GetMapping("index/exist/async")
    public  void   existAsync(String id){
         esManager.existDocumentAsync(INDEX,id);
    }


    @GetMapping("index/delete")
    public  void   delete(String id){
        esManager.deleteDocument(INDEX,id);
    }


    @GetMapping("index/delete/async")
    public  void   deleteAsync(String id){
        esManager.deleteDocumentAsync(INDEX,id);
    }


    @GetMapping("index/update")
    public  void   update(String  id){
        esManager.updateDocument(INDEX,id);
    }


    @GetMapping("index/update/async")
    public  void   updateAsync(String  id){
        esManager.updateDocumentAsync(INDEX,id);
    }


    @GetMapping("index/termVector")
    public  String   termVector(String  id,String field){
        return esManager.termVector(INDEX,id,field);
    }



    @GetMapping("index/bulk/put")
    public void bulkCreate(@RequestBody List<Use> list){
        esManager.bulkCreateIndex(INDEX,list);
    }

    @GetMapping("index/bulkProcessor/put")
    public void bulkProcessorCreate(@RequestBody List<Use> list){
        esManager.bulkProcessor(INDEX,list);
    }

    @GetMapping("index/multiGet")
    public String multiGet(@RequestBody  List<String> ids){
        return esManager.multiGet(INDEX, ids);
    }

    @GetMapping("index/multiGet/async")
    public void multiGetAsync(@RequestBody  List<String> ids){
         esManager.multiGetAsync(INDEX, ids);
    }

    @GetMapping("reindex")
    public void reindex(String toIndex){
        esManager.reIndex(INDEX, toIndex);
    }


    @GetMapping("search")
    public String searchAll(String field, String value ){
        return  esManager.search(field,value);
    }



    @GetMapping("searchScroll")
    public void searchScroll(int size,String field, int value ){
          esManager.scrollSearch(INDEX,size,field,value);
    }


    @GetMapping("multiSearch")
    public String multiSearch(@RequestBody  List<String> names){

      return   esManager.multiSearch(INDEX,names);
    }
}
