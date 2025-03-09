package com.card.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class LostApply {
    /*挂失申请id*/
    private Integer lostApplyId;
    public Integer getLostApplyId(){
        return lostApplyId;
    }
    public void setLostApplyId(Integer lostApplyId){
        this.lostApplyId = lostApplyId;
    }

    /*车牌号*/
    private Product proObj;
    public Product getProObj() {
        return proObj;
    }
    public void setProObj(Product proObj) {
        this.proObj = proObj;
    }

    /*挂失用户*/
    private UserInfo lostUserObj;
    public UserInfo getLostUserObj() {
        return lostUserObj;
    }
    public void setLostUserObj(UserInfo lostUserObj) {
        this.lostUserObj = lostUserObj;
    }

    /*挂失时间*/
    @NotEmpty(message="挂失时间不能为空")
    private String lostTime;
    public String getLostTime() {
        return lostTime;
    }
    public void setLostTime(String lostTime) {
        this.lostTime = lostTime;
    }

    /*挂失原因*/
    private String reason;
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    /*处理状态*/
    @NotEmpty(message="处理状态不能为空")
    private String applyState;
    public String getApplyState() {
        return applyState;
    }
    public void setApplyState(String applyState) {
        this.applyState = applyState;
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
    	JSONObject jsonLostApply=new JSONObject(); 
		jsonLostApply.accumulate("lostApplyId", this.getLostApplyId());
		jsonLostApply.accumulate("proObj", this.getProObj().getProductNo());
		jsonLostApply.accumulate("proObjPri", this.getProObj().getProductNo());
		jsonLostApply.accumulate("lostUserObj", this.getLostUserObj().getName());
		jsonLostApply.accumulate("lostUserObjPri", this.getLostUserObj().getUser_name());
		jsonLostApply.accumulate("lostTime", this.getLostTime().length()>19?this.getLostTime().substring(0,19):this.getLostTime());
		jsonLostApply.accumulate("reason", this.getReason());
		jsonLostApply.accumulate("applyState", this.getApplyState());
		jsonLostApply.accumulate("result", this.getResult());
		return jsonLostApply;
    }}