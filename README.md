# kaitou-erp
基于文件DB的联机版ERP

#升级日志：
#版本1.1：
1、每次启动程序时，会自动备份数据文件，增强数据安全性；
2、增加导出全部工程师培训信息功能。
#版本1.2：
1、修正导入工程师培训信息bug；
2、增加记录上次选择文件路径，提高易用性；
3、增加认定店信息导入导出功能。
特别说明：首次启动此版本前，请先删除D:\ppp目录。
#版本1.3：
1、简化工程师导入模板；
2、增加认定店RTS、帐号信息导入；
3、调整主界面，增加菜单栏；
4、增加一些导出功能。
#版本1.4：
1、安全性增强：备份、恢复数据；
2、校验导入的excel文件是否正确；
3、认定店导入校验编号；
4、增加查询界面；
5、删除操作移植到查询界面；
6、认定店查询界面增加认定中/取消操作；
7、工程师查询界面增加在职/离职操作；
8、认定店查询和导出按照认定店编码排序；
9、认定店可以按产品线导出在职人数；
10、工程师基本、认定店发展导入不要区域。注意更新导入模板
#版本1.5（结婚周年纪念版）：
1、各种修改信息联动；
2、导入时格式不正确信息提示完整；
3、查询界面部分列可以编辑；
4、查询界面输入条件回车快速查询出结果；
5、查询界面增加添加按钮。
#版本1.6（三八妇女节礼物版）：
1、整合生成保修卡；
2、日志文件按天滚动分割，且设置最大保留文件数；
3、简化增加新功能的代码量；
4、出错了有更好的提醒方式；
5、增加保修卡生成记录管理。
#版本2.0（步入联机版）：
1、支持联机；
2、查询界面增加导出按钮；
3、查询界面右键单击记录，可以将数据复制到剪贴板上；
4、保修卡记录按照卡号排序；
5、调整保修卡记录导入异常提示。
#版本2.1：
1、认定店管理下的基础数据全导出增加机型一列；
2、离线outbox机制；
3、增加联机状态查询；
4、实现数据存疑机制，且在查询页面颜色区别；
5、保修卡记录以机身号作为主键，且保修卡号可以为空；
6、增加保修卡远程服务，提供保存保修卡记录接口；
7、认定店管理下的认定级别导出按照年份横向排列，每一个认定店一行记录。
#版本3.0：
1、重新规划菜单栏；
2、增加报告错误提示框；
3、调整注册逻辑，向远程注册表中所有ip注册；
4、修改版本号生成规则，时间戳；
5、查询界面增加单独同步功能；
6、改造save接口，支持多个参数；
7、查询界面增加checkbox。
#版本3.1：
1、增加菜单新功能；
2、调整功能菜单中查询位置；
3、取消RTS菜单；
4、更新生成保修卡模板；
5、保修卡生成时，支持类似“2015年5月14日”或者“2015/5/14”格式；
6、增加存疑筛选；
7、增加上下滚轮翻页；
8、自动更新保修状态。
#版本3.2：
实现全部菜单功能。