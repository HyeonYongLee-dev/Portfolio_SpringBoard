package com.spring.portfolio.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.portfolio.member.vo.MemberVO;

@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO{
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<MemberVO> selectAllMemberList() throws DataAccessException {
		//MemberVO를 자료형으로 갖는 List인 memberList 초기화
		List<MemberVO> membersList = null;
		//membersList에 주입된 sqlSession 빈으로 selectList()메서드를 호출하면서 sql문의 id값을 전달한다
		membersList = sqlSession.selectList("mapper.member.selectAllMemberList");
		return membersList;
	}

	@Override
	public int insertMember(MemberVO memberVO) throws DataAccessException {
		//변수 result에 sqlSession 빈으로 insert()메서드를 호출하면서 sql문의 id와 memberVO를 전달한다
		int result = sqlSession.insert("mapper.member.insertMember", memberVO);
		return result;
	}

	@Override
	public int deleteMember(String id) throws DataAccessException {
		//변수 result에 sqlSession 빈으로 delete()메서드를 호출하면서 sql문의 id와 id를 전달한다
		int result = sqlSession.delete("mapper.member.deleteMember", id);
		return result;
	}

	@Override
	public MemberVO loginByid(MemberVO memberVO) throws DataAccessException {
		//메서드 호출 시 전달된 memberVO를 SQL문으로 전달해 ID와 비밀번호에 대한 회원 정보를 MemberVO 객체로 반환한다
		MemberVO vo = sqlSession.selectOne("mapper.member.loginById", memberVO);
		return vo;
	}
	
	
}
