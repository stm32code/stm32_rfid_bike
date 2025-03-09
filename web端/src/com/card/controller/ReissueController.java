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
import com.card.service.ReissueService;
import com.card.po.Reissue;
import com.card.service.ProductService;
import com.card.po.Product;
import com.card.service.UserInfoService;
import com.card.po.UserInfo;

//Reissue管理控制层
@Controller
@RequestMapping("/Reissue")
public class ReissueController extends BaseController {

    /*业务层对象*/
    @Resource ReissueService reissueService;

    @Resource ProductService productService;
    @Resource UserInfoService userInfoService;
	@InitBinder("proObj")
	public void initBinderproObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("proObj.");
	}
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("reissue")
	public void initBinderReissue(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("reissue.");
	}
	/*跳转到添加Reissue视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Reissue());
		/*查询所有的Product信息*/
		List<Product> productList = productService.queryAllProduct();
		request.setAttribute("productList", productList);
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "Reissue_add";
	}

	/*客户端ajax方式提交添加补办申请信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Reissue reissue, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        reissueService.addReissue(reissue);
        message = "补办申请添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	/*客户端ajax方式提交添加补办申请信息*/
	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public void userAdd(@Validated Reissue reissue, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		String user_name = (String) session.getAttribute("user_name");
		UserInfo userObj = userInfoService.getUserInfo(user_name);
		reissue.setState("待审核");
		reissue.setUserObj(userObj);
		reissue.setRfid("--");
		 
        reissueService.addReissue(reissue);
        message = "补办申请添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*ajax方式按照查询条件分页查询补办申请信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("proObj") Product proObj,@ModelAttribute("userObj") UserInfo userObj,String reissueTime,String state,String rfid,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (reissueTime == null) reissueTime = "";
		if (state == null) state = "";
		if (rfid == null) rfid = "";
		if(rows != 0)reissueService.setRows(rows);
		List<Reissue> reissueList = reissueService.queryReissue(proObj, userObj, reissueTime, state, rfid, page);
	    /*计算总的页数和总的记录数*/
	    reissueService.queryTotalPageAndRecordNumber(proObj, userObj, reissueTime, state, rfid);
	    /*获取到总的页码数目*/
	    int totalPage = reissueService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = reissueService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Reissue reissue:reissueList) {
			JSONObject jsonReissue = reissue.getJsonObject();
			jsonArray.put(jsonReissue);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询补办申请信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Reissue> reissueList = reissueService.queryAllReissue();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Reissue reissue:reissueList) {
			JSONObject jsonReissue = new JSONObject();
			jsonReissue.accumulate("reissueId", reissue.getReissueId());
			jsonArray.put(jsonReissue);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询补办申请信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("proObj") Product proObj,@ModelAttribute("userObj") UserInfo userObj,String reissueTime,String state,String rfid,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (reissueTime == null) reissueTime = "";
		if (state == null) state = "";
		if (rfid == null) rfid = "";
		List<Reissue> reissueList = reissueService.queryReissue(proObj, userObj, reissueTime, state, rfid, currentPage);
	    /*计算总的页数和总的记录数*/
	    reissueService.queryTotalPageAndRecordNumber(proObj, userObj, reissueTime, state, rfid);
	    /*获取到总的页码数目*/
	    int totalPage = reissueService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = reissueService.getRecordNumber();
	    request.setAttribute("reissueList",  reissueList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("proObj", proObj);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("reissueTime", reissueTime);
	    request.setAttribute("state", state);
	    request.setAttribute("rfid", rfid);
	    List<Product> productList = productService.queryAllProduct();
	    request.setAttribute("productList", productList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "Reissue/reissue_frontquery_result"; 
	}
	
	
	/*前台按照查询条件分页查询补办申请信息*/
	@RequestMapping(value = { "/userFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String userFrontlist(@ModelAttribute("proObj") Product proObj,@ModelAttribute("userObj") UserInfo userObj,String reissueTime,String state,String rfid,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (reissueTime == null) reissueTime = "";
		if (state == null) state = "";
		if (rfid == null) rfid = "";
		String user_name = (String)session.getAttribute("user_name");
		userObj = userInfoService.getUserInfo(user_name);
		List<Reissue> reissueList = reissueService.queryReissue(proObj, userObj, reissueTime, state, rfid, currentPage);
	    /*计算总的页数和总的记录数*/
	    reissueService.queryTotalPageAndRecordNumber(proObj, userObj, reissueTime, state, rfid);
	    /*获取到总的页码数目*/
	    int totalPage = reissueService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = reissueService.getRecordNumber();
	    request.setAttribute("reissueList",  reissueList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("proObj", proObj);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("reissueTime", reissueTime);
	    request.setAttribute("state", state);
	    request.setAttribute("rfid", rfid);
	    List<Product> productList = productService.queryAllProduct();
	    request.setAttribute("productList", productList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "Reissue/reissue_userFrontquery_result"; 
	}
	

     /*前台查询Reissue信息*/
	@RequestMapping(value="/{reissueId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer reissueId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键reissueId获取Reissue对象*/
        Reissue reissue = reissueService.getReissue(reissueId);

        List<Product> productList = productService.queryAllProduct();
        request.setAttribute("productList", productList);
        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("reissue",  reissue);
        return "Reissue/reissue_frontshow";
	}

	/*ajax方式显示补办申请修改jsp视图页*/
	@RequestMapping(value="/{reissueId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer reissueId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键reissueId获取Reissue对象*/
        Reissue reissue = reissueService.getReissue(reissueId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonReissue = reissue.getJsonObject();
		out.println(jsonReissue.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新补办申请信息*/
	@RequestMapping(value = "/{reissueId}/update", method = RequestMethod.POST)
	public void update(@Validated Reissue reissue, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
    	 
		
    	Reissue reissueDb = reissueService.getReissue(reissue.getReissueId());
		try {
			reissueService.updateReissue(reissue);
			if(reissueDb.getState().equals("待审核") && reissue.getState().equals("审核通过")) {
				Product product = productService.getProduct(reissue.getProObj().getProductNo());
				product.setRfid(reissue.getRfid());
				productService.updateProduct(product);
			}
			
			message = "补办申请更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "补办申请更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除补办申请信息*/
	@RequestMapping(value="/{reissueId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer reissueId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  reissueService.deleteReissue(reissueId);
	            request.setAttribute("message", "补办申请删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "补办申请删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条补办申请记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String reissueIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = reissueService.deleteReissues(reissueIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出补办申请信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("proObj") Product proObj,@ModelAttribute("userObj") UserInfo userObj,String reissueTime,String state,String rfid, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(reissueTime == null) reissueTime = "";
        if(state == null) state = "";
        if(rfid == null) rfid = "";
        List<Reissue> reissueList = reissueService.queryReissue(proObj,userObj,reissueTime,state,rfid);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Reissue信息记录"; 
        String[] headers = { "补办id","车牌号","补办用户","补办时间","补办原因","处理状态","rfid"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<reissueList.size();i++) {
        	Reissue reissue = reissueList.get(i); 
        	dataset.add(new String[]{reissue.getReissueId() + "",reissue.getProObj().getProductNo(),reissue.getUserObj().getName(),reissue.getReissueTime(),reissue.getEmemo(),reissue.getState(),reissue.getRfid()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Reissue.xls");//filename是下载的xls的名，建议最好用英文 
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
