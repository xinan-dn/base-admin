package cn.huanzi.qch.baseadmin.userlevel.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Id;

public class UserInfo implements Serializable {
    @Id
    private Integer id;

    private String name;

    private String parent;

    private Integer level;

    private Integer fzLevel;

    private Integer childrenNum;

    private BigDecimal money;

    private BigDecimal totalMoney;

    private String parentLongId;

    private String remark;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setFzLevel(Integer fzLevel) {
        this.fzLevel = fzLevel;
    }

    public void setChildrenNum(Integer childrenNum) {
        this.childrenNum = childrenNum;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public void setParentLongId(String parentLongId) {
        this.parentLongId = parentLongId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String toString() {
        return "UserInfo(id=" + getId() + ", name=" + getName() + ", parent=" + getParent() + ", level=" + getLevel() + ", fzLevel=" + getFzLevel() + ", childrenNum=" + getChildrenNum() + ", money=" + getMoney() + ", totalMoney=" + getTotalMoney() + ", parentLongId=" + getParentLongId() + ", remark=" + getRemark() + ")";
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getParent() {
        return this.parent;
    }

    public Integer getLevel() {
        return this.level;
    }

    public Integer getFzLevel() {
        return this.fzLevel;
    }

    public Integer getChildrenNum() {
        return this.childrenNum;
    }

    public BigDecimal getMoney() {
        return this.money;
    }

    public BigDecimal getTotalMoney() {
        return this.totalMoney;
    }

    public String getParentLongId() {
        return this.parentLongId;
    }

    public String getRemark() {
        return this.remark;
    }
}
