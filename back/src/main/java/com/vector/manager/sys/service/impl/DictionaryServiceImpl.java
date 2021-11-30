package com.vector.manager.sys.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.vector.manager.core.enums.StatusEnum;
import com.vector.manager.sys.dao.DictionaryDao;
import com.vector.manager.sys.entity.Dictionary;
import com.vector.manager.sys.entity.vo.DictionaryVO;
import com.vector.manager.sys.service.IDictionaryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Acerola
 * @since 2021-08-06
 */
@CacheConfig(cacheNames = {"DictionaryService"})
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryDao, Dictionary> implements IDictionaryService {


    @Cacheable(key = "'all_dictionary'")
    @Override
    public Map<String, List<DictionaryVO>> getAllDict() {
        List<Dictionary> dictionaryList = this.list(new LambdaQueryWrapper<Dictionary>()
                .eq(Dictionary::getStatus, StatusEnum.YES.getValue())
                .orderByDesc(Dictionary::getSort));
        Map<String, List<DictionaryVO>> dictMap = new HashMap<>();
        dictionaryList.stream().collect(Collectors.groupingBy(Dictionary::getCode)).forEach((k, dictionaries) -> {
            List<DictionaryVO> v = dictionaries.stream().map(d -> {
                String value = d.getValue();
                if (NumberUtil.isInteger(value)) {
                    return new DictionaryVO(d.getLabel(), Integer.valueOf(value));
                } else {
                    return new DictionaryVO(d.getLabel(), 0);
                }
            }).collect(Collectors.toList());
            dictMap.put(k, v);
        });
        return dictMap;
    }

    @CacheEvict(key = "'all_dictionary'")
    @Override
    public void saveDictionary(Dictionary dictionary) {
        this.save(dictionary);
    }

    @CacheEvict(key = "'all_dictionary'")
    @Override
    public void updateDictionary(Dictionary dictionary) {
        this.updateById(dictionary);
    }

    @CacheEvict(key = "'all_dictionary'")
    @Override
    public void removeDictionary(List<Long> dictionaryIds) {
        this.removeByIds(dictionaryIds);
    }

}
