package kaitou.ppp.dao.support;

import kaitou.ppp.domain.BaseDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象.
 * User: 赵立伟
 * Date: 2015/6/1
 * Time: 15:44
 */
public class Pager<T extends BaseDomain> {
    /**
     * 当前页码
     */
    private int currentPage = 1;
    /**
     * 总页数
     */
    private int totalPage = 0;
    /**
     * 记录总数
     */
    private int totalSize = 0;
    /**
     * 每页条数
     */
    private int perSize = 25;
    /**
     * 查询条件列表
     */
    private List<Condition> conditions = new ArrayList<Condition>();
    /**
     * 结果集
     */
    private List<T> result = new ArrayList<T>();

    public Pager(int currentPage, List<Condition> conditions) {
        this.currentPage = currentPage;
        this.conditions = conditions;
    }

    /**
     * 校验有效性
     *
     * @return 如果当前页码小于1，则无效
     */
    public boolean inValid() {
        return currentPage < 1;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public Pager setTotalSize(int totalSize) {
        this.totalSize = totalSize;
        if (this.totalSize >= 1) {
            this.totalPage = (this.totalSize - 1) / this.perSize + 1;
        }
        return this;
    }

    public List<T> getResult() {
        return result;
    }

    public Pager setResult(List<T> result) {
        this.result = result;
        return this;
    }

    /**
     * 获取开始位置
     *
     * @return 开始位置
     */
    public int beginIndex() {
        if (totalPage < 1) {
            return 0;
        }
        return currentPage < totalPage ? (currentPage - 1) * perSize : (totalPage - 1) * perSize;
    }

    /**
     * 获取结束位置
     *
     * @return 结束位置
     */
    public int endIndex() {
        return currentPage < totalPage ? currentPage * perSize : totalSize;
    }
}
