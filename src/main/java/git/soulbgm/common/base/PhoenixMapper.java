package git.soulbgm.common.base;

import tk.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface PhoenixMapper<T> extends UpsertMapper<T>, UpsertListMapper<T> {
}
