package com.cn.hnust.controller;


import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.hnust.pojo.Img;
import com.cn.hnust.pojo.News;
import com.cn.hnust.pojo.User;
import com.cn.hnust.service.IImgService;
import com.cn.hnust.service.INewsService;
import com.cn.hnust.service.IUserService;
import com.cn.hnust.util.AbstractController;

@Controller
@RequestMapping("/index")
public class IndexController extends AbstractController {
	
	@Resource
	private IImgService imgService ;
	
	@Resource
	private INewsService newService ;
	
	@Resource
	private IUserService userService;
	
	@RequestMapping("/turnToIndex/{pageNum}")
	public String trunToIndex(@PathVariable int pageNum,Model model){
		Img img = new Img() ;
//		设置为1表示自查询上首页的图片
		img.setIs_index("1");
		List<Img> imgs = imgService.findByIndex(img) ;
		model.addAttribute("imgs", imgs) ;
		//查询最新的新闻信息。
		News ns = new News() ;
		super.setPageNum(pageNum) ;
		super.setRowCount(newService.findAllCount()) ;
		super.getIndex() ;
		ns.setStartIndex(super.getStartIndex()) ;
		ns.setEndIndex(super.getEndIndex()) ;
		List<News> news = newService.findAll(ns) ;
		model.addAttribute("news", news) ;
		model.addAttribute("currentpage", pageNum) ;
		
		return "../../index" ;
	}
	
	@RequestMapping("/checkEmail")
	public void checkEmail(User u,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8") ;
		response.setContentType("html/text") ;
		int count = userService.findAllCount(u) ;
		if(count ==0){
			response.getWriter().print("true") ;
		}else {
			response.getWriter().print("false") ;
		}
	}
}