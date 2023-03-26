package cn.huanzi.qch.baseadmin.common.pojo;

import cn.huanzi.qch.baseadmin.util.CopyUtil;
import lombok.Data;
import org.hibernate.query.internal.NativeQueryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * 分页对象
 */
@Data
public class PageInfo<M> {
    private int page;

    private int pageSize;

    private String sidx;

    private String sord;

    private List<M> rows;

    private int records;

    private int total;

    private String awardType;

    private String coinType;

    private List<String> awardTypeList;

    private List<String> coinTypeList;

    public void setPage(int page) {
        this.page = page;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public void setRows(List<M> rows) {
        this.rows = rows;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setAwardType(String awardType) {
        this.awardType = awardType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public void setAwardTypeList(List<String> awardTypeList) {
        this.awardTypeList = awardTypeList;
    }

    public void setCoinTypeList(List<String> coinTypeList) {
        this.coinTypeList = coinTypeList;
    }

    public String toString() {
        return "PageInfo(page=" + getPage() + ", pageSize=" + getPageSize() + ", sidx=" + getSidx() + ", sord=" + getSord() + ", rows=" + getRows() + ", records=" + getRecords() + ", total=" + getTotal() + ", awardType=" + getAwardType() + ", coinType=" + getCoinType() + ", awardTypeList=" + getAwardTypeList() + ", coinTypeList=" + getCoinTypeList() + ")";
    }

    public int getPage() {
        return this.page;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public String getSidx() {
        return this.sidx;
    }

    public String getSord() {
        return this.sord;
    }

    public List<M> getRows() {
        return this.rows;
    }

    public int getRecords() {
        return this.records;
    }

    public int getTotal() {
        return this.total;
    }

    public String getAwardType() {
        return this.awardType;
    }

    public String getCoinType() {
        return this.coinType;
    }

    public List<String> getAwardTypeList() {
        return this.awardTypeList;
    }

    public List<String> getCoinTypeList() {
        return this.coinTypeList;
    }

    public static <M> cn.huanzi.qch.baseadmin.common.pojo.PageInfo<M> of(Page page, Class<M> entityModelClass) {
        int records = (int)page.getTotalElements();
        int pageSize = page.getSize();
        int total = (records % pageSize == 0) ? (records / pageSize) : (records / pageSize + 1);
        cn.huanzi.qch.baseadmin.common.pojo.PageInfo<M> pageInfo = new cn.huanzi.qch.baseadmin.common.pojo.PageInfo<>();
        pageInfo.setPage(page.getNumber() + 1);
        pageInfo.setPageSize(pageSize);
        String sortString = page.getSort().toString();
        if (!"UNSORTED".equals(sortString)) {
            String[] split = sortString.split(":");
            pageInfo.setSidx(split[0].trim());
            pageInfo.setSord(split[1].trim().toLowerCase());
        }
        pageInfo.setRows(CopyUtil.copyList(page.getContent(), entityModelClass));
        pageInfo.setRecords(records);
        pageInfo.setTotal(total);
        return pageInfo;
    }

    /**
     * 获取JPA的分页对象
     */
    public static Page getJpaPage(Query query, PageRequest pageRequest, EntityManager em) {
        query.setFirstResult((int) pageRequest.getOffset());
        query.setMaxResults(pageRequest.getPageSize());

        //获取分页结果
        return PageableExecutionUtils.getPage(query.getResultList(), pageRequest, () -> {
            //设置countQuerySQL语句
            Query countQuery = em.createNativeQuery("select count(*) from ( " + ((NativeQueryImpl) query).getQueryString() + " ) count_table");
            //设置countQuerySQL参数
            query.getParameters().forEach(parameter -> countQuery.setParameter(parameter.getName(), query.getParameterValue(parameter.getName())));
            //返回一个总数
            return Long.valueOf(countQuery.getResultList().get(0).toString());
        });
    }
}
