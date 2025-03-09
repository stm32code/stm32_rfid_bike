package com.card.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.card.po.AccessInfo;

public interface AccessInfoMapper {
	/*添加电动车进出信息*/
	public void addAccessInfo(AccessInfo accessInfo) throws Exception;

	/*按照查询条件分页查询电动车进出记录*/
	public ArrayList<AccessInfo> queryAccessInfo(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有电动车进出记录*/
	public ArrayList<AccessInfo> queryAccessInfoList(@Param("where") String where) throws Exception;

	/*按照查询条件的电动车进出记录数*/
	public int queryAccessInfoCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条电动车进出记录*/
	public AccessInfo getAccessInfo(int accessId) throws Exception;

	/*更新电动车进出记录*/
	public void updateAccessInfo(AccessInfo accessInfo) throws Exception;

	/*删除电动车进出记录*/
	public void deleteAccessInfo(int accessId) throws Exception;

}
