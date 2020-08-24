package git.soulbgm.common.base;

import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.provider.SpecialProvider;
import tk.mybatis.mapper.provider.base.BaseInsertProvider;

import java.util.List;

@RegisterMapper
public interface UpsertMapper<T> {

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param record
     * @return
     */
    @InsertProvider(type = PhoenixProvider.class, method = "dynamicSQL")
    int upsert(T record);

}
