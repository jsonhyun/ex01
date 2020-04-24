package com.yi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yi.domain.Criteria;
import com.yi.domain.ReplyVO;
import com.yi.persistence.ReplyDAO;

@Service
public class ReplyService {
	
	@Autowired
	ReplyDAO dao;
	
	public void insert(ReplyVO vo) throws Exception{
		dao.insert(vo);
	}
	public List<ReplyVO> list(int bno) throws Exception{
		return dao.list(bno);
	}
	public void update(ReplyVO vo) throws Exception{
		dao.update(vo);
	}
	public void delete(int rno) throws Exception{
		dao.delete(rno);
	}
	public List<ReplyVO> listPage(int bno, Criteria cri) throws Exception{
		return dao.listPage(bno, cri);
	}
	public int totalCount(int bno) throws Exception{
		return dao.totalCount(bno);
	}
}
