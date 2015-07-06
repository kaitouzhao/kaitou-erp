package kaitou.ppp.manager.shop.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.common.utils.FileUtil;
import kaitou.ppp.domain.shop.PartsLibrary;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.shop.PartsLibraryManager;

import java.util.List;

/**
 * 零件备库管理DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/17
 * Time: 22:29
 */
public class PartsLibraryManagerImpl extends BaseFileDaoManager<PartsLibrary> implements PartsLibraryManager, ShopUpdateListener {
    @Override
    public Class<PartsLibrary> domainClass() {
        return PartsLibrary.class;
    }

    @Override
    public List<PartsLibrary> query() {
        return super.query();
    }

    @Override
    public void updateShopEvent(Shop... shops) {
        List<PartsLibrary> partsLibraries = query();
        if (CollectionUtil.isEmpty(partsLibraries) || CollectionUtil.isEmpty(shops)) {
            return;
        }
        for (PartsLibrary partLibrary : partsLibraries) {
            for (Shop shop : shops) {
                if (shop.getId().equals(partLibrary.getShopId())) {
                    partLibrary.setShopName(shop.getName());
                    break;
                }
            }
        }
        save(partsLibraries);
    }

    @Override
    public void updateShopDetailEvent(ShopDetail... shopDetails) {

    }

    @Override
    public void updateShopIdEvent(Shop... shops) {
//        List<PartsLibrary> partsLibraryList = queryAll();
//        FileUtil.deleteFilesOfDir(dbDir, "PartsLibrary.kdb");
//        for (PartsLibrary partsLibrary : partsLibraryList) {
//            for (Shop shop : shops) {
//                if (!shop.getName().equals(partsLibrary.getName())) {
//                    continue;
//                }
//                partsLibrary.setId(shop.getId());
//            }
//        }
//        save(partsLibraryList);
    }
}
