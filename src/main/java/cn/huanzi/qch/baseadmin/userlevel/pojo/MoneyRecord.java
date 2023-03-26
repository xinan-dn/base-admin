package cn.huanzi.qch.baseadmin.userlevel.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Id;

public class MoneyRecord implements Serializable {
    @Id
    private Integer id;

    private String name;

    private String awardType;

    private String coinType;

    private BigDecimal money;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAwardType(String awardType) {
        this.awardType = awardType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof cn.huanzi.qch.baseadmin.userlevel.pojo.MoneyRecord))
            return false;
        cn.huanzi.qch.baseadmin.userlevel.pojo.MoneyRecord other = (cn.huanzi.qch.baseadmin.userlevel.pojo.MoneyRecord)o;
        if (!other.canEqual(this))
            return false;
        Object this$id = getId(), other$id = other.getId();
        if ((this$id == null) ? (other$id != null) : !this$id.equals(other$id))
            return false;
        Object this$name = getName(), other$name = other.getName();
        if ((this$name == null) ? (other$name != null) : !this$name.equals(other$name))
            return false;
        Object this$awardType = getAwardType(), other$awardType = other.getAwardType();
        if ((this$awardType == null) ? (other$awardType != null) : !this$awardType.equals(other$awardType))
            return false;
        Object this$coinType = getCoinType(), other$coinType = other.getCoinType();
        if ((this$coinType == null) ? (other$coinType != null) : !this$coinType.equals(other$coinType))
            return false;
        Object this$money = getMoney(), other$money = other.getMoney();
        return !((this$money == null) ? (other$money != null) : !this$money.equals(other$money));
    }

    protected boolean canEqual(Object other) {
        return other instanceof cn.huanzi.qch.baseadmin.userlevel.pojo.MoneyRecord;
    }

    public String toString() {
        return "MoneyRecord(id=" + getId() + ", name=" + getName() + ", awardType=" + getAwardType() + ", coinType=" + getCoinType() + ", money=" + getMoney() + ")";
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAwardType() {
        return this.awardType;
    }

    public String getCoinType() {
        return this.coinType;
    }

    public BigDecimal getMoney() {
        return this.money;
    }
}
