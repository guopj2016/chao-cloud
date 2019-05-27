package com.chao.cloud.admin.system.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @功能：返回值（将被删除）
 * @author： 薛超
 * @时间： 2019年5月27日
 * @version 1.0.0
 */
@Deprecated
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public R() {
		put("code", 0);
		put("msg", "操作成功");
	}

	public static R error() {
		return error(1, "操作失败");
	}

	public static R error(String msg) {
		return error(500, msg);
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}

	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R ok() {
		return new R();
	}

	public static R page(List<?> data, int count) {
		return ok().put("data", data).put("count", count);
	}

	public static R page(List<?> data) {
		return ok().put("data", data);
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}