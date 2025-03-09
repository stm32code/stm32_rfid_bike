package com.card.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Product {
    /*车牌号*/
    @NotEmpty(message="车牌号不能为空")
    private String productNo;
    public String getProductNo(){
        return productNo;
    }
    public void setProductNo(String productNo){
        this.productNo = productNo;
    }

    /*电动车类别*/
    private ProductClass productClassObj;
    public ProductClass getProductClassObj() {
        return productClassObj;
    }
    public void setProductClassObj(ProductClass productClassObj) {
        this.productClassObj = productClassObj;
    }

    /*电动车名称*/
    @NotEmpty(message="电动车名称不能为空")
    private String productName;
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /*电动车主图*/
    private String mainPhoto;
    public String getMainPhoto() {
        return mainPhoto;
    }
    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    /*电动车价格*/
    @NotNull(message="必须输入电动车价格")
    private Float price;
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }

    /*电动车描述*/
    @NotEmpty(message="电动车描述不能为空")
    private String productDesc;
    public String getProductDesc() {
        return productDesc;
    }
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    /*发布时间*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    /*所属用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*电动车状态*/
    @NotEmpty(message="电动车状态不能为空")
    private String proState;
    public String getProState() {
        return proState;
    }
    public void setProState(String proState) {
        this.proState = proState;
    }

    /*电动车RFID*/
    private String rfid;
    public String getRfid() {
        return rfid;
    }
    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonProduct=new JSONObject(); 
		jsonProduct.accumulate("productNo", this.getProductNo());
		jsonProduct.accumulate("productClassObj", this.getProductClassObj().getClassName());
		jsonProduct.accumulate("productClassObjPri", this.getProductClassObj().getClassId());
		jsonProduct.accumulate("productName", this.getProductName());
		jsonProduct.accumulate("mainPhoto", this.getMainPhoto());
		jsonProduct.accumulate("price", this.getPrice());
		jsonProduct.accumulate("productDesc", this.getProductDesc());
		jsonProduct.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		jsonProduct.accumulate("userObj", this.getUserObj().getName());
		jsonProduct.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonProduct.accumulate("proState", this.getProState());
		jsonProduct.accumulate("rfid", this.getRfid());
		return jsonProduct;
    }}