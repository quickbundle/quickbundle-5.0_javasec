package org.quickbundle.modules.mobile.phonegapdemo;

import java.io.IOException;
import java.util.ArrayList;

import org.quickbundle.project.serializer.RmObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(value = "/api/phonegapdemo")
public class PhonegapDemoRestController {

	@RequestMapping(value = "/loginUser", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String loginUser(@RequestParam String userName, @RequestParam String userPass,
			@RequestParam("jsoncallback") String jsoncallback) throws IOException {
		String json = "";
		if (userName != null && userPass != null && userName.equals("admin") && userPass.equals("admin")) {
			UserInfo user = new UserInfo();
			user.setId(1);
			user.setName("admin");
			user.setPassword("admin");
			user.setRole("admin");
			System.out.println(jsoncallback);
			json = RmObjectMapper.getInstance().writeValueAsString(user);
		} else {
			UserInfo user = new UserInfo();
			user.setId(1);
			user.setName("error" + jsoncallback);
			user.setPassword("error" + jsoncallback);
			user.setRole("error" + jsoncallback);
			System.out.println(jsoncallback);
			json = RmObjectMapper.getInstance().writeValueAsString(user);
		}

		return jsoncallback + "(" + json + ")";
	}

	@RequestMapping(value = "/getReceiptInfo", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getReceiptInfo(@RequestParam String receiptApplicant, @RequestParam String receiptNum, @RequestParam String receiptFrom,
			@RequestParam String receiptTo, @RequestParam String jsoncallback) throws JsonProcessingException {
		if (receiptApplicant != null && receiptNum.equals("")) {
			ArrayList<ReceiptInfo> list = new ArrayList<ReceiptInfo>();
			ReceiptInfo receipt1 = new ReceiptInfo();
			receipt1.setReceiptApplicant("admin");
			receipt1.setReceiptDate("2013-06-11");
			receipt1.setReceiptNum("1");
			receipt1.setReceiptDetail("2013年6月1日~2013年6月10日北京出差，共发生费用2800元。");
			receipt1.setReceiptTitle("2013年6月1日报销申请");
			list.add(receipt1);
			ReceiptInfo receipt2 = new ReceiptInfo();
			receipt2.setReceiptApplicant("admin");
			receipt2.setReceiptNum("2");
			receipt2.setReceiptDate("2013-06-11");
			receipt2.setReceiptDetail("2013年5月1日~2013年5月10日北京出差，共发生费用4800元。");
			receipt2.setReceiptTitle("2013年5月1日报销申请");
			list.add(receipt2);
			String json = RmObjectMapper.getInstance().writeValueAsString(list);
			return jsoncallback + "(" + json + ")";
		}
		if (receiptNum != null) {
			ReceiptInfo receipt1 = new ReceiptInfo();
			receipt1.setReceiptApplicant("admin");
			receipt1.setReceiptDate("2013-06-11");
			receipt1.setReceiptNum("1");
			receipt1.setReceiptDetail("2013年6月1日~2013年6月10日北京出差，共发生费用2800元。");
			receipt1.setReceiptTitle("2013年6月1日报销申请");
			String json = RmObjectMapper.getInstance().writeValueAsString(receipt1);
			return jsoncallback + "(" + json + ")";
		}

		throw new UnsupportedOperationException("Not yet implemented.");
	}

	@RequestMapping(value = "/saveReceiptInfo", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String saveReceiptInfo(@RequestParam String receiptApplicant, @RequestParam String receiptDate, @RequestParam String receiptTitle,
			@RequestParam String receiptDetail, @RequestParam String jsoncallback) throws JsonProcessingException {
		System.out.println("receipt is saved!" + receiptApplicant + " " + receiptTitle + " " + receiptDetail);
		String json = RmObjectMapper.getInstance().writeValueAsString("success!");
		return jsoncallback + "(" + json + ")";
	}

	@RequestMapping(value = "/deleteReceiptInfo", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String deleteReceiptInfo(@RequestParam String receiptNum, @RequestParam String jsoncallback) throws JsonProcessingException {
		System.out.println("receipt is delete!" + receiptNum);
		String json = RmObjectMapper.getInstance().writeValueAsString("success!");
		return jsoncallback + "(" + json + ")";
	}
}
