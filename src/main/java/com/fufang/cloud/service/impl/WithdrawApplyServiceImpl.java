package com.fufang.cloud.service.impl;

import com.fufang.cloud.mapper.mysql.WithdrawApplyMapper;
import com.fufang.cloud.mapper.mysql_acc.AccountMapper;
import com.fufang.cloud.model.Account;
import com.fufang.cloud.model.WithdrawApply;
import com.fufang.cloud.service.IWithdrawApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zhifu.chen on 2016/11/16.
 */
@Service("withdrawApplyService")
public class WithdrawApplyServiceImpl implements IWithdrawApplyService {
    @Autowired
    private WithdrawApplyMapper withdrawApplyMapper;
    @Autowired
    private AccountMapper accountMapper;
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addWithDrawApply(WithdrawApply withdrawApply) {
        Date createTime = new Date();
        withdrawApply.setAccountId(withdrawApply.getAccountId());
        withdrawApply.setId(UUID.randomUUID().toString());
        withdrawApply.setCreateTime(createTime);
        withdrawApply.setSort(1L);
        withdrawApplyMapper.insert(withdrawApply);//用的是acc的事务,所以不回滚
        Account account = new Account();
        account.setId(UUID.randomUUID().toString());
        account.setPharmacyId(2);
        account.setCreateDate(createTime);
        accountMapper.insert(account);//回滚
        throw new RuntimeException("3");
    }
}
