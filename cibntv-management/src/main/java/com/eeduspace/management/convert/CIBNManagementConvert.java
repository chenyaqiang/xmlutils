package com.eeduspace.management.convert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Null;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.eeduspace.management.model.ManagerModel;
import com.eeduspace.management.model.PermissionModel;
import com.eeduspace.management.model.RoleModel;
import com.eeduspace.management.persist.po.ManagerPo;
import com.eeduspace.management.persist.po.PermissionPo;
import com.eeduspace.management.persist.po.RolePo;


/**
 * Author: dingran
 * Date: 2016/4/20
 * Description: model与实体转换
 */

public class CIBNManagementConvert {
	//    private static final Logger logger = LoggerFactory.getLogger(CIBNManagementConvert.class);

	public static ManagerModel fromManagerPo(ManagerPo managerPo){
		ManagerModel mm = new ManagerModel();

		return mm;
	}
	public static ManagerPo fromManagerModel(ManagerModel managerModel){
		ManagerPo po = new ManagerPo();

		return po;
	}
	public static RolePo fromRoleModel(RoleModel roleModel){
		RolePo rolePo = new RolePo();
		if (!StringUtils.isEmpty(roleModel)) {
			rolePo.setDescription(roleModel.getDescription());
			rolePo.setName(roleModel.getName());
			rolePo.setStatus(roleModel.getStatus());
			rolePo.setType(roleModel.getType());
			rolePo.setUpdateDate(StringUtils.isEmpty(rolePo.getUpdateDate()) ? null : rolePo.getUpdateDate());
		}
		return rolePo;
	}
	public static RoleModel fromRolePo(RolePo rolePo){
		RoleModel roleModel = new RoleModel();
		if (!StringUtils.isEmpty(rolePo)) {
			roleModel.setId(rolePo.getId());
			roleModel.setUuid(rolePo.getUuid());
			roleModel.setDescription(rolePo.getDescription());
			roleModel.setName(rolePo.getName());
			roleModel.setStatus(rolePo.getStatus());
			roleModel.setType(rolePo.getType());
			roleModel.setCreateDate(StringUtils.isEmpty(rolePo.getCreateDate()) ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rolePo.getCreateDate()));
			roleModel.setUpdateDate(StringUtils.isEmpty(rolePo.getUpdateDate()) ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rolePo.getUpdateDate()));
		}
		return roleModel;
	}
	public static List<RoleModel> fromRolePos(List<RolePo> rolePos){
		List<RoleModel> roleModels = new ArrayList<RoleModel>();
		if (rolePos.size() > 0) {
			for (RolePo pp : rolePos) {
				roleModels.add(fromRolePo(pp));
			}
		}
		return roleModels;
	}
	public static PermissionModel fromPermissionPo(PermissionPo permissionPo){
		PermissionModel permissionModel = new PermissionModel();
		if(!StringUtils.isEmpty(permissionPo)){
			permissionModel.setId(permissionPo.getId());
			permissionModel.setUuid(permissionPo.getUuid());
			permissionModel.setDescription(permissionPo.getDescription());
			permissionModel.setFunctionId(permissionPo.getFunctionId());
			permissionModel.setGroups(permissionPo.getGroups());
			permissionModel.setName(permissionPo.getName());
			permissionModel.setStatus(permissionPo.getStatus());
			permissionModel.setType(permissionPo.getType());
			permissionModel.setCreateDate(StringUtils.isEmpty(permissionPo.getCreateDate()) ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(permissionPo.getCreateDate())) ;
			permissionModel.setUpdateDate(StringUtils.isEmpty(permissionPo.getUpdateDate()) ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(permissionPo.getUpdateDate()));
		}
		return permissionModel;
	}
	public static List<PermissionModel> fromPermissionPos(List<PermissionPo> permissionPos){
		List<PermissionModel> permissionModels = new ArrayList<PermissionModel>();
		if (permissionPos.size() > 0) {
			for (PermissionPo pp : permissionPos) {
				permissionModels.add(fromPermissionPo(pp));
			}
		}
		return permissionModels;
	}
}
