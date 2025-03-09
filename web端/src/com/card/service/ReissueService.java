package com.card.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.card.po.Product;
import com.card.po.UserInfo;
import com.card.po.Reissue;

import com.card.mapper.ReissueMapper;
@Service
public class ReissueService {

	@Resource ReissueMapper reissueMapper;
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

    /*添加补办申请记录*/
    public void addReissue(Reissue reissue) throws Exception {
    	reissueMapper.addReissue(reissue);
    }

    /*按照查询条件分页查询补办申请记录*/
    public ArrayList<Reissue> queryReissue(Product proObj,UserInfo userObj,String reissueTime,String state,String rfid,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != proObj &&  proObj.getProductNo() != null  && !proObj.getProductNo().equals(""))  where += " and t_reissue.proObj='" + proObj.getProductNo() + "'";
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_reissue.userObj='" + userObj.getUser_name() + "'";
    	if(!reissueTime.equals("")) where = where + " and t_reissue.reissueTime like '%" + reissueTime + "%'";
    	if(!state.equals("")) where = where + " and t_reissue.state like '%" + state + "%'";
    	if(!rfid.equals("")) where = where + " and t_reissue.rfid like '%" + rfid + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return reissueMapper.queryReissue(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Reissue> queryReissue(Product proObj,UserInfo userObj,String reissueTime,String state,String rfid) throws Exception  { 
     	String where = "where 1=1";
    	if(null != proObj &&  proObj.getProductNo() != null && !proObj.getProductNo().equals(""))  where += " and t_reissue.proObj='" + proObj.getProductNo() + "'";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_reissue.userObj='" + userObj.getUser_name() + "'";
    	if(!reissueTime.equals("")) where = where + " and t_reissue.reissueTime like '%" + reissueTime + "%'";
    	if(!state.equals("")) where = where + " and t_reissue.state like '%" + state + "%'";
    	if(!rfid.equals("")) where = where + " and t_reissue.rfid like '%" + rfid + "%'";
    	return reissueMapper.queryReissueList(where);
    }

    /*查询所有补办申请记录*/
    public ArrayList<Reissue> queryAllReissue()  throws Exception {
        return reissueMapper.queryReissueList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Product proObj,UserInfo userObj,String reissueTime,String state,String rfid) throws Exception {
     	String where = "where 1=1";
    	if(null != proObj &&  proObj.getProductNo() != null && !proObj.getProductNo().equals(""))  where += " and t_reissue.proObj='" + proObj.getProductNo() + "'";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_reissue.userObj='" + userObj.getUser_name() + "'";
    	if(!reissueTime.equals("")) where = where + " and t_reissue.reissueTime like '%" + reissueTime + "%'";
    	if(!state.equals("")) where = where + " and t_reissue.state like '%" + state + "%'";
    	if(!rfid.equals("")) where = where + " and t_reissue.rfid like '%" + rfid + "%'";
        recordNumber = reissueMapper.queryReissueCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取补办申请记录*/
    public Reissue getReissue(int reissueId) throws Exception  {
        Reissue reissue = reissueMapper.getReissue(reissueId);
        return reissue;
    }

    /*更新补办申请记录*/
    public void updateReissue(Reissue reissue) throws Exception {
        reissueMapper.updateReissue(reissue);
    }

    /*删除一条补办申请记录*/
    public void deleteReissue (int reissueId) throws Exception {
        reissueMapper.deleteReissue(reissueId);
    }

    /*删除多条补办申请信息*/
    public int deleteReissues (String reissueIds) throws Exception {
    	String _reissueIds[] = reissueIds.split(",");
    	for(String _reissueId: _reissueIds) {
    		reissueMapper.deleteReissue(Integer.parseInt(_reissueId));
    	}
    	return _reissueIds.length;
    }
}
