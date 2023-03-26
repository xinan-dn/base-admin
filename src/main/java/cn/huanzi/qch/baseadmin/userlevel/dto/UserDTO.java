package cn.huanzi.qch.baseadmin.userlevel.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class UserDTO implements Serializable {
    private String name;

    private String parent;

    private Integer level;

    private Integer fzLevel;

    private String parentLongId;

    private Integer childrenNum;

    private BigDecimal money;

    private BigDecimal totalMoney;

    private String remark;

    List<cn.huanzi.qch.baseadmin.userlevel.dto.UserDTO> children;

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

    public void setParentLongId(String parentLongId) {
        this.parentLongId = parentLongId;
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

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setChildren(List<cn.huanzi.qch.baseadmin.userlevel.dto.UserDTO> children) {
        this.children = children;
    }

    public String toString() {
        return "UserDTO(name=" + getName() + ", parent=" + getParent() + ", level=" + getLevel() + ", fzLevel=" + getFzLevel() + ", parentLongId=" + getParentLongId() + ", childrenNum=" + getChildrenNum() + ", money=" + getMoney() + ", totalMoney=" + getTotalMoney() + ", remark=" + getRemark() + ", children=" + getChildren() + ")";
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

    public String getParentLongId() {
        return this.parentLongId;
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

    public String getRemark() {
        return this.remark;
    }

    public List<cn.huanzi.qch.baseadmin.userlevel.dto.UserDTO> getChildren() {
        return this.children;
    }

    public UserDTO(String name, String parent, BigDecimal money) {
        this.name = name;
        this.parent = parent;
        this.money = money;
    }

    public UserDTO() {}
}
