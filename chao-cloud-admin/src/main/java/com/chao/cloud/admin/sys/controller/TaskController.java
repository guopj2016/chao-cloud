package com.chao.cloud.admin.sys.controller;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chao.cloud.admin.sys.dal.entity.SysTask;
import com.chao.cloud.admin.sys.log.AdminLog;
import com.chao.cloud.admin.sys.service.SysTaskService;
import com.chao.cloud.common.entity.Response;
import com.chao.cloud.common.entity.ResponseResult;
import com.chao.cloud.common.extra.mybatis.generator.menu.MenuEnum;
import com.chao.cloud.common.extra.mybatis.generator.menu.MenuMapping;

import cn.hutool.core.util.StrUtil;

/**
 * 
 * @功能：
 * @author： 薛超
 * @时间：2019年3月13日
 * @version 1.0.0
 */
@RequestMapping("/sys/task")
@Controller
@Validated
@MenuMapping
public class TaskController extends BaseController {

	String prefix = "sys/task";

	@Autowired
	private SysTaskService sysTaskService;

	@MenuMapping(value = "计划任务", type = MenuEnum.MENU)
	@RequiresPermissions("sys:task:list")
	@RequestMapping
	String task() {
		return prefix + "/list";
	}

	@AdminLog(AdminLog.STAT_PREFIX + "任务列表")
	@MenuMapping("列表")
	@RequiresPermissions("sys:task:list")
	@RequestMapping("/list")
	@ResponseBody
	public Response<IPage<SysTask>> list(Page<SysTask> page, String jobName) {
		// 查询列表数据
		LambdaQueryWrapper<SysTask> queryWrapper = Wrappers.<SysTask>lambdaQuery();
		if (StrUtil.isNotBlank(jobName)) {
			queryWrapper.like(SysTask::getJobName, jobName);
		}
		return ResponseResult.getResponseResult(sysTaskService.page(page, queryWrapper));
	}

	@MenuMapping("增加")
	@RequiresPermissions("sys:task:add")
	@RequestMapping("/add")
	String add() {
		return prefix + "/add";
	}

	@MenuMapping("编辑")
	@RequiresPermissions("sys:task:edit")
	@RequestMapping("/edit/{id}")
	String edit(@PathVariable("id") Long id, Model model) {
		SysTask task = sysTaskService.getById(id);
		model.addAttribute("task", task);
		return prefix + "/edit";
	}

	/**
	 * 保存
	 */
	@RequiresPermissions("sys:task:add")
	@RequestMapping("/save")
	@ResponseBody
	public Response<String> save(SysTask task) {
		if (sysTaskService.save(task)) {
			return ResponseResult.ok();
		}
		return ResponseResult.error();
	}

	/**
	 * 修改
	 */
	@RequiresPermissions("sys:task:edit")
	@RequestMapping("/update")
	@ResponseBody
	public Response<String> update(SysTask task) {
		sysTaskService.updateById(task);
		return ResponseResult.ok();
	}

	/**
	 * 删除
	 */
	@MenuMapping("删除")
	@RequiresPermissions("sys:task:remove")
	@RequestMapping("/remove")
	@ResponseBody
	public Response<String> remove(@NotNull Long id) {
		if (sysTaskService.remove(id) > 0) {
			return ResponseResult.ok();
		}
		return ResponseResult.error();
	}

	/**
	 * 删除
	 */
	@MenuMapping("批量删除")
	@RequiresPermissions("sys:task:batchRemove")
	@RequestMapping("/batchRemove")
	@ResponseBody
	public Response<String> remove(@Size(min = 1) @RequestParam("ids[]") Long[] ids) {
		sysTaskService.batchRemove(ids);
		return ResponseResult.ok();
	}

	@MenuMapping("改变任务状态")
	@RequiresPermissions("sys:task:changeJobStatus")
	@RequestMapping("/changeJobStatus")
	@ResponseBody
	public Response<String> changeJobStatus(@NotNull Long id, String cmd) {
		sysTaskService.changeStatus(id, cmd);
		return ResponseResult.getResponseResult(StrUtil.format("任务{}成功", cmd));
	}

}
