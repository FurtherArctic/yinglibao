package com.bjpowernode.web.struct;

import com.bjpowernode.common.RCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用应答结果类，专门用于给前端返回数据
 *
 * @author wangjunchen
 */
@Data
@ApiModel(value = "参数接收，详细信息和状态码")
public class CommonResult {
    /**
     * 应答码
     */
    @ApiModelProperty(name = "code", value = "信息获取状态码")
    private Integer code;
    /**
     * 应答消息
     */
    @ApiModelProperty(name = "msg", value = "应答消息")
    private String msg;
    /**
     * 详细信息
     */
    @ApiModelProperty(name = "info", value = "详细信息")
    private Object info;

    /**
     * 成功时的应答结果
     *
     * @param info 详细信息
     * @return 应答结果
     */
    public static CommonResult success(Object info) {
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(RCode.SUCCESS.getCode());
        commonResult.setMsg(RCode.SUCCESS.getText());
        commonResult.setInfo(info);
        return commonResult;
    }

    /**
     * 失败时的应答结果，统一应答信息为请求失败
     *
     * @return 应答结果
     */
    public static CommonResult failure() {
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(RCode.FAILURE.getCode());
        commonResult.setMsg(RCode.FAILURE.getText());
        commonResult.setInfo("");
        return commonResult;
    }

    /**
     * 失败时的应答结果，但是应答信息具体
     * @param rCode 枚举信息
     * @return commonResult对象
     */
    public static CommonResult failure(RCode rCode) {
        CommonResult commonResult = new CommonResult();
        commonResult.setRCode(rCode);
        commonResult.setInfo("");
        return commonResult;
    }

    public void setRCode(RCode rCode) {
        this.code = rCode.getCode();
        this.msg = rCode.getText();
    }
}
