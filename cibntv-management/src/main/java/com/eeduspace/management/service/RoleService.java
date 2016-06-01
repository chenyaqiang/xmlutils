package com.eeduspace.management.service;

import java.util.List;

import com.eeduspace.management.model.RoleModel;

/**角色接口
 * @author songwei
 *
 */
public interface RoleService {
	
	/**查询角色列表
	 * @param status
	 * @return
	 */
	List<RoleModel> getRoleModels(String status);
	
	/**增加角色
	 * @param roleModel
	 * @return
	 */
	RoleModel saveModel(RoleModel roleModel);
	
}