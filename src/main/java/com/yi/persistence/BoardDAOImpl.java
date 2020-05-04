package com.yi.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.SearchCriteria;

@Repository
public class BoardDAOImpl implements BoardDAO {

	private static final String namespace = "mapper.BoardMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void insert(BoardVO vo) throws Exception {
		sqlSession.insert(namespace+"insert", vo);
	}

	@Override
	public BoardVO readByNo(int bno) throws Exception {
		return sqlSession.selectOne(namespace+"readByNo", bno);
	}

	@Override
	public List<BoardVO> list() throws Exception {
		return sqlSession.selectList(namespace+"list");
	}

	@Override
	public void update(BoardVO vo) throws Exception {
		sqlSession.update(namespace+"update", vo);

	}

	@Override
	public void delete(int bno) throws Exception {
		sqlSession.delete(namespace+"delete", bno);

	}

	@Override
	public void updateViewcnt(BoardVO vo) throws Exception {
		sqlSession.update(namespace+"updateViewcnt", vo);
		
	}

	@Override
	public List<BoardVO> listPage(int page) throws Exception {
		//1->0, 2->10, 3->20
		if(page < 0) {
			page = 1;
		}
		
		page = (page - 1) * 10;
				
		return sqlSession.selectList(namespace+"listPage", page);
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		return sqlSession.selectList(namespace+"listCriteria", cri);
	}

	@Override
	public int totalCount() throws Exception {
		return sqlSession.selectOne(namespace+"totalCount");
	}

	@Override
	public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception {
		return sqlSession.selectList(namespace+"listSearchCriteria", cri);
	}

	@Override
	public int totalSearchCount(SearchCriteria cri) throws Exception {
		return sqlSession.selectOne(namespace+"totalSearchCount", cri);
	}

	@Override
	public void updateReplyCnt(int amount, int bno) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("amount", amount);
		map.put("bno", bno);
		sqlSession.update(namespace+"updateReplyCnt", map);
	}

	@Override
	public void addAttach(String fullname) throws Exception {
		sqlSession.insert(namespace+"addAttach", fullname);
	}

	@Override
	public BoardVO readAndAttachByBno(int bno) throws Exception {
		return sqlSession.selectOne(namespace+"readAndAttachByBno", bno);
	}

	@Override
	public void delAttach(String fullname) throws Exception {
		sqlSession.delete(namespace+"delAttach", fullname);
		
		
	}

	@Override
	public void modAttach(String fullname, int bno) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fullname", fullname);
		map.put("bno", bno);
		sqlSession.insert(namespace+"modAttach", map);
		
	}

	@Override
	public void delAllAttach(int bno) throws Exception {
		sqlSession.delete(namespace+"delAllAttach", bno);
		
	}

	@Override
	public List<String> selFullName(int bno) throws Exception {
		return sqlSession.selectList(namespace+"selFullName", bno);
		
	}

}
