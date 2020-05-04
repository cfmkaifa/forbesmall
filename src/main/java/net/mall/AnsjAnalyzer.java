/*
 *
 *
 *
 *
 */
package net.mall;

import org.ansj.library.DicLibrary;
import org.ansj.util.MyStaticValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Analyzer - Ansj
 *
 * @author huanghy
 * @version 6.1
 */
public class AnsjAnalyzer extends org.ansj.lucene5.AnsjAnalyzer {

    /**
     * 默认类型
     */
    public static final org.ansj.lucene5.AnsjAnalyzer.TYPE DEFAULT_TYPE = org.ansj.lucene5.AnsjAnalyzer.TYPE.dic_ansj;
    public static final Map<String, String> args = new HashMap<String, String>();

    /**
     * 构造方法
     */
    public AnsjAnalyzer() {
        super(args);
        DicLibrary.putIfAbsent("dics","/opt/dic/synonyms.dic");
        args.put("type", DEFAULT_TYPE.name());
        args.put("dic","dics");
    }

}