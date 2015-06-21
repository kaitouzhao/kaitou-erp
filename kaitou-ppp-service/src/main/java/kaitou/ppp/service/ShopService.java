package kaitou.ppp.service;

import kaitou.ppp.domain.shop.*;

import java.io.File;
import java.util.List;

/**
 * 认定店业务处理层.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 13:22
 */
public interface ShopService {
    /**
     * 导入认定店
     *
     * @param srcFile 源文件
     */
    public void importShops(File srcFile);

    /**
     * 导出认定店
     *
     * @param targetFile 目标文件
     */
    public void exportShops(File targetFile);

    /**
     * 导入认定店明细
     *
     * @param srcFile 源文件
     */
    public void importShopDetails(File srcFile);

    /**
     * 删除认定店基本信息
     *
     * @param shops 认定店集合
     */
    public void deleteShops(Object... shops);

    /**
     * 删除认定店认定级别
     *
     * @param details 认定级别集合
     */
    public void deleteShopDetails(Object... details);

    /**
     * 删除认定店帐号信息
     *
     * @param pays 帐号信息集合
     */
    public void deleteShopPays(Object... pays);

    /**
     * 删除认定店RTS
     *
     * @param rts RTS集合
     */
    public void deleteShopRTSs(Object... rts);

    /**
     * 缓存全部认定店
     */
    public void cacheAllShops();

    /**
     * 导入RTS
     *
     * @param srcFile 源文件
     */
    public void importRTSs(File srcFile);

    /**
     * 导出RTS
     *
     * @param targetFile 目标文件
     */
    public void exportRTSs(File targetFile);

    /**
     * 导入付款信息
     *
     * @param srcFile 源文件
     */
    public void importPays(File srcFile);

    /**
     * 导出付款信息
     *
     * @param targetFile 目标文件
     */
    public void exportPays(File targetFile);

    /**
     * 基础信息全导出（今年的认定级别）
     *
     * @param targetFile 目标文件
     */
    public void exportAll(File targetFile);

    /**
     * 查询全部认定店基本信息
     *
     * @return 基本信息列表
     */
    public List<Shop> queryAllShops();

    /**
     * 查询全部认定店认定级别
     *
     * @return 认定级别列表
     */
    public List<ShopDetail> queryAllDetails();

    /**
     * 查询全部认定店RTS
     *
     * @return RTS列表
     */
    public List<ShopRTS> queryAllRTSs();

    /**
     * 查询全部认定店帐号信息
     *
     * @return 帐号信息列表
     */
    public List<ShopPay> queryAllPays();

    /**
     * 保存/更新认定店基本信息
     *
     * @param shop 认定店
     */
    public void saveOrUpdateShop(Shop... shop);

    /**
     * 保存/更新认定店认定级别
     *
     * @param detail 认定级别
     */
    public void saveOrUpdateShopDetail(ShopDetail... detail);

    /**
     * 保存/更新认定店RTS
     *
     * @param rts RTS
     */
    public void saveOrUpdateShopRTS(ShopRTS... rts);

    /**
     * 保存/更新认定店帐号信息
     *
     * @param pay 帐号信息
     */
    public void saveOrUpdateShopPay(ShopPay... pay);

    /**
     * 统计认定店设备
     *
     * @param targetFile 目标文件
     */
    public void countShopEquipment(File targetFile);

    /**
     * 导入合同信息
     *
     * @param srcFile 源文件
     */
    public void importShopContracts(File srcFile);

    /**
     * 导出合同信息
     *
     * @param targetFile 目标文件
     */
    public void exportShopContracts(File targetFile);

    /**
     * 查询合同信息
     *
     * @return 合同信息列表
     */
    public List<ShopContract> queryShopContracts();

    /**
     * 保存/更新合同信息
     *
     * @param shopContracts 合同信息
     */
    public void saveOrUpdateShopContracts(ShopContract... shopContracts);

    /**
     * 删除合同信息
     *
     * @param shopContracts 合同信息
     */
    public void deleteShopContract(Object... shopContracts);

    /**
     * 导入零件备库信息
     *
     * @param srcFile 源文件
     */
    public void importPartsLibrary(File srcFile);

    /**
     * 导出零件备库信息
     *
     * @param targetFile 目标文件
     */
    public void exportPartsLibrary(File targetFile);

    /**
     * 查询零件备库信息
     *
     * @return 零件备库信息列表
     */
    public List<PartsLibrary> queryPartsLibrary();

    /**
     * 保存/更新零件备库信息
     *
     * @param partsLibraries 零件备库信息
     */
    public void saveOrUpdatePartsLibrary(PartsLibrary... partsLibraries);

    /**
     * 删除零件备库信息
     *
     * @param partsLibraries 零件备库信息
     */
    public void deletePartsLibrary(Object... partsLibraries);

    /**
     * 更新认定店编号
     *
     * @param srcFile 源文件
     */
    public void updateShopId(File srcFile);
}
