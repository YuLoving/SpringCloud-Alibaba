package cn.nj.elasticsearch.controller;

import cn.nj.elasticsearch.manager.EsManager;
import cn.nj.elasticsearch.pojo.Use;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
