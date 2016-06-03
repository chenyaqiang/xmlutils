package com.eeduspace.management.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eeduspace.management.convert.CIBNManagementConvert;
import com.eeduspace.management.model.OrderQueryModel;
import com.eeduspace.management.model.UserModel;
import com.eeduspace.management.model.UserOrderModel;
import com.eeduspace.management.model.VipOrderModel;
import com.eeduspace.management.persist.enumeration.BuyTypeEnum;
import com.eeduspace.management.persist.po.UserPo;
import com.eeduspace.management.persist.po.VipBuyRecord;
import com.eeduspace.management.rescode.ResponseCode;
import com.eeduspace.management.rescode.ResponseItem;
import com.eeduspace.management.service.UserService;
import com.eeduspace.management.service.VipBuyRecordService;
import com.google.gson.Gson;
/**\
 * 订单控制层
 * @author zhuchaowei
 * 2016年6月1日
 * Description
 */
@Controller
@RequestMapping("/vip_order")
public class VipOrderController {
	private final Logger logger = LoggerFactory.getLogger(VipOrderController.class);
	private Gson gson=new Gson();
	@Inject
	private VipBuyRecordService vipBuyRecordService;
	@Inject
	private UserService userService;
	@RequestMapping("/user_vip_order")
	@ResponseBody
	public ResponseItem userOrderList(@ModelAttribute OrderQueryModel orderQueryModel){
		ResponseItem item=new ResponseItem();
		logger.debug("userOrderList request param:{}",gson.toJson(orderQueryModel));
		if(StringUtils.isBlank(orderQueryModel.getUserCode())){
			item.setMessage("UserCode参数丢失");
			item.setHttpCode(ResponseCode.PARAMETER_MISS.toString());
			return item;
		}
		try {
			//UserPo userPo=userService.findByUserCode("df6548d1fd534b90aa18939af7e1f052");
			List<VipBuyRecord> vipBuyRecords=vipBuyRecordService.findByUserCodeAndIsPay(orderQueryModel.getUserCode(), true,BuyTypeEnum.VIP);
			List<VipBuyRecord> diagnosticOrder=vipBuyRecordService.findByUserCodeAndIsPay(orderQueryModel.getUserCode(), true,BuyTypeEnum.DIAGNOSTIC);
			UserOrderModel userOrderModel=new UserOrderModel();
			userOrderModel.setDiagnosticOrder(diagnosticOrder);
			userOrderModel.setVipOrder(vipBuyRecords);
			item.setMessage("success");
			item.setData(userOrderModel);
			return item;
		} catch (Exception e) {
			  logger.error("userOrderList  Exception:", e);
	           return ResponseItem.responseWithName(new ResponseItem(), ResponseCode.SERVICE_ERROR.toString(), "userIndex exception");
		}
	}
	
	@RequestMapping("/order_list")
	@ResponseBody
	public ResponseItem getOrderList(@ModelAttribute OrderQueryModel orderQueryModel){
		ResponseItem item=new ResponseItem();
		logger.debug("getOrderList request param:{}",gson.toJson(orderQueryModel));
		try {
			Pageable pageable=new PageRequest(orderQueryModel.getCurrentPage(), orderQueryModel.getSize());
			Page<VipBuyRecord> pageList=vipBuyRecordService.findAll(orderQueryModel,pageable);
			List<VipBuyRecord> orderList=pageList.getContent();
			List<VipOrderModel> vipOrderModels=new ArrayList<>();
			for (VipBuyRecord vipBuyRecord : orderList) {
				VipOrderModel vipOrderModel=CIBNManagementConvert.fromVipBuyRecord(vipBuyRecord);
				vipOrderModels.add(vipOrderModel);
			}
			logger.debug("getNumber:{},getNumberOfElements:{},getSize{},getTotalElements:{},getTotalPages:{}",pageList.getNumber(),pageList.getNumberOfElements(),pageList.getSize(),pageList.getTotalElements(),pageList.getTotalPages());
			item.setTotalRecords(pageList.getTotalElements());
			item.setCurrentPage(pageList.getNumber());
			item.setTotalPage(pageList.getTotalPages());
			item.setSize(pageList.getSize());
			item.setMessage("success");
			item.setDatas(vipOrderModels);
			return item;
		} catch (Exception e) {
			logger.error("getOrderList  Exception:", e);
	        return ResponseItem.responseWithName(new ResponseItem(), ResponseCode.SERVICE_ERROR.toString(), "getOrderList exception");
		}
		
	}
	@RequestMapping("/order_info")
	@ResponseBody
	public ResponseItem getOrderInfo(){
		ResponseItem item=new ResponseItem();
		item.setMessage("success");
		return item;
	}
	
	@RequestMapping("/order_excel_export")
	@ResponseBody
	public ResponseItem orderExcelExport(HttpServletResponse response,@ModelAttribute OrderQueryModel orderQueryModel) throws IOException{
		ResponseItem item = new ResponseItem();
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		OutputStream outputStream = response.getOutputStream();
		String[] tableHeader = { "订单号", "流水号", "手机号", "商品类型", "商品名称", "价格", "支付方式", "交易时间", "状态" };
		String fileName = new String(("order_list").getBytes(), "utf-8");
		response.setHeader("Content-disposition", "attachment; filename="+ fileName + ".xlsx");// 组装附件名称和格式
		Page<VipBuyRecord> pageList=vipBuyRecordService.findAll(orderQueryModel,pageable);
		vipBuyRecordService.ExportOrderExcle(pageList.getContent(), tableHeader, outputStream);
		item.setMessage("success");
		return item;
	}
	
}
