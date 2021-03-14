package com.spring.portfolio.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.spring.portfolio.board.vo.ArticleVO;
import com.spring.portfolio.member.vo.MemberVO;

public interface BoardDAO {
	public List selectAllArticlesList() throws DataAccessException;
	public MemberVO loginById(MemberVO memberVO) throws DataAccessException;
	public int insertNewArticle(Map articleMap) throws DataAccessException;
	public ArticleVO selectArticle(int articleNO) throws DataAccessException;
	public void updateArticle(Map articleMap) throws DataAccessException;
	public void deleteArticle(int articleNO) throws DataAccessException;
	
	
}
