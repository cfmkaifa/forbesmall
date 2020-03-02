package net.mall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * SubsNewsHuman概要说明：订阅记录
 * @author Huanghy
 */
@Entity
@Table(name = "subs_news_human")
public class SubsNewsHuman extends BaseEntity<Long> {

    /**
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 7799050859874485576L;


    @Column(nullable = false, name = "human_id")
    private Long humanId;

    @Column(nullable = false, name = "data_id")
    private Long dataId;

    @Column(nullable = false, name = "data_type")
    private String dataType;

    @Column(name = "pay_sn")
    private String paySn;


    @Column(nullable = false, name = "sn")
    private String sn;


    /***
     *
     */
    @Column(name = "expd")
    private Date expd;


    /**
     * @return humanId
     */
    public Long getHumanId() {
        return humanId;
    }


    /**
     * @param humanId 要设置的 humanId
     */
    public void setHumanId(Long humanId) {
        this.humanId = humanId;
    }


    /**
     * @return dataId
     */
    public Long getDataId() {
        return dataId;
    }


    /**
     * @param dataId 要设置的 dataId
     */
    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }


    /**
     * @return sn
     */
    public String getSn() {
        return sn;
    }


    /**
     * @param sn 要设置的 sn
     */
    public void setSn(String sn) {
        this.sn = sn;
    }


    /**
     * @return expd
     */
    public Date getExpd() {
        return expd;
    }


    /**
     * @param expd 要设置的 expd
     */
    public void setExpd(Date expd) {
        this.expd = expd;
    }


    /**
     * @return dataType
     */
    public String getDataType() {
        return dataType;
    }


    /**
     * @param dataType 要设置的 dataType
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }


    /**
     * @return paySn
     */
    public String getPaySn() {
        return paySn;
    }


    /**
     * @param paySn 要设置的 paySn
     */
    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

}
