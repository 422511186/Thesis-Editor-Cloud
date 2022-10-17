package com.cmgzs.controller;

import com.cmgzs.constant.HttpStatus;
import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.domain.base.TableDataInfo;

import java.util.Collection;
import java.util.List;

public class BaseController {

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    public ApiResult toResult(boolean result) {
        return result ? ApiResult.success() : ApiResult.error();
    }

    /**
     * 响应返回结果
     *
     * @param obj 结果
     * @return 操作结果
     */
    public ApiResult toResult(Object obj) {
        return obj != null ? ApiResult.success(obj) : ApiResult.error();
    }

    /**
     * 响应返回结果
     *
     * @param collection 结果
     * @return 操作结果
     */
    public ApiResult toResult(Collection collection) {
        return collection.size() > 0 ? ApiResult.success(collection) : ApiResult.error();
    }

    /**
     * 响应返回结果
     *
     * @param row 影响行数
     * @return 操作结果
     */
    public ApiResult toResult(int row) {
        return row > 0 ? ApiResult.success() : ApiResult.error();
    }

    /**
     * 返回成功
     */
    public ApiResult success() {
        return ApiResult.success();
    }

    /**
     * 返回失败消息
     */
    public ApiResult error() {
        return ApiResult.error();
    }

    /**
     * 返回成功消息
     */
    public ApiResult success(String message) {
        return ApiResult.success(message);
    }

    /**
     * 返回失败消息
     */
    public ApiResult error(String message) {
        return ApiResult.error(message);
    }

    /**
     * 待开发的消息提示
     */
    public ApiResult warn() {
        return ApiResult.error("该接口正在开发中");
    }


    /**
     * 响应请求分页数据
     */
    protected TableDataInfo getDataTable(long total, List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(total);
        return rspData;
    }


}
