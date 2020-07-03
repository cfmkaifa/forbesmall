package net.mall.model;
import java.io.Serializable;

/***
 * 返回数据
 */
public class BusResult implements Serializable {

    private static final long serialVersionUID = -6445190669407612888L;

    /**链账户地址*/;
    private String accountAddress;
    /***上链值*/
    private String value;
    /**备注**/
    private String memo;
    /**上链时间**/
    private String chainTransTime;
    /***高度**/
    private String height;
    /***图片的上传地址**/
    private String ossFileUrl;
    /**hash地址**/
    private String hash;


    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getChainTransTime() {
        return chainTransTime;
    }

    public void setChainTransTime(String chainTransTime) {
        this.chainTransTime = chainTransTime;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getOssFileUrl() {
        return ossFileUrl;
    }

    public void setOssFileUrl(String ossFileUrl) {
        this.ossFileUrl = ossFileUrl;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
