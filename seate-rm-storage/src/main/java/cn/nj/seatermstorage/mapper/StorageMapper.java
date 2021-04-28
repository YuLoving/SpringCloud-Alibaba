package cn.nj.seatermstorage.mapper;

import cn.nj.seatermstorage.entity.Storage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author ：zty
 * @date ：Created in 2021/4/28 14:55
 * @description ：
 */
@Repository
@Mapper
public interface StorageMapper {

        int update(Storage storage);
}
