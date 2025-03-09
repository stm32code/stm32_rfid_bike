package com.card.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.card.po.JieDong;

public interface JieDongMapper {
	/*添加解冻申请信息*/
	public void addJieDong(JieDong jieDong) throws Exception;

	/*按照查询条件分页查询解冻申请记录*/
	public ArrayList<JieDong> queryJieDong(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有解冻申请记录*/
	public ArrayList<JieDong> queryJieDongList(@Param("where") String where) throws Exception;

	/*按照查询条件的解冻申请记录数*/
	public int queryJieDongCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条解冻申请记录*/
	public JieDong getJieDong(int jieDongId) throws Exception;

	/*更新解冻申请记录*/
	public void updateJieDong(JieDong jieDong) throws Exception;

	/*删除解冻申请记录*/
	public void deleteJieDong(int jieDongId) throws Exception;

}
