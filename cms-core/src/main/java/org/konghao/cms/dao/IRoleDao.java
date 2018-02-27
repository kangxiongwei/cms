package org.konghao.cms.dao;

import java.util.List;

import org.konghao.basic.dao.IBaseDao;
import org.konghao.cms.model.Role;

public interface IRoleDao extends IBaseDao<Role> {
	public List<Role> listRole();
	public void deleteRoleUsers(int rid);
}
