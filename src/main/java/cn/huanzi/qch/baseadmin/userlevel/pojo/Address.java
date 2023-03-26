package cn.huanzi.qch.baseadmin.userlevel.pojo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Id;

public class Address implements Serializable {
    @Id
    private Integer id;

    private String address;

    private String label;

    private String chain;

    private String currBalance;

    private String usdtBalance;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public void setCurrBalance(String currBalance) {
        this.currBalance = currBalance;
    }

    public void setUsdtBalance(String usdtBalance) {
        this.usdtBalance = usdtBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return Objects.equals(id, address1.id) && Objects.equals(address, address1.address) && Objects.equals(label, address1.label) && Objects.equals(chain, address1.chain) && Objects.equals(currBalance, address1.currBalance) && Objects.equals(usdtBalance, address1.usdtBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, label, chain, currBalance, usdtBalance);
    }

    public String toString() {
        return "Address(id=" + getId() + ", address=" + getAddress() + ", label=" + getLabel() + ", chain=" + getChain() + ", currBalance=" + getCurrBalance() + ", usdtBalance=" + getUsdtBalance() + ")";
    }

    public Integer getId() {
        return this.id;
    }

    public String getAddress() {
        return this.address;
    }

    public String getLabel() {
        return this.label;
    }

    public String getChain() {
        return this.chain;
    }

    public String getCurrBalance() {
        return this.currBalance;
    }

    public String getUsdtBalance() {
        return this.usdtBalance;
    }
}
