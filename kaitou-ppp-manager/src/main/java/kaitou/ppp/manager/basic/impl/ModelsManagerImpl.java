package kaitou.ppp.manager.basic.impl;

import kaitou.ppp.dao.basic.ModelsDao;
import kaitou.ppp.domain.basic.Models;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.basic.ModelsManager;

/**
 * 机型基础数据DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/6/22
 * Time: 19:17
 */
public class ModelsManagerImpl extends BaseFileDaoManager<Models> implements ModelsManager {

    private ModelsDao modelsDao;

    public void setModelsDao(ModelsDao modelsDao) {
        this.modelsDao = modelsDao;
    }

    @Override
    public Class<Models> domainClass() {
        return Models.class;
    }

    @Override
    public Models getByModel(String model) {
        return modelsDao.getByModel(model);
    }

    @Override
    public void cacheModels() {
        modelsDao.cacheModels();
    }
}
