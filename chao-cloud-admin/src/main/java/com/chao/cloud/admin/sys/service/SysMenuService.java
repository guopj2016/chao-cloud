package com.chao.cloud.admin.sys.service;

import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chao.cloud.admin.sys.dal.entity.SysMenu;
import com.chao.cloud.admin.sys.domain.dto.MenuLayuiDTO;

import cn.hutool.core.collection.CollUtil;

/**
 * @功能：
 * @author： 超君子
 * @时间：2019-05-28
 * @version 1.0.0
 */
public interface SysMenuService extends IService<SysMenu> {

	List<Integer> SHOW_MENU_TYPE = CollUtil.toList(0, 1);// 1/2 级菜单

	/**
	 * 获取用户权限
	 * @param userId
	 * @return
	 */
	Set<String> listPerms(Long userId);

	/**
	 * 获取用户左侧菜单
	 * @param userId
	 * @return
	 */
	List<MenuLayuiDTO> listMenuLayuiTree(Long userId);
}