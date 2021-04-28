package cn.nj.seatermstorage.service;

import cn.nj.seatermstorage.entity.Storage;
import cn.nj.seatermstorage.mapper.StorageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：zty
 * @date ：Created in 2021/4/28 15:03
 * @description ：
 */
@Service
public class StorageService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StorageMapper storageMapper;


    @Transactional(rollbackFor = Exception.class)
    public void update(Storage storage) {

        if(1==1){
            throw new RuntimeException("测试库存服务异常--分布式事务是否生效");
        }

        int update = storageMapper.update(storage);
        if (update > 0) {
            logger.info("库存扣减成功");
        } else {
            logger.info("库存扣减失败");
        }
    }


}
