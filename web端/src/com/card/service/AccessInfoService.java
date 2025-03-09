package com.card.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.card.po.Product;
import com.card.po.UserInfo;
import com.card.po.AccessInfo;

import com.card.mapper.AccessInfoMapper;
@Service
public class AccessInfoService {

	@Resource AccessInfoMapper accessInfoMapper;
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

    /*添加电动车进出记录*/
    public void addAccessInfo(AccessInfo accessInfo) throws Exception {
    	accessInfoMapper.addAccessInfo(accessInfo);
    }

    /*按照查询条件分页查询电动车进出记录*/
    public ArrayList<AccessInfo> queryAccessInfo(Product carObj,String rfid,UserInfo userId,String inTime,String outTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != carObj &&  carObj.getProductNo() != null  && !carObj.getProductNo().equals(""))  where += " and t_accessInfo.carObj='" + carObj.getProductNo() + "'";
    	if(!rfid.equals("")) where = where + " and t_accessInfo.rfid like '%" + rfid + "%'";
    	if(null != userId &&  userId.getUser_name() != null  && !userId.getUser_name().equals(""))  where += " and t_accessInfo.userId='" + userId.getUser_name() + "'";
    	if(!inTime.equals("")) where = where + " and t_accessInfo.inTime like '%" + inTime + "%'";
    	if(!outTime.equals("")) where = where + " and t_accessInfo.outTime like '%" + outTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return accessInfoMapper.queryAccessInfo(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<AccessInfo> queryAccessInfo(Product carObj,String rfid,UserInfo userId,String inTime,String outTime) throws Exception  { 
     	String where = "where 1=1";
    	if(null != carObj &&  carObj.getProductNo() != null && !carObj.getProductNo().equals(""))  where += " and t_accessInfo.carObj='" + carObj.getProductNo() + "'";
    	if(!rfid.equals("")) where = where + " and t_accessInfo.rfid like '%" + rfid + "%'";
    	if(null != userId &&  userId.getUser_name() != null && !userId.getUser_name().equals(""))  where += " and t_accessInfo.userId='" + userId.getUser_name() + "'";
    	if(!inTime.equals("")) where = where + " and t_accessInfo.inTime like '%" + inTime + "%'";
    	if(!outTime.equals("")) where = where + " and t_accessInfo.outTime like '%" + outTime + "%'";
    	return accessInfoMapper.queryAccessInfoList(where);
    }

    /*查询所有电动车进出记录*/
    public ArrayList<AccessInfo> queryAllAccessInfo()  throws Exception {
        return accessInfoMapper.queryAccessInfoList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Product carObj,String rfid,UserInfo userId,String inTime,String outTime) throws Exception {
     	String where = "where 1=1";
    	if(null != carObj &&  carObj.getProductNo() != null && !carObj.getProductNo().equals(""))  where += " and t_accessInfo.carObj='" + carObj.getProductNo() + "'";
    	if(!rfid.equals("")) where = where + " and t_accessInfo.rfid like '%" + rfid + "%'";
    	if(null != userId &&  userId.getUser_name() != null && !userId.getUser_name().equals(""))  where += " and t_accessInfo.userId='" + userId.getUser_name() + "'";
    	if(!inTime.equals("")) where = where + " and t_accessInfo.inTime like '%" + inTime + "%'";
    	if(!outTime.equals("")) where = where + " and t_accessInfo.outTime like '%" + outTime + "%'";
        recordNumber = accessInfoMapper.queryAccessInfoCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取电动车进出记录*/
    public AccessInfo getAccessInfo(int accessId) throws Exception  {
        AccessInfo accessInfo = accessInfoMapper.getAccessInfo(accessId);
        return accessInfo;
    }

    /*更新电动车进出记录*/
    public void updateAccessInfo(AccessInfo accessInfo) throws Exception {
        accessInfoMapper.updateAccessInfo(accessInfo);
    }

    /*删除一条电动车进出记录*/
    public void deleteAccessInfo (int accessId) throws Exception {
        accessInfoMapper.deleteAccessInfo(accessId);
    }

    /*删除多条电动车进出信息*/
    public int deleteAccessInfos (String accessIds) throws Exception {
    	String _accessIds[] = accessIds.split(",");
    	for(String _accessId: _accessIds) {
    		accessInfoMapper.deleteAccessInfo(Integer.parseInt(_accessId));
    	}
    	return _accessIds.length;
    }
}
