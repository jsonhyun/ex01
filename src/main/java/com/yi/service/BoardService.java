package com.yi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.SearchCriteria;
import com.yi.persistence.BoardDAO;

@Service
public class BoardService {
	
	@Autowired
	BoardDAO dao;
	
	@Transactional //커넥션 한번만 열고 닫으며 전부 완료 못했을 경우 자동 rollback 처리해줌
	public void create(BoardVO vo) throws Exception {
		dao.insert(vo);
		
		// 파일 이름을 tbl_attach 추가
		for(String file: vo.getFiles()) {
			dao.addAttach(file);
		}
	}
	
	public BoardVO readByNo(int bno) throws Exception {
		return dao.readAndAttachByBno(bno);
	}
	
	public List<BoardVO> list() throws Exception {
		return dao.list();
	}
	
	@Transactional
	public void update(BoardVO vo) throws Exception {
		dao.update(vo);
		
		for(String file: vo.getFiles()) {
			dao.modAttach(file, vo.getBno());
		}
	}
	
	public void updateViewcnt(BoardVO vo) throws Exception {
		dao.updateViewcnt(vo);
	}
	
	public void delete(int bno) throws Exception {
		dao.delete(bno);
	}
	
	public List<BoardVO> listCriteria(Criteria cri) throws Exception{
		return dao.listCriteria(cri);
	}
	
	public int totalCount() throws Exception {
		return dao.totalCount();
	}
	
	public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception{
		return dao.listSearchCriteria(cri);
	}
	
	public int totalSearchCount(SearchCriteria cri) throws Exception {
		return dao.totalSearchCount(cri);
	}
	
	public void delAttach(String fullname) throws Exception {
		dao.delAttach(fullname);
	}
	
	public void delAllAttach(int bno) throws Exception {
		dao.delAllAttach(bno);
	}
	
	public List<String> selFullName(int bno) throws Exception{
		return dao.selFullName(bno);
	}
}
