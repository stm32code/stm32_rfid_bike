package com.card.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.card.po.Reissue;

public interface ReissueMapper {
	/*添加补办申请信息*/
	public void addReissue(Reissue reissue) throws Exception;

	/*按照查询条件分页查询补办申请记录*/
	public ArrayList<Reissue> queryReissue(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有补办申请记录*/
	public ArrayList<Reissue> queryReissueList(@Param("where") String where) throws Exception;

	/*按照查询条件的补办申请记录数*/
	public int queryReissueCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条补办申请记录*/
	public Reissue getReissue(int reissueId) throws Exception;

	/*更新补办申请记录*/
	public void updateReissue(Reissue reissue) throws Exception;

	/*删除补办申请记录*/
	public void deleteReissue(int reissueId) throws Exception;

}
