package com.yi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yi.domain.BoardVO;
import com.yi.domain.PageMaker;
import com.yi.domain.SearchCriteria;
import com.yi.service.BoardService;

@Controller
@RequestMapping("/sboard/*")//command에 항상 /sboard/로 시작한다.
public class SearchBoardController {
	
	@Autowired
	BoardService service;
	
	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public String listPage(SearchCriteria cri, Model model) throws Exception {
		System.out.println("--------------"+cri);
		List<BoardVO> list =  service.listSearchCriteria(cri);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.totalSearchCount(cri));
		
		model.addAttribute("cri", cri);
		model.addAttribute("list", list);
		model.addAttribute("pageMaker", pageMaker);
		return "/sboard/listPage";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerGet() {
		return "/sboard/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPost(BoardVO vo) throws Exception {
		System.out.println("register Post --------" + vo);
		
		service.create(vo);
		
		return "redirect:/sboard/listPage";
	}
	
	@RequestMapping(value = "/readPage", method = RequestMethod.GET)
	public String readPage(int bno, SearchCriteria cri, Model model) throws Exception {
		BoardVO vo = service.readByNo(bno);
		vo.setViewcnt(vo.getViewcnt()+1);
		service.updateViewcnt(vo);
		model.addAttribute("board", vo);
		model.addAttribute("page", cri.getPage());
		model.addAttribute("searchType", cri.getSearchType());
		model.addAttribute("keyword", cri.getKeyword());
		return "/sboard/readPage";
	}
	
	@RequestMapping(value = "/modifyPage", method = RequestMethod.GET)
	public String modifyPage(int bno, SearchCriteria cri, Model model) throws Exception {
		BoardVO vo = service.readByNo(bno);
		model.addAttribute("board", vo);
		model.addAttribute("page", cri.getPage());
		model.addAttribute("searchType", cri.getSearchType());
		model.addAttribute("keyword", cri.getKeyword());
		return "/sboard/modifyPage";
	}
	
	@RequestMapping(value = "/modifyPage", method = RequestMethod.POST)
	public String modifyPage(BoardVO vo, SearchCriteria cri, Model model) throws Exception {
		System.out.println(vo);
		service.update(vo);
		model.addAttribute("board", vo);
		model.addAttribute("keyword", cri.getKeyword());
		return "redirect:/sboard/readPage?bno="+vo.getBno()+"&page="+cri.getPage()+"&searchType="+cri.getSearchType();
		
	}
	
	@RequestMapping(value = "/removePage", method = RequestMethod.GET)
	public String remove(int bno, SearchCriteria cri, Model model) throws Exception {
		service.delete(bno);
		
		model.addAttribute("page", cri.getPage());
		model.addAttribute("searchType", cri.getSearchType());
		model.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/sboard/listPage";
	}
}
