package com.phmth.laptopshop.controller.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.entity.CheckoutEntity;
import com.phmth.laptopshop.entity.OrderEntity;
import com.phmth.laptopshop.enums.StateCheckout;
import com.phmth.laptopshop.service.ICheckoutService;
import com.phmth.laptopshop.service.IOrderDetailService;
import com.phmth.laptopshop.service.IOrderService;
import com.phmth.laptopshop.utils.ConfigVNPAY;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CheckoutController.class);
	
	@Autowired
	private ICheckoutService checkoutService;
	
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private IOrderDetailService orderDetailService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/vnpay")
	public ModelAndView PaymentVNPAY(
					@RequestParam("codeOrder") String codeOrder,
					@RequestParam("bankCode") String bankCode,
					HttpServletRequest req, 
					HttpServletResponse resp) throws ServletException, IOException {
		
		OrderEntity orderEntity = orderService.findByCodeOrder(codeOrder).get();
		String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        long amount = (long) (orderEntity.getTotal_money()*100);
        
        String vnp_TxnRef = codeOrder;
        String vnp_TmnCode = ConfigVNPAY.vnp_TmnCode;
        
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef );
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", ConfigVNPAY.vnp_Returnurl);

//        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(orderEntity.getCreated_at());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        
//        cld.add(Calendar.MINUTE, 15);
//        String vnp_ExpireDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = ConfigVNPAY.hmacSHA512(ConfigVNPAY.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = ConfigVNPAY.vnp_PayUrl + "?" + queryUrl;
	        
		return new ModelAndView("redirect:"+paymentUrl);
	}
	
	@GetMapping("/vnpay/info")
	public ModelAndView infoPayment(
						@RequestParam("vnp_Amount") String vnp_Amount,
						@RequestParam("vnp_BankCode") String vnp_BankCode,
						@RequestParam("vnp_BankTranNo") String vnp_BankTranNo,
						@RequestParam("vnp_CardType") String vnp_CardType,
						@RequestParam("vnp_OrderInfo") String vnp_OrderInfo,
						@RequestParam("vnp_PayDate") String vnp_PayDate,
						@RequestParam("vnp_ResponseCode") String vnp_ResponseCode,
						@RequestParam("vnp_TmnCode") String vnp_TmnCode,
						@RequestParam("vnp_TransactionNo") String vnp_TransactionNo,
						@RequestParam("vnp_TransactionStatus") String vnp_TransactionStatus,
						@RequestParam("vnp_TxnRef") String vnp_TxnRef,
						@RequestParam("vnp_SecureHash") String vnp_SecureHash,
						HttpServletRequest request ) throws UnsupportedEncodingException {
		
		ModelAndView mav= new ModelAndView("/web/checkout/bill");
		
		OrderEntity orderEntity = orderService.findByCodeOrder(vnp_TxnRef).get();
		
		if("00".equals(vnp_ResponseCode)) {
			CheckoutEntity checkoutEntity = new CheckoutEntity();
			//Accounts and Orders Paid
			checkoutEntity.setOrder(orderEntity);
			checkoutEntity.setUser(orderEntity.getUser());
			//Save information from vnpay returned
			checkoutEntity.setAmount(Integer.parseInt(vnp_Amount));
			checkoutEntity.setBankCode(vnp_BankCode);
			checkoutEntity.setBankTranNo(vnp_BankTranNo);
			checkoutEntity.setCardType(vnp_CardType);
			checkoutEntity.setOrderInfo(vnp_OrderInfo);
			checkoutEntity.setPayDate(vnp_PayDate);
			checkoutEntity.setResponseCode(vnp_ResponseCode);
			checkoutEntity.setTmnCode(vnp_TmnCode);
			checkoutEntity.setTransactionNo(vnp_TransactionNo);
			checkoutEntity.setTransactionStatus(vnp_TransactionStatus);
			checkoutEntity.setTxnRef(vnp_TxnRef);
			checkoutEntity.setSecureHash(vnp_SecureHash);
			//Save Data
			checkoutService.save(checkoutEntity);
			//Change order status is successful payment
			orderService.updateStateCheckout(orderEntity.getId(), StateCheckout.PAID);
			
			mav.addObject("success", "Giao dịch thàng công");
		}else {
			mav.addObject("error", "Giao dịch thất bại");
		}
		
		mav.addObject("checkout", " ");
		
		mav.addObject("vnp_Amount", Integer.parseInt(vnp_Amount)/100);
		mav.addObject("vnp_BankCode", vnp_BankCode);
		mav.addObject("vnp_BankTranNo", vnp_BankTranNo);
		mav.addObject("vnp_CardType", vnp_CardType);
		mav.addObject("vnp_OrderInfo", vnp_OrderInfo);
		mav.addObject("vnp_PayDate", vnp_PayDate);
		mav.addObject("vnp_ResponseCode", vnp_ResponseCode);
		mav.addObject("vnp_TmnCode", vnp_TmnCode);
		mav.addObject("vnp_TransactionNo", vnp_TransactionNo);
		mav.addObject("vnp_TransactionStatus", vnp_TransactionStatus);
		mav.addObject("vnp_TxnRef", vnp_TxnRef);
		mav.addObject("vnp_SecureHash", vnp_SecureHash);
		
		mav.addObject("order", orderEntity);
		mav.addObject("orderdetail", orderDetailService.findByOrder(orderEntity.getId()));
		
		return mav;
	}
}

	
