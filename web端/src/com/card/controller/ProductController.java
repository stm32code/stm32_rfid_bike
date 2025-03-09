package com.card.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.card.utils.ExportExcelUtil;
import com.card.utils.UserException;
import com.card.service.ProductService;
import com.card.po.Product;
import com.card.service.ProductClassService;
import com.card.po.ProductClass;
import com.card.service.UserInfoService;
import com.card.po.UserInfo;

//Product管理控制层
@Controller
@RequestMapping("/Product")
public class ProductController extends BaseController {

    /*业务层对象*/
    @Resource ProductService productService;

    @Resource ProductClassService productClassService;
    @Resource UserInfoService userInfoService;
	@InitBinder("productClassObj")
	public void initBinderproductClassObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("productClassObj.");
	}
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("product")
	public void initBinderProduct(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("product.");
	}
	/*跳转到添加Product视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Product());
		/*查询所有的ProductClass信息*/
		List<ProductClass> productClassList = productClassService.queryAllProductClass();
		request.setAttribute("productClassList", productClassList);
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "Product_add";
	}

	/*客户端ajax方式提交添加电动车信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Product product, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		if(productService.getProduct(product.getProductNo()) != null) {
			message = "车牌号已经存在！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			product.setMainPhoto(this.handlePhotoUpload(request, "mainPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        productService.addProduct(product);
        message = "电动车添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	
	/*客户端ajax方式提交添加电动车信息*/
	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public void userAdd(Product product, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
	 
		if(productService.getProduct(product.getProductNo()) != null) {
			message = "车牌号已经存在！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			product.setMainPhoto(this.handlePhotoUpload(request, "mainPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		product.setAddTime(sdf.format(new java.util.Date()));
		
		String user_name = (String) session.getAttribute("user_name");
		if(user_name == null) {
			message = "请先登录系统！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		UserInfo userInfo = userInfoService.getUserInfo(user_name);
		product.setUserObj(userInfo);
		
		product.setProState("待审核");
        productService.addProduct(product);
        message = "电动车添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	
	
	/*ajax方式按照查询条件分页查询电动车信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String productNo,@ModelAttribute("productClassObj") ProductClass productClassObj,String productName,String addTime,@ModelAttribute("userObj") UserInfo userObj,String proState,String rfid,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (productNo == null) productNo = "";
		if (productName == null) productName = "";
		if (addTime == null) addTime = "";
		if (proState == null) proState = "";
		if (rfid == null) rfid = "";
		if(rows != 0)productService.setRows(rows);
		List<Product> productList = productService.queryProduct(productNo, productClassObj, productName, addTime, userObj, proState, rfid, page);
	    /*计算总的页数和总的记录数*/
	    productService.queryTotalPageAndRecordNumber(productNo, productClassObj, productName, addTime, userObj, proState, rfid);
	    /*获取到总的页码数目*/
	    int totalPage = productService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = productService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Product product:productList) {
			JSONObject jsonProduct = product.getJsonObject();
			jsonArray.put(jsonProduct);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询电动车信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Product> productList = productService.queryAllProduct();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Product product:productList) {
			JSONObject jsonProduct = new JSONObject();
			jsonProduct.accumulate("productNo", product.getProductNo());
			jsonArray.put(jsonProduct);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询电动车信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String productNo,@ModelAttribute("productClassObj") ProductClass productClassObj,String productName,String addTime,@ModelAttribute("userObj") UserInfo userObj,String proState,String rfid,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (productNo == null) productNo = "";
		if (productName == null) productName = "";
		if (addTime == null) addTime = "";
		if (proState == null) proState = "";
		if (rfid == null) rfid = "";
		List<Product> productList = productService.queryProduct(productNo, productClassObj, productName, addTime, userObj, proState, rfid, currentPage);
	    /*计算总的页数和总的记录数*/
	    productService.queryTotalPageAndRecordNumber(productNo, productClassObj, productName, addTime, userObj, proState, rfid);
	    /*获取到总的页码数目*/
	    int totalPage = productService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = productService.getRecordNumber();
	    request.setAttribute("productList",  productList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("productNo", productNo);
	    request.setAttribute("productClassObj", productClassObj);
	    request.setAttribute("productName", productName);
	    request.setAttribute("addTime", addTime);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("proState", proState);
	    request.setAttribute("rfid", rfid);
	    List<ProductClass> productClassList = productClassService.queryAllProductClass();
	    request.setAttribute("productClassList", productClassList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "Product/product_frontquery_result"; 
	}
	
	
	/*前台按照查询条件分页查询电动车信息*/
	@RequestMapping(value = { "/userFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String userFrontlist(String productNo,@ModelAttribute("productClassObj") ProductClass productClassObj,String productName,String addTime,@ModelAttribute("userObj") UserInfo userObj,String proState,String rfid,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (productNo == null) productNo = "";
		if (productName == null) productName = "";
		if (addTime == null) addTime = "";
		if (proState == null) proState = "";
		if (rfid == null) rfid = "";
		 
		String user_name = (String) session.getAttribute("user_name");
		userObj = userInfoService.getUserInfo(user_name);
		List<Product> productList = productService.queryProduct(productNo, productClassObj, productName, addTime, userObj, proState, rfid, currentPage);
	    /*计算总的页数和总的记录数*/
	    productService.queryTotalPageAndRecordNumber(productNo, productClassObj, productName, addTime, userObj, proState, rfid);
	    /*获取到总的页码数目*/
	    int totalPage = productService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = productService.getRecordNumber();
	    request.setAttribute("productList",  productList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("productNo", productNo);
	    request.setAttribute("productClassObj", productClassObj);
	    request.setAttribute("productName", productName);
	    request.setAttribute("addTime", addTime);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("proState", proState);
	    request.setAttribute("rfid", rfid);
	    List<ProductClass> productClassList = productClassService.queryAllProductClass();
	    request.setAttribute("productClassList", productClassList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "Product/product_userFrontquery_result"; 
	}
	
	

     /*前台查询Product信息*/
	@RequestMapping(value="/{productNo}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable String productNo,Model model,HttpServletRequest request) throws Exception {
		/*根据主键productNo获取Product对象*/
        Product product = productService.getProduct(productNo);

        List<ProductClass> productClassList = productClassService.queryAllProductClass();
        request.setAttribute("productClassList", productClassList);
        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("product",  product);
        return "Product/product_frontshow";
	}
	
	 /*前台查询Product信息*/
	@RequestMapping(value="/{productNo}/userFrontshow",method=RequestMethod.GET)
	public String userFrontshow(@PathVariable String productNo,Model model,HttpServletRequest request) throws Exception {
		/*根据主键productNo获取Product对象*/
        Product product = productService.getProduct(productNo);

        List<ProductClass> productClassList = productClassService.queryAllProductClass();
        request.setAttribute("productClassList", productClassList);
        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("product",  product);
        return "Product/product_userFrontshow";
	}
	
		

	/*ajax方式显示电动车修改jsp视图页*/
	@RequestMapping(value="/{productNo}/update",method=RequestMethod.GET)
	public void update(@PathVariable String productNo,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键productNo获取Product对象*/
        Product product = productService.getProduct(productNo);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonProduct = product.getJsonObject();
		out.println(jsonProduct.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新电动车信息*/
	@RequestMapping(value = "/{productNo}/update", method = RequestMethod.POST)
	public void update(@Validated Product product, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String mainPhotoFileName = this.handlePhotoUpload(request, "mainPhotoFile");
		if(!mainPhotoFileName.equals("upload/NoImage.jpg"))product.setMainPhoto(mainPhotoFileName); 


		try {
			productService.updateProduct(product);
			message = "电动车更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "电动车更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除电动车信息*/
	@RequestMapping(value="/{productNo}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String productNo,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  productService.deleteProduct(productNo);
	            request.setAttribute("message", "电动车删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "电动车删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条电动车记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String productNos,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = productService.deleteProducts(productNos);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出电动车信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String productNo,@ModelAttribute("productClassObj") ProductClass productClassObj,String productName,String addTime,@ModelAttribute("userObj") UserInfo userObj,String proState,String rfid, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(productNo == null) productNo = "";
        if(productName == null) productName = "";
        if(addTime == null) addTime = "";
        if(proState == null) proState = "";
        if(rfid == null) rfid = "";
        List<Product> productList = productService.queryProduct(productNo,productClassObj,productName,addTime,userObj,proState,rfid);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Product信息记录"; 
        String[] headers = { "车牌号","电动车类别","电动车名称","电动车主图","电动车价格","发布时间","所属用户","电动车状态","电动车RFID"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<productList.size();i++) {
        	Product product = productList.get(i); 
        	dataset.add(new String[]{product.getProductNo(),product.getProductClassObj().getClassName(),product.getProductName(),product.getMainPhoto(),product.getPrice() + "",product.getAddTime(),product.getUserObj().getName(),product.getProState(),product.getRfid()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Product.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
