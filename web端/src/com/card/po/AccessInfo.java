package com.card.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class AccessInfo {
    /*进出记录id*/
    private Integer accessId;
    public Integer getAccessId(){
        return accessId;
    }
    public void setAccessId(Integer accessId){
        this.accessId = accessId;
    }

    /*车牌号*/
    private Product carObj;
    public Product getCarObj() {
        return carObj;
    }
    public void setCarObj(Product carObj) {
        this.carObj = carObj;
    }

    /*所属rfid*/
    @NotEmpty(message="所属rfid不能为空")
    private String rfid;
    public String getRfid() {
        return rfid;
    }
    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    /*所属用户*/
    private UserInfo userId;
    public UserInfo getUserId() {
        return userId;
    }
    public void setUserId(UserInfo userId) {
        this.userId = userId;
    }

    /*进入时间*/
    @NotEmpty(message="进入时间不能为空")
    private String inTime;
    public String getInTime() {
        return inTime;
    }
    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    /*出去时间*/
    @NotEmpty(message="出去时间不能为空")
    private String outTime;
    public String getOutTime() {
        return outTime;
    }
    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    /*备注*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonAccessInfo=new JSONObject(); 
		jsonAccessInfo.accumulate("accessId", this.getAccessId());
		jsonAccessInfo.accumulate("carObj", this.getCarObj().getProductNo());
		jsonAccessInfo.accumulate("carObjPri", this.getCarObj().getProductNo());
		jsonAccessInfo.accumulate("rfid", this.getRfid());
		jsonAccessInfo.accumulate("userId", this.getUserId().getName());
		jsonAccessInfo.accumulate("userIdPri", this.getUserId().getUser_name());
		jsonAccessInfo.accumulate("inTime", this.getInTime().length()>19?this.getInTime().substring(0,19):this.getInTime());
		jsonAccessInfo.accumulate("outTime", this.getOutTime().length()>19?this.getOutTime().substring(0,19):this.getOutTime());
		jsonAccessInfo.accumulate("memo", this.getMemo());
		return jsonAccessInfo;
    }}