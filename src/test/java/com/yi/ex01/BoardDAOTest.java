package com.yi.ex01;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.persistence.BoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BoardDAOTest {
	
	@Autowired
	private BoardDAO dao;
	
//	@Test
	public void test01DAO() {
		System.out.println(dao);
	}
	
//	@Test
	public void test02Insert() throws Exception {
		BoardVO vo = new BoardVO();
		vo.setTitle("게시글을 등록합니다.");
		vo.setContent("게시글의 내용입니다.");
		vo.setWriter("user00");
		dao.insert(vo);
	}
	
//	@Test
	public void test03ReadByNo() throws Exception {
		dao.readByNo(3);
	}
	
//	@Test
	public void test04List() throws Exception {
		dao.list();
	}
	
//	@Test
	public void test05Update() throws Exception {
		BoardVO vo = new BoardVO();
		vo.setTitle("게시글을 수정합니다.");
		vo.setContent("수정된 게시글의 내용입니다.");
		vo.setBno(3);
		dao.update(vo);
	}
	
//	@Test
	public void test06Delete() throws Exception {
		dao.delete(3);
	}
	
//	@Test
	public void test07ListPage() throws Exception {
		dao.listPage(3);
	}
	
	@Test
	public void test08ListCriteria() throws Exception {
		Criteria cri = new Criteria(); //페이지 번호, 페이지당 display 게시글 갯수
		cri.setPage(1);
		cri.setPerPageNum(20);
		dao.listCriteria(cri);
		
	}
}
