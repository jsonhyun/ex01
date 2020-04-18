package com.yi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.PageMaker;
import com.yi.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	BoardService service;
	
	@RequestMapping(value = "/board/register", method = RequestMethod.GET)
	public String registerGet() {
		return "/board/register";
	}
	
	@RequestMapping(value = "/board/register", method = RequestMethod.POST)
	public String registerPost(BoardVO vo) throws Exception {
		System.out.println("register Post --------" + vo);
		
		service.create(vo);
		
		return "redirect:/board/list";
	}
	
	@RequestMapping(value = "/board/list", method = RequestMethod.GET)
	public String list(Model model) throws Exception {
		List<BoardVO> list = service.list();
		model.addAttribute("list", list);
		
		return "/board/list";
	}
	
	@RequestMapping(value = "/board/read", method = RequestMethod.GET)
	public String read(int bno, Model model) throws Exception {
		BoardVO vo = service.readByNo(bno);
		vo.setViewcnt(vo.getViewcnt()+1);
		service.updateViewcnt(vo);
		model.addAttribute("board", vo);
		return "/board/read";
	}
	
	@RequestMapping(value = "/board/remove", method = RequestMethod.GET)
	public String remove(int bno, Model model) throws Exception {
		service.delete(bno);
		
		return "redirect:/board/list";
	}
	
	@RequestMapping(value = "/board/modify", method = RequestMethod.GET)
	public String modify(int bno, Model model) throws Exception {
		BoardVO vo = service.readByNo(bno);
		model.addAttribute("board", vo);
		return "/board/modify";
	}
	
	@RequestMapping(value = "/board/modify", method = RequestMethod.POST)
	public String modify(BoardVO vo, Model model) throws Exception {
		System.out.println(vo);
		service.update(vo);
		model.addAttribute("board", vo);
		return "redirect:/board/read?bno="+vo.getBno();
		
	}
	@RequestMapping(value = "/board/listPage", method = RequestMethod.GET)
	public String listPage(Criteria cri, Model model) throws Exception {
		List<BoardVO> list =  service.listCriteria(cri);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.totalCount());
		
		model.addAttribute("list", list);
		model.addAttribute("pageMaker", pageMaker);
		return "/board/listPage";
	}
	
	@RequestMapping(value = "/board/readPage", method = RequestMethod.GET)
	public String readPage(int bno, Criteria cri, Model model) throws Exception {
		BoardVO vo = service.readByNo(bno);
		vo.setViewcnt(vo.getViewcnt()+1);
		service.updateViewcnt(vo);
		model.addAttribute("board", vo);
		model.addAttribute("cri", cri);
		return "/board/readPage";
	}
	
	@RequestMapping(value = "/board/removePage", method = RequestMethod.GET)
	public String remove(int bno, Criteria cri, Model model) throws Exception {
		service.delete(bno);
		
		return "redirect:/board/listPage?page="+cri.getPage();
	}
	
	@RequestMapping(value = "/board/modifyPage", method = RequestMethod.GET)
	public String modifyPage(int bno, Criteria cri, Model model) throws Exception {
		BoardVO vo = service.readByNo(bno);
		model.addAttribute("board", vo);
		model.addAttribute("cri", cri);
		return "/board/modifyPage";
	}
	
	@RequestMapping(value = "/board/modifyPage", method = RequestMethod.POST)
	public String modifyPage(BoardVO vo, Criteria cri, Model model) throws Exception {
		System.out.println(vo);
		service.update(vo);
		model.addAttribute("board", vo);
		return "redirect:/board/readPage?bno="+vo.getBno()+"&page="+cri.getPage();
		
	}
}
