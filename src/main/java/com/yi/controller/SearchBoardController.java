package com.yi.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yi.domain.BoardVO;
import com.yi.domain.PageMaker;
import com.yi.domain.SearchCriteria;
import com.yi.service.BoardService;
import com.yi.util.UploadFileUtils;

@Controller
@RequestMapping("/sboard/*")//command에 항상 /sboard/로 시작한다.
public class SearchBoardController {
	
	@Autowired
	BoardService service;
	
	@Resource(name = "uploadPath")
	String uploadPath;
	
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
	public String registerPost(BoardVO vo, List<MultipartFile> imageFiles) throws Exception {
		System.out.println("register Post --------" + vo);
		
		ArrayList<String> fullNames = new ArrayList<String>();
		for(MultipartFile file : imageFiles) {
			System.out.println(file.getOriginalFilename());
			System.out.println(file.getSize());
			
			//upload처리
			String savedName = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
			fullNames.add(savedName);
		}
		vo.setFiles(fullNames);
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
		vo.setViewcnt(vo.getViewcnt()-1);
		service.updateViewcnt(vo);
		model.addAttribute("board", vo);
		model.addAttribute("page", cri.getPage());
		model.addAttribute("searchType", cri.getSearchType());
		model.addAttribute("keyword", cri.getKeyword());
		return "/sboard/modifyPage";
	}
	
	@RequestMapping(value = "/modifyPage", method = RequestMethod.POST)
	public String modifyPage(BoardVO vo, SearchCriteria cri, Model model, String[] imgFile, List<MultipartFile> imageFiles) throws Exception {
		
		//체크된 이미지 모두 지우기
		if(imgFile == null && imageFiles.get(0).getSize()==0) {//체크된 이미지와 추가이미지가 없을 경우 처리 조건절
			return "redirect:/sboard/readPage?bno="+vo.getBno()+"&page="+cri.getPage()+"&searchType="+cri.getSearchType();
		}else if(imgFile != null) {
			for(int i=0;i<imgFile.length;i++) {
				service.delAttach(imgFile[i]);//DB에서 삭제
				UploadFileUtils.deleteFile(uploadPath, imgFile[i]);//이미지가 저장된 폴더에서 파일 삭제
			}
		}
		ArrayList<String> fullNames = new ArrayList<String>();
		for(MultipartFile file : imageFiles) {
			if(file.getSize()==0) {//체크된 이미지만 삭제할 경우 처리 조건절
				break;
			}
			System.out.println(file.getOriginalFilename()+"오리지날");
			System.out.println(file.getSize()+"사이즈");
			
			//upload처리
			String savedName = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
			fullNames.add(savedName);
		}
		vo.setFiles(fullNames);
		service.update(vo);
		
		model.addAttribute("board", vo);
		model.addAttribute("keyword", cri.getKeyword());
		return "redirect:/sboard/readPage?bno="+vo.getBno()+"&page="+cri.getPage()+"&searchType="+cri.getSearchType();
		
	}
	
	@RequestMapping(value = "/removePage", method = RequestMethod.GET)
	public String remove(int bno, SearchCriteria cri, Model model) throws Exception {
		List<String> imgFile = service.selFullName(bno);
		for(int i=0;i<imgFile.size();i++) {
			UploadFileUtils.deleteFile(uploadPath, imgFile.get(i));//이미지가 저장된 폴더에서 파일 삭제
		}
		service.delAllAttach(bno);
		service.delete(bno);
		
		model.addAttribute("page", cri.getPage());
		model.addAttribute("searchType", cri.getSearchType());
		model.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/sboard/listPage";
	}
	
	@ResponseBody
	@RequestMapping(value = "displayFile", method = RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(String filename){
		ResponseEntity<byte[]> entity = null;
		
		System.out.println("displayFile -------------" + filename);
		InputStream in = null;
		try {
			in = new FileInputStream(uploadPath+filename);
			String format = filename.substring(filename.lastIndexOf(".")+1);//확장자
			MediaType mType = null;
			if(format.equalsIgnoreCase("png")) {
				mType = MediaType.IMAGE_PNG;
			}else if(format.equalsIgnoreCase("jpg")||format.equalsIgnoreCase("jpeg")) {
				mType = MediaType.IMAGE_JPEG;
			}else if(format.equalsIgnoreCase("gif")) {
				mType = MediaType.IMAGE_GIF;
			}
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(mType);
			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}
		
		
		return entity;
	}
	
}
