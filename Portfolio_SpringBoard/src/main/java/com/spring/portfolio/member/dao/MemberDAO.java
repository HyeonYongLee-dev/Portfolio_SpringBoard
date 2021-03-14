package com.spring.portfolio.member.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.spring.portfolio.member.vo.MemberVO;

public interface MemberDAO {
	
	public List<MemberVO> selectAllMemberList() throws DataAccessException;
	public int insertMember(MemberVO memberVO) throws DataAccessException;
	public int deleteMember(String id) throws DataAccessException;
	public MemberVO loginByid(MemberVO memberVO) throws DataAccessException;
}
