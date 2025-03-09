package com.card.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.card.po.Product;
import com.card.po.UserInfo;
import com.card.po.JieDong;

import com.card.mapper.JieDongMapper;
@Service
public class JieDongService {

	@Resource JieDongMapper jieDongMapper;
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

    /*添加解冻申请记录*/
    public void addJieDong(JieDong jieDong) throws Exception {
    	jieDongMapper.addJieDong(jieDong);
    }

    /*按照查询条件分页查询解冻申请记录*/
    public ArrayList<JieDong> queryJieDong(Product proObj,UserInfo userObj,String jiedongTime,String state,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != proObj &&  proObj.getProductNo() != null  && !proObj.getProductNo().equals(""))  where += " and t_jieDong.proObj='" + proObj.getProductNo() + "'";
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_jieDong.userObj='" + userObj.getUser_name() + "'";
    	if(!jiedongTime.equals("")) where = where + " and t_jieDong.jiedongTime like '%" + jiedongTime + "%'";
    	if(!state.equals("")) where = where + " and t_jieDong.state like '%" + state + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return jieDongMapper.queryJieDong(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<JieDong> queryJieDong(Product proObj,UserInfo userObj,String jiedongTime,String state) throws Exception  { 
     	String where = "where 1=1";
    	if(null != proObj &&  proObj.getProductNo() != null && !proObj.getProductNo().equals(""))  where += " and t_jieDong.proObj='" + proObj.getProductNo() + "'";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_jieDong.userObj='" + userObj.getUser_name() + "'";
    	if(!jiedongTime.equals("")) where = where + " and t_jieDong.jiedongTime like '%" + jiedongTime + "%'";
    	if(!state.equals("")) where = where + " and t_jieDong.state like '%" + state + "%'";
    	return jieDongMapper.queryJieDongList(where);
    }

    /*查询所有解冻申请记录*/
    public ArrayList<JieDong> queryAllJieDong()  throws Exception {
        return jieDongMapper.queryJieDongList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Product proObj,UserInfo userObj,String jiedongTime,String state) throws Exception {
     	String where = "where 1=1";
    	if(null != proObj &&  proObj.getProductNo() != null && !proObj.getProductNo().equals(""))  where += " and t_jieDong.proObj='" + proObj.getProductNo() + "'";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_jieDong.userObj='" + userObj.getUser_name() + "'";
    	if(!jiedongTime.equals("")) where = where + " and t_jieDong.jiedongTime like '%" + jiedongTime + "%'";
    	if(!state.equals("")) where = where + " and t_jieDong.state like '%" + state + "%'";
        recordNumber = jieDongMapper.queryJieDongCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取解冻申请记录*/
    public JieDong getJieDong(int jieDongId) throws Exception  {
        JieDong jieDong = jieDongMapper.getJieDong(jieDongId);
        return jieDong;
    }

    /*更新解冻申请记录*/
    public void updateJieDong(JieDong jieDong) throws Exception {
        jieDongMapper.updateJieDong(jieDong);
    }

    /*删除一条解冻申请记录*/
    public void deleteJieDong (int jieDongId) throws Exception {
        jieDongMapper.deleteJieDong(jieDongId);
    }

    /*删除多条解冻申请信息*/
    public int deleteJieDongs (String jieDongIds) throws Exception {
    	String _jieDongIds[] = jieDongIds.split(",");
    	for(String _jieDongId: _jieDongIds) {
    		jieDongMapper.deleteJieDong(Integer.parseInt(_jieDongId));
    	}
    	return _jieDongIds.length;
    }
}
