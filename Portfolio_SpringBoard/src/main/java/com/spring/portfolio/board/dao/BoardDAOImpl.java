package com.spring.portfolio.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.portfolio.board.vo.ArticleVO;
import com.spring.portfolio.member.vo.MemberVO;
@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO{
	@Autowired
	private SqlSession sqlSession;
	
	
	@Override
	public List selectAllArticlesList() throws DataAccessException {
		//ID가 selectAllArticlesList인 SQL문 요청한다
		List<ArticleVO> articlelist = sqlSession.selectList("mapper.board.selectAllArticlesList");
		return articlelist;
	}


	@Override
	public int insertNewArticle(Map articleMap) throws DataAccessException {
		//새 글에 대한 글 번호를 가져온다
		int articleNO = selectNewArticleNO();
		//글 번호를 articleMap에 저장한다
		articleMap.put("articleNO", articleNO);
		//id에 대한 insert문을 호출하면서 articleMap을 전달한다
		sqlSession.insert("mapper.board.insertNewArticle",articleMap);
		return articleNO;
	}
	 
	@Override
	public MemberVO loginById(MemberVO memberVO) throws DataAccessException {
		MemberVO vo = sqlSession.selectOne("mapper.member.loginById",memberVO);
		return vo;
	}


	private int selectNewArticleNO() throws DataAccessException{
		return sqlSession.selectOne("mapper.board.selectNewArticleNO");
	}


	@Override
	public ArticleVO selectArticle(int articleNO) throws DataAccessException {
		return sqlSession.selectOne("mapper.board.selectArticle", articleNO);
	}


	@Override
	public void updateArticle(Map articleMap) throws DataAccessException {
		sqlSession.selectOne("mapper.board.updateArticle", articleMap);
		
	}


	@Override
	public void deleteArticle(int articleNO) throws DataAccessException {
		sqlSession.selectOne("mapper.board.deleteArticle", articleNO);
		
	}
	
	
	
	

}
