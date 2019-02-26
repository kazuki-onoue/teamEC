package com.internousdev.magenda.action;

import java.sql.SQLException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.magenda.dao.UserInfoDAO;
import com.internousdev.magenda.dto.UserInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class MyPageAction extends ActionSupport implements SessionAware{

	private Map<String, Object> session;
	private String message;

	public String execute() throws SQLException{

		if(!session.containsKey("mCategoryDTOList")){
			return "timeout";
		}

			UserInfoDAO userInfoDAO = new UserInfoDAO();
			UserInfoDTO userInfoDTO = new UserInfoDTO();
			userInfoDTO = userInfoDAO.getUserInfo(String.valueOf(session.get("loginId")));
			if(userInfoDTO!=null && session.get("logined").equals(1)){
				session.put("message","");
				session.put("familyName", userInfoDTO.getFamilyName());
				session.put("firstName",userInfoDTO.getFirstName());
				session.put("familyNameKana",userInfoDTO.getFamilyNameKana());
				session.put("firstNameKana",userInfoDTO.getFirstNameKana());
				session.put("sex",userInfoDTO.getSex());
				session.put("email",userInfoDTO.getEmail());
			}else{
				setMessage("ユーザー情報がありません。");
				session.put("message", message);
			}
			return SUCCESS;
	}

	public String getMessage(){
		return message;
	}
	public void setMessage(String message) {
		this.message=message;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
