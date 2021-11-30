package com.vector.manager.core.utils.tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class TreeUtils {

    @SuppressWarnings("unchecked")
    public static <T extends BaseTree> List<T> formatTree(List<T> dataList) {
        try {
            if (dataList != null) {
                List<T> treeData = new ArrayList<T>();
                LinkedHashMap<Long, T> map = new LinkedHashMap<Long, T>();
                for (T tree : dataList) {
                    map.put(tree.getId(), tree);
                }
                for (Long id : map.keySet()) {
                    T tree = map.get(id);
                    Long pid = tree.getPid();
                    if (pid == null || pid == 0L || pid == -1) {
                        treeData.add(tree);
                    } else {
                        T parentData = map.get(pid);
                        if (null == parentData) {
                            treeData.add(tree);
                        } else {
                            List<T> childrenList = (List<T>) parentData.getChildren();
                            if (childrenList == null) {
                                childrenList = new ArrayList<T>();
                            }
                            childrenList.add(tree);
                            parentData.setChildren((List<BaseTree>) childrenList);
                        }
                    }
                }
                return treeData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
