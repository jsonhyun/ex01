package com.yi.persistence;

import java.util.List;

import com.yi.domain.Criteria;
import com.yi.domain.ReplyVO;

public interface ReplyDAO {
	public void insert(ReplyVO vo) throws Exception;
	public List<ReplyVO> list(int bno) throws Exception;
	public void update(ReplyVO vo) throws Exception;
	public void delete(int rno) throws Exception;
	public List<ReplyVO> listPage(int bno, Criteria cri) throws Exception;
	public int totalCount(int bno) throws Exception;
	public ReplyVO selectByRno(int rno) throws Exception;
}
