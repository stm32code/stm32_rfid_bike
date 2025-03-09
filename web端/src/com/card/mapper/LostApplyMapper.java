package com.card.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.card.po.LostApply;

public interface LostApplyMapper {
	/*添加挂失申请信息*/
	public void addLostApply(LostApply lostApply) throws Exception;

	/*按照查询条件分页查询挂失申请记录*/
	public ArrayList<LostApply> queryLostApply(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有挂失申请记录*/
	public ArrayList<LostApply> queryLostApplyList(@Param("where") String where) throws Exception;

	/*按照查询条件的挂失申请记录数*/
	public int queryLostApplyCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条挂失申请记录*/
	public LostApply getLostApply(int lostApplyId) throws Exception;

	/*更新挂失申请记录*/
	public void updateLostApply(LostApply lostApply) throws Exception;

	/*删除挂失申请记录*/
	public void deleteLostApply(int lostApplyId) throws Exception;

}
