package net.mall.util;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;

public class RuleBaseFacatory {
    private static RuleBase ruleBase;


    public static RuleBase getRuleBase(){

        return null != ruleBase ? ruleBase : RuleBaseFactory.newRuleBase();

    }

    public static void setRuleBase(RuleBase ruleBase) {
        RuleBaseFacatory.ruleBase = ruleBase;
    }
}
