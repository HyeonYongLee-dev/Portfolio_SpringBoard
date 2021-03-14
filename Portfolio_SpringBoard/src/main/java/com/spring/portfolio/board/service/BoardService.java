package com.spring.portfolio.board.service;

import java.util.List;
import java.util.Map;

import com.spring.portfolio.board.vo.ArticleVO;

public interface BoardService {
	public List<ArticleVO> listArticles();

	public int addNewArticle(Map articleMap) throws Exception;

	public ArticleVO viewArticle(int articleNO) throws Exception;

	public void modArticle(Map articleMap) throws Exception;

	public void removeArticle(int articleNO);
}
