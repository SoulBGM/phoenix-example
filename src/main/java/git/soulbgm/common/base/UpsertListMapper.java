package git.soulbgm.common.base;

import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

@RegisterMapper
public interface UpsertListMapper<T> {

    @InsertProvider(type = PhoenixProvider.class, method = "dynamicSQL")
    int upsertList(List<? extends T> recordList);

}
