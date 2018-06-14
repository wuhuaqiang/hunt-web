package com.hunt.dao;

import com.hunt.model.entity.SysDataItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDataItemMapper {
    //新增
    public Integer insert(SysDataItem SysDataItem);

    //更新
    public void update(SysDataItem SysDataItem);

    //通过对象进行查询
    public SysDataItem select(SysDataItem SysDataItem);

    //通过id进行查询
    public SysDataItem selectById(@Param("id") Integer id);

    //查询全部
    public List<SysDataItem> selectAll();

    //查询数量
    public int selectCounts();

    boolean isExistName(@Param("key") String key, @Param("groupId") Integer groupId);

    void deleteById(@Param("id") Integer id);

    boolean isExistDataItemNameExcludeId(@Param("id") Integer id, @Param("key") String key, @Param("groupId") Integer groupId);

    String selectByKey(@Param("key") String key, @Param("groupId") Integer groupId);
}