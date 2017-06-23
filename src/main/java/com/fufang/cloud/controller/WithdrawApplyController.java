package com.fufang.cloud.controller;

import com.fufang.cloud.model.WithdrawApply;
import com.fufang.cloud.service.IWithdrawApplyService;
import com.fufang.cloud.util.core.response.FFApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by zhifu.chen on 2016/11/16.
 */

@Controller
@RequestMapping("/withdrawApply")
public class WithdrawApplyController extends BaseController {
    @Autowired
    private IWithdrawApplyService withdrawApplyService;

    @RequestMapping(value = "/addApply", method = RequestMethod.POST)
    @ResponseBody
    public FFApiResponse<Map<String,Object>> addApply(@RequestBody WithdrawApply param) {
    	withdrawApplyService.addWithDrawApply(param);
        return success();
    }
}
