package com.gplucky.common.mybatis.model.ext;

import com.gplucky.common.mybatis.model.TaskHistory;

/**
 * Created by ehsy_it on 2017/2/3.
 */
public class TaskHistoryExt extends TaskHistory {

    /**
     * 同步股票信息
     */
    public static final String TYPE_FETCHSTOCKINFO = "1";

    /**
     * 任务状态：0：未执行完；1：执行完成
     */
    public static final String STATUS_START = "0";

    /**
     * 任务状态：0：未执行完；1：执行完成
     */
    public static final String STATUS_FINISHED = "1";
}
