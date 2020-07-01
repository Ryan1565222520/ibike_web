package com.yc.projects.ibike.service;

public interface LogService {
    /**
     * 用户登录记录日志
     * @param log
     */
    public void save(String log);

    /**
     * 用户充值是记录日志
     * @param log
     */
    public void savePayLog(String log);

    /**
     * 用户报修记录日志
     * @param log
     */
    public void saveRepairLog(String log);

    /**
     * 用户骑行日志
     * @param log
     */
    public void saveUseLog(String log);
}
