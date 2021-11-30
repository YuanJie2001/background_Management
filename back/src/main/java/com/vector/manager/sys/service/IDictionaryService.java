package com.vector.manager.sys.service;

import com.vector.manager.sys.entity.Dictionary;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vector.manager.sys.entity.vo.DictionaryVO;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Acerola
 */

public interface IDictionaryService extends IService<Dictionary> {

    Map<String, List<DictionaryVO>> getAllDict();

    void saveDictionary(Dictionary dictionary);

    void updateDictionary(Dictionary dictionary);

    void removeDictionary(List<Long> dictionaryIds);
}
