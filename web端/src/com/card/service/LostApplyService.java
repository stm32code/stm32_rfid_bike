package com.card.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.card.po.Product;
import com.card.po.UserInfo;
import com.card.po.LostApply;

import com.card.mapper.LostApplyMapper;
@Service
public class LostApplyService {

	@Resource LostApplyMapper lostApplyMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加挂失申请记录*/
    public void addLostApply(LostApply lostApply) throws Exception {
    	lostApplyMapper.addLostApply(lostApply);
    }

    /*按照查询条件分页查询挂失申请记录*/
    public ArrayList<LostApply> queryLostApply(Product proObj,UserInfo lostUserObj,String lostTime,String applyState,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != proObj &&  proObj.getProductNo() != null  && !proObj.getProductNo().equals(""))  where += " and t_lostApply.proObj='" + proObj.getProductNo() + "'";
    	if(null != lostUserObj &&  lostUserObj.getUser_name() != null  && !lostUserObj.getUser_name().equals(""))  where += " and t_lostApply.lostUserObj='" + lostUserObj.getUser_name() + "'";
    	if(!lostTime.equals("")) where = where + " and t_lostApply.lostTime like '%" + lostTime + "%'";
    	if(!applyState.equals("")) where = where + " and t_lostApply.applyState like '%" + applyState + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return lostApplyMapper.queryLostApply(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<LostApply> queryLostApply(Product proObj,UserInfo lostUserObj,String lostTime,String applyState) throws Exception  { 
     	String where = "where 1=1";
    	if(null != proObj &&  proObj.getProductNo() != null && !proObj.getProductNo().equals(""))  where += " and t_lostApply.proObj='" + proObj.getProductNo() + "'";
    	if(null != lostUserObj &&  lostUserObj.getUser_name() != null && !lostUserObj.getUser_name().equals(""))  where += " and t_lostApply.lostUserObj='" + lostUserObj.getUser_name() + "'";
    	if(!lostTime.equals("")) where = where + " and t_lostApply.lostTime like '%" + lostTime + "%'";
    	if(!applyState.equals("")) where = where + " and t_lostApply.applyState like '%" + applyState + "%'";
    	return lostApplyMapper.queryLostApplyList(where);
    }

    /*查询所有挂失申请记录*/
    public ArrayList<LostApply> queryAllLostApply()  throws Exception {
        return lostApplyMapper.queryLostApplyList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Product proObj,UserInfo lostUserObj,String lostTime,String applyState) throws Exception {
     	String where = "where 1=1";
    	if(null != proObj &&  proObj.getProductNo() != null && !proObj.getProductNo().equals(""))  where += " and t_lostApply.proObj='" + proObj.getProductNo() + "'";
    	if(null != lostUserObj &&  lostUserObj.getUser_name() != null && !lostUserObj.getUser_name().equals(""))  where += " and t_lostApply.lostUserObj='" + lostUserObj.getUser_name() + "'";
    	if(!lostTime.equals("")) where = where + " and t_lostApply.lostTime like '%" + lostTime + "%'";
    	if(!applyState.equals("")) where = where + " and t_lostApply.applyState like '%" + applyState + "%'";
        recordNumber = lostApplyMapper.queryLostApplyCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取挂失申请记录*/
    public LostApply getLostApply(int lostApplyId) throws Exception  {
        LostApply lostApply = lostApplyMapper.getLostApply(lostApplyId);
        return lostApply;
    }

    /*更新挂失申请记录*/
    public void updateLostApply(LostApply lostApply) throws Exception {
        lostApplyMapper.updateLostApply(lostApply);
    }

    /*删除一条挂失申请记录*/
    public void deleteLostApply (int lostApplyId) throws Exception {
        lostApplyMapper.deleteLostApply(lostApplyId);
    }

    /*删除多条挂失申请信息*/
    public int deleteLostApplys (String lostApplyIds) throws Exception {
    	String _lostApplyIds[] = lostApplyIds.split(",");
    	for(String _lostApplyId: _lostApplyIds) {
    		lostApplyMapper.deleteLostApply(Integer.parseInt(_lostApplyId));
    	}
    	return _lostApplyIds.length;
    }
}
