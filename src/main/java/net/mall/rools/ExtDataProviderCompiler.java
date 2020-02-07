package net.mall.rools;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.drools.template.DataProviderCompiler;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/***
 *
 */
@Component
public class ExtDataProviderCompiler {

    private static KnowledgeBuilder KB;

    /***
     */
    static {
         KB = KnowledgeBuilderFactory.newKnowledgeBuilder();
    }

    /***
     * 设置参数
     * @param params
     * @param rows
     */
    private void upParams(String[] params,ArrayList<String[]> rows){
        rows.add(params);
    }


    /***
     *
     * @param rows
     * @return
     */
    private InternalKnowledgeBase compileTemplate(ArrayList<String[]> rows)
            throws UnsupportedEncodingException{
        ExtDataProvider tdp = new ExtDataProvider(rows);
        DataProviderCompiler converter = new DataProviderCompiler();
        String drl = converter.compile(tdp, "/drl/rule_template.drl");
        KB.add(ResourceFactory.newByteArrayResource(drl.getBytes("utf-8")), ResourceType.DRL);
        KnowledgeBuilderErrors errors = KB.getErrors();
        for (KnowledgeBuilderError error : errors) {
            System.out.println(error);
        }
        InternalKnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
        kBase.addPackages(KB.getKnowledgePackages());
        return kBase;
    }

    /***
     * @param groupPurchRools
     * @param internalKnowledgeBase
     */
    private  void executeRule(GroupPurchRools groupPurchRools,InternalKnowledgeBase internalKnowledgeBase){
        KieSession kSession = internalKnowledgeBase.newKieSession();
        kSession.insert(groupPurchRools);
        kSession.fireAllRules();
        this.disposeRule(kSession);
    }

    /***
     *
     * @param kSession
     */
    private void disposeRule(KieSession kSession){
        kSession.dispose();
    }

    /***
     *
     * @param params
     * @param groupPurchRools
     * @throws UnsupportedEncodingException
     */
    public void executeRules(String[] params,GroupPurchRools groupPurchRools)
            throws UnsupportedEncodingException {
        ArrayList<String[]> rows = new ArrayList<String[]>();
        this.upParams(params,rows);
        InternalKnowledgeBase internalKnowledgeBase = this.compileTemplate(rows);
        this.executeRule(groupPurchRools,internalKnowledgeBase);
    }
}
