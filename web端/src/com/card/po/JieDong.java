package com.card.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class JieDong {
    /*解冻id*/
    private Integer jieDongId;
    public Integer getJieDongId(){
        return jieDongId;
    }
    public void setJieDongId(Integer jieDongId){
        this.jieDongId = jieDongId;
    }

    /*车牌号*/
    private Product proObj;
    public Product getProObj() {
        return proObj;
    }
    public void setProObj(Product proObj) {
        this.proObj = proObj;
    }

    /*解冻用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*解冻申请时间*/
    @NotEmpty(message="解冻申请时间不能为空")
    private String jiedongTime;
    public String getJiedongTime() {
        return jiedongTime;
    }
    public void setJiedongTime(String jiedongTime) {
        this.jiedongTime = jiedongTime;
    }

    /*原因*/
    private String reason;
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
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

    /*处理结果*/
    private String result;
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonJieDong=new JSONObject(); 
		jsonJieDong.accumulate("jieDongId", this.getJieDongId());
		jsonJieDong.accumulate("proObj", this.getProObj().getProductNo());
		jsonJieDong.accumulate("proObjPri", this.getProObj().getProductNo());
		jsonJieDong.accumulate("userObj", this.getUserObj().getName());
		jsonJieDong.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonJieDong.accumulate("jiedongTime", this.getJiedongTime().length()>19?this.getJiedongTime().substring(0,19):this.getJiedongTime());
		jsonJieDong.accumulate("reason", this.getReason());
		jsonJieDong.accumulate("state", this.getState());
		jsonJieDong.accumulate("result", this.getResult());
		return jsonJieDong;
    }}