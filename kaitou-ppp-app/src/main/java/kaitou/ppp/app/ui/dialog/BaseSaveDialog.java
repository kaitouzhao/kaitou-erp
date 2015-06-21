package kaitou.ppp.app.ui.dialog;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.app.ui.table.queryobject.IQueryObject;
import kaitou.ppp.app.ui.table.OPManager;
import kaitou.ppp.domain.BaseDomain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static kaitou.ppp.app.ui.table.OPManager.doRunWithWaiting;
import static kaitou.ppp.app.ui.table.OPManager.saveOrUpdate;
import static kaitou.ppp.common.utils.ReflectionUtil.newInstance;
import static kaitou.ppp.common.utils.ReflectionUtil.setFieldValue;

/**
 * 保存对话框父类.
 * User: 赵立伟
 * Date: 2015/6/9
 * Time: 15:16
 */
public abstract class BaseSaveDialog<T extends BaseDomain> extends JDialog {
    /**
     * 是否保存
     */
    protected boolean isOk;
    /**
     * 查询对象
     */
    protected IQueryObject<T> queryObject;
    /**
     * 输入框列表
     */
    protected List<JTextField> textFields = new ArrayList<JTextField>();
    /**
     * 保存的实体
     */
    protected T domain;
    /**
     * 父窗体
     */
    protected JFrame parentFrame;

    protected BaseSaveDialog(Frame owner) {
        super(owner);
    }

    /**
     * 确定添加
     */
    protected void ok() {
        ConfirmHint confirmHint = new ConfirmHint(this, "确定添加吗？");
        if (!confirmHint.isOk()) {
            return;
        }
        domain = newInstance(queryObject.domainClass());
        String[] saveFieldNames = queryObject.saveFieldNames();
        for (int i = 0; i < textFields.size(); i++) {
            setFieldValue(saveFieldNames[i], domain, textFields.get(i).getText());
        }
        doRunWithWaiting(parentFrame, new OPManager.OpRunnable() {
            @Override
            public void run() {
                saveOrUpdate(queryObject.domainType(), CollectionUtil.toArray(CollectionUtil.newList(domain), queryObject.domainClass()));
            }
        }, "");
        isOk = true;
        setVisible(false);
    }

    /**
     * 取消添加
     */
    protected void cancel() {
        setVisible(false);
    }

    public T getDomain() {
        return domain;
    }

    public boolean isOk() {
        return isOk;
    }

}
