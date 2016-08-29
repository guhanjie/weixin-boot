package com.guhanjie.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加
 * @author a
 *
 */
public enum WebExceptionEnum {
		// 0-99 通用 
		UNDEFINED_ERROR(10000, "undefined.error","未知异常"),
		SYSTEM_ERROR(10001, "system.error", "系统异常"),
		ACCESS_FORBIDDEN(10002, "access.forbidden","没有权限"),
		UNIMPLEMENT(10003, "unimplement", "功能未实现"),
		SYSTEM_NO_SUPPORT(10004, "system.not.support","系统不支持"),
		VALIDATE_ERROR(10005, "validate.error","数据校验不正确"),
		PARAMETER_ABSENT(10006, "parameter.absent","缺少必须的参数"),
		SIGNATURE_INVALID(10007, "signature.invalid", "无效的签名"),
		DATA_NOT_FOUND(10008, "data.not.found","数据不存在"),
		DATA_HAS_EXIST(10009, "data.has.exist","数据已存在"),
		DATA_DUPLICATION(10010, "data.duplication","数据重复"),
		DATA_NOT_WELL(10011, "data.not.well","数据不完整"),
		DATA_HAS_USED(10012, "data.has.used","数据已被使用"),
		RPC_ERROR(10013, "rpc.error","远程调用异常"),
		
		// 1XX 用户操作相关
		USER_LOCKED(10100, "user.locked","用户被锁定"),
		FRENQUENCY_TO_FAST(10101,"frequency.too.fast","操作太频繁"), 
		USER_NAME_NOT_EXIST(10102, "user.name.not.exist", "用户名不能为空"),
		USER_PASSWORD_NOT_EXIST(10103, "user.password.not.exist", "用户密码不能为空"),
		USER_EMAIL_NOT_EXIST(10104, "user.email.not.exist", "用户邮箱不能为空"),
		
		// 2XX  商品相关
		PRODUCT_NOT_SALE(10200, "suite.not.sale","商品不能售卖"),
		PRODUCT_HAS_OFFLINE(10201, "suite.has.offline","商品已下线"),
		PRODUCT_HAS_DELETED(10202, "suite.has.deleted","商品已删除"),
		PRODUCT_NOT_WELL(10203, "suite.not.well","商品结构不完整"),				
		
		// 3XX 购买相关
		ORDER_ERROR(10300, "order exception", "订单错误"),
		ORDER_AUTHORIZATION_ERROR(10301, "auth.apply.error", "订单写入授权失败"),
		ORDER_CANCEL_ERROR(10302, "order.cancel.error", "订单不能取消"),
		CAN_NOT_FIND_ORDER(10303, "can't find order", "找不到对应的订单"),
		CRM_SYNC_ERROR(10304, "crm.sync.error", "订单同步CRM发送错误");		
		
		private Integer code;
		private String message;
		private String screenMessage;

		private WebExceptionEnum(Integer code, String message, String screenMessage){
			this.code = code;
			this.message = message;
			this.screenMessage = screenMessage;
		}
		
		public Integer getCode() {
			return code;
		}
		public void setCode(Integer code) {
			this.code = code;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
		public String getScreenMessage() {
			return screenMessage;
		}

		public void setScreenMessage(String screenMessage) {
			this.screenMessage = screenMessage;
		}

		public String toLog(){
			return this.name()+String.format("{%s,%s,%s}", code,message,screenMessage);
		}		
		
		/**
		 * 检查code,message是否重复定义
		 * 
		 */
		static void check(){
			WebExceptionEnum[] values = WebExceptionEnum.values();
			Map<Integer,WebExceptionEnum> map = new HashMap<Integer,WebExceptionEnum>();
			for (WebExceptionEnum item : values) {
				if(map.containsKey(item.getCode())){
					System.out.println("code冲突:");
					System.out.println(map.get(item.getCode()).toLog());
					System.out.println(item.toLog());
					System.out.println("\r\n");
					return;
				}else{
					map.put(item.getCode(), item);
				}
			}

			Map<String,WebExceptionEnum> map2 = new HashMap<String,WebExceptionEnum>();
			for (WebExceptionEnum item : values) {
				if(map2.containsKey(item.getMessage())){
					System.out.println("message冲突:");
					System.out.println(map2.get(item.getMessage()).toLog());
					System.out.println(item.toLog());
					System.out.println("\r\n");
					return;
				}else{
					map2.put(item.getMessage(), item);
				}
			}
		}
		static void pringAll(){
			WebExceptionEnum[] values = WebExceptionEnum.values();
			for (WebExceptionEnum item : values) {
				System.out.println(item.toLog());
			}
		}		

		public static void main(String[] args) {
			check();
			pringAll();
		}
}
