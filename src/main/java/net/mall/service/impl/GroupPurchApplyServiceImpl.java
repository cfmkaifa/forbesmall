package net.mall.service.impl;
import net.mall.dao.GroupPurchApplyDao;
import net.mall.entity.GroupPurchApply;
import net.mall.service.GroupPurchApplyService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class GroupPurchApplyServiceImpl extends BaseServiceImpl<GroupPurchApply, Long> implements GroupPurchApplyService {

    @Inject
    private GroupPurchApplyDao groupPurchApplyDao;

}
