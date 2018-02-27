package org.konghao.cms.dao;


import java.util.List;

import org.konghao.basic.dao.BaseDao;
import org.konghao.cms.model.Role;
import org.springframework.stereotype.Repository;

@Repository("roleDao")
public class RoleDao extends BaseDao<Role> implements IRoleDao {

	@Override
	public List<Role> listRole() {
		return this.list("from Role");
	}

	@Override
	public void deleteRoleUsers(int rid) {
		this.updateByHql("delete UserRole ur where ur.role.id=?",rid);
	}


}
