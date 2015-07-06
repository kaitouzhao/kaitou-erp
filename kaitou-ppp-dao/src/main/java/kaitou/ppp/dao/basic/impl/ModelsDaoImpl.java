package kaitou.ppp.dao.basic.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.basic.ModelsDao;
import kaitou.ppp.domain.basic.Models;

import java.util.List;

import static kaitou.ppp.dao.cache.CacheManager.cachedModels;

/**
 * 机型基础数据DAO实现.
 * User: 赵立伟
 * Date: 2015/6/22
 * Time: 19:12
 */
public class ModelsDaoImpl extends BaseDao<Models> implements ModelsDao {
    @Override
    public Class<Models> getDomainClass() {
        return Models.class;
    }

    @Override
    public Models getByModel(String model) {
        return cachedModels.get(model);
    }

    @Override
    public void cacheModels() {
        List<Models> modelsList = queryAll();
        for (Models models : modelsList) {
            cachedModels.put(models.getModel(), models);
        }
    }
}
