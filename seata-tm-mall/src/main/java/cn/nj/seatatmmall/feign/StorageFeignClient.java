package cn.nj.seatatmmall.feign;

import cn.nj.seatatmmall.entity.Storage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author ：zty
 * @date ：Created in 2021/4/28 15:51
 * @description ：
 */
@FeignClient("rm-storage")
public interface StorageFeignClient {

    @PostMapping("/reduce_storage")
    public String reduce(Storage storage);
}
