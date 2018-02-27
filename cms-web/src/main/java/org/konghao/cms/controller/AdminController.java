package org.konghao.cms.controller;

import javax.servlet.http.HttpSession;

import org.konghao.cms.auth.AuthClass;
import org.konghao.cms.auth.AuthMethod;
import org.konghao.cms.web.CmsSessionContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AuthClass("login")
public class AdminController {

	@RequestMapping("/admin")
	@AuthMethod
	public String index() {
		return "admin/index";
	}
	
	@AuthMethod
	@RequestMapping("/admin/logout")
	public String logout(HttpSession session) {
		CmsSessionContext.removeSession(session);
		session.invalidate();
		return "redirect:/login";
	}
}
