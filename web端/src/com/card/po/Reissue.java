package com.card.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Reissue {
    /*补办id*/
    private Integer reissueId;
    public Integer getReissueId(){
        return reissueId;
    }
    public void setReissueId(Integer reissueId){
        this.reissueId = reissueId;
    }

    /*车牌号*/
    private Product proObj;
    public Product getProObj() {
        return proObj;
    }
    public void setProObj(Product proObj) {
        this.proObj = proObj;
    }

    /*补办用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*补办时间*/
    @NotEmpty(message="补办时间不能为空")
    private String reissueTime;
    public String getReissueTime() {
        return reissueTime;
    }
    public void setReissueTime(String reissueTime) {
        this.reissueTime = reissueTime;
    }

    /*补办原因*/
    private String ememo;
    public String getEmemo() {
        return ememo;
    }
    public void setEmemo(String ememo) {
        this.ememo = ememo;
    }

    /*处理状态*/
    @NotEmpty(message="处理状态不能为空")
    private String state;
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    /*rfid*/
    @NotEmpty(message="rfid不能为空")
    private String rfid;
    public String getRfid() {
        return rfid;
    }
    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonReissue=new JSONObject(); 
		jsonReissue.accumulate("reissueId", this.getReissueId());
		jsonReissue.accumulate("proObj", this.getProObj().getProductNo());
		jsonReissue.accumulate("proObjPri", this.getProObj().getProductNo());
		jsonReissue.accumulate("userObj", this.getUserObj().getName());
		jsonReissue.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonReissue.accumulate("reissueTime", this.getReissueTime().length()>19?this.getReissueTime().substring(0,19):this.getReissueTime());
		jsonReissue.accumulate("ememo", this.getEmemo());
		jsonReissue.accumulate("state", this.getState());
		jsonReissue.accumulate("rfid", this.getRfid());
		return jsonReissue;
    }}