package net.mall.rools;

import org.drools.template.DataProvider;

import java.util.Iterator;
import java.util.List;

/***
 * 自定义数据解析
 */
public class ExtDataProvider implements DataProvider {

    private Iterator<String[]> iterator;

    public ExtDataProvider(List<String[]> rows) {
        this.iterator = rows.iterator();
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public String[] next() {
        return iterator.next();
    }
}
