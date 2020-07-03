package net.mall.model;

import java.io.Serializable;

/***
 * 返回对象
 */
public class ResultModel implements Serializable {
    private static final long serialVersionUID = 7291999444237413923L;


    /***
     * 返回码
     */
    private String resultCode;

    /***返回消息
     * */
    private String resultMessage;

    private BusResult data;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public BusResult getData() {
        return data;
    }

    public void setData(BusResult data) {
        this.data = data;
    }
}
