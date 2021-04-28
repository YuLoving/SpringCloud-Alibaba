package cn.nj.seatermstorage.controller;

import cn.nj.seatermstorage.entity.Storage;
import cn.nj.seatermstorage.service.StorageService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：zty
 * @date ：Created in 2021/4/28 15:07
 * @description ：
 */
@RestController
public class StorageController {


    @Autowired
    private StorageService storageService;


    @PostMapping("/reduce_storage")
    public String reduceStorage(@RequestBody Storage storage) {
        Map<String, String> result = new HashMap<>();
        storageService.update(storage);
        result.put("code", "0");
        result.put("message", "reduce storage success");
        return JSON.toJSONString(result);
    }
}
