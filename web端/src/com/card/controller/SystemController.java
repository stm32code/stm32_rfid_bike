package com.card.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

  
import com.card.po.Admin;
import com.card.service.AdminService;
import com.card.service.ProductService;
import com.card.service.UserInfoService;
import com.card.utils.UserException;
 

 
@Controller
@SessionAttributes("username")
public class SystemController { 
	
	@Resource AdminService adminService;  
	@Resource UserInfoService userInfoService; 
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute(new Admin());
		return "login";
	}

	
	 
	@RequestMapping(value="/user/test",method=RequestMethod.GET)
	public void test(String rfidId,Model model,ServletRequest request,HttpServletResponse response) throws JSONException, IOException {
		System.out.println("test.....");
		response.setContentType("text/json;charset=UTF-8");  
		PrintWriter out = response.getWriter();  
	    //将要被返回到客户端的对象   
	    JSONObject json=new JSONObject();   
	    json.accumulate("success", true);
	    json.accumulate("msg", "testok");
	    out.println(json.toString());   
	    out.flush();  
	    out.close();  
	}
	
	
	@RequestMapping(value="/user/puttRfid",method=RequestMethod.GET)
	public void puttRfid(String rfidId,Model model,ServletRequest request,HttpServletResponse response) throws JSONException, IOException {
		System.out.println("接收到硬件传输的数据："+rfidId);
		// 获取ServletContext
		ServletContext context = request.getServletContext();
		// 设置全局变量
		context.setAttribute("rfidId", rfidId);
		response.setContentType("text/json;charset=UTF-8");  
		PrintWriter out = response.getWriter();  
	    //将要被返回到客户端的对象   
	    JSONObject json=new JSONObject();   
	    json.accumulate("success", true);
	    json.accumulate("msg", "设置rfid成功");
	    out.println(json.toString());   
	    out.flush();  
	    out.close();  
	}
	
	@RequestMapping(value="/user/getRfid",method=RequestMethod.GET)
	public void getRfid(Model model,ServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		
		boolean success = true; 
		// 获取ServletContext
		ServletContext context = request.getServletContext();
		// 获取全局变量
		String msg = (String) context.getAttribute("rfidId");
		String productNo = "";  //车牌号
		if(msg == null) {
			msg = "获取RFID失败，请刷卡！";
			success = false;
		}
	 
		// 设置全局变量
		context.setAttribute("rfidId", null);	
		response.setContentType("text/json;charset=UTF-8");  
		PrintWriter out = response.getWriter();  
	    //将要被返回到客户端的对象   
	    JSONObject json=new JSONObject();   
	    json.accumulate("success", success);
	    json.accumulate("msg", msg);
	    out.println(json.toString());   
	    out.flush();  
	    out.close();  
	}
	

	
	
	//前台用户登录
	@RequestMapping(value="/frontLogin",method=RequestMethod.POST)
	public void frontLogin(@RequestParam("userName")String userName,@RequestParam("password")String password,HttpServletResponse response,HttpSession session) throws Exception { 
		boolean success = true;
		String msg = ""; 

		
		if (!userInfoService.checkLogin(userName, password)) { 
			msg = userInfoService.getErrMessage();
			success = false; 
		} 
		if(success) {
			session.setAttribute("user_name", userName); 
		}
  
        response.setContentType("text/json;charset=UTF-8");  
        PrintWriter out = response.getWriter();  
        //将要被返回到客户端的对象   
        JSONObject json=new JSONObject();   
        json.accumulate("success", success);
        json.accumulate("msg", msg);
        out.println(json.toString());   
        out.flush();  
        out.close();  
	}


	@RequestMapping(value="/login",method=RequestMethod.POST)
	public void login(@Validated Admin admin,BindingResult br,Model model,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception { 
		boolean success = true;
		String msg = ""; 
		if(br.hasErrors()) {
			msg = br.getAllErrors().toString();
			success = false;  
		} 
		if (!adminService.checkLogin(admin)) { 
			msg = adminService.getErrMessage();
			success = false; 
		} 
		if(success) {
			session.setAttribute("username", admin.getUsername()); 
		}  
        response.setContentType("text/json;charset=UTF-8");  
        PrintWriter out = response.getWriter();  
        //将要被返回到客户端的对象   
        JSONObject json=new JSONObject();   
        json.accumulate("success", success);
        json.accumulate("msg", msg);
        out.println(json.toString());   
        out.flush();  
        out.close();  
	}
	
	
	
	@RequestMapping("/logout")
	public String logout(Model model,HttpSession session) {
		model.asMap().remove("username"); 
		session.invalidate();
		return "redirect:/login";
	}
	
	
	@RequestMapping(value="/changePassword",method=RequestMethod.POST)
	public String ChangePassword(String oldPassword,String newPassword,String newPassword2,HttpServletRequest request,HttpSession session) throws Exception { 
		if(oldPassword.equals("")) throw new UserException("请输入旧密码！");
		if(newPassword.equals("")) throw new UserException("请输入新密码！");
		if(!newPassword.equals(newPassword2)) throw new UserException("两次新密码输入不一致"); 
		
		String username = (String)session.getAttribute("username");
		if(username == null) throw new UserException("session会话超时，请重新登录系统!");
		 
		
		Admin admin = adminService.findAdminByUserName(username); 
		if(!admin.getPassword().equals(oldPassword)) throw new UserException("输入的旧密码不正确!");
		
		try { 
			adminService.changePassword(username,newPassword);
			request.setAttribute("message", java.net.URLEncoder.encode(
					"密码修改成功!", "GBK"));
			return "message";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", java.net.URLEncoder
					.encode("密码修改失败!","GBK"));
			return "error";
		}   
	}
	
	
	
	
}
