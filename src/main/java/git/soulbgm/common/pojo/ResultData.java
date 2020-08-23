package git.soulbgm.common.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 前端返回格式
 *
 * @author SoulBGM
 * @version V1.0
 * @date 2020-07-30 13:48:42
 */
public class ResultData {

    private Integer code;

    private String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public ResultData() {
    }

    public static ResultData getResultData(Integer code, String msg) {
        ResultData resultData = new ResultData();
        resultData.code = code;
        resultData.msg = msg;
        return resultData;
    }

    public static ResultData getResultData(ReturnCode returnCode, Object data) {
        ResultData resultData = new ResultData();
        resultData.code = returnCode.code;
        resultData.msg = returnCode.msg;
        resultData.data = data;
        return resultData;
    }

    public static ResultData getResultData(ReturnCode returnCode) {
        ResultData resultData = new ResultData();
        resultData.code = returnCode.code;
        resultData.msg = returnCode.msg;
        return resultData;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
