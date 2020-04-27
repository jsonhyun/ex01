package com.yi.persistence;

import java.util.List;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.SearchCriteria;

public interface BoardDAO {
	public void insert(BoardVO vo) throws Exception;
	public BoardVO readByNo(int bno) throws Exception;
	public List<BoardVO> list() throws Exception;
	public void update(BoardVO vo) throws Exception;
	public void updateViewcnt(BoardVO vo) throws Exception;
	public void delete(int bno) throws Exception;
	
	public List<BoardVO> listPage(int page) throws Exception;
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;
	public int totalCount() throws Exception;
	
	public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception;
	public int totalSearchCount(SearchCriteria cri) throws Exception;
	
	public void updateReplyCnt(int amount, int bno) throws Exception;
}
