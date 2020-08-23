package git.soulbgm.common.pojo;

import com.github.pagehelper.Page;
import lombok.Data;

/**
 * 前端分页查询的统一格式
 *
 * @author SoulBGM
 * @date 2020-08-12
 */
@Data
public class PageInfo {

    /**
     * 当前页码
     */
    private int pageNum = 1;

    /**
     * 每页显示条数
     */
    private int pageSize = 10;

    /**
     * 数据总数
     */
    private long totalCount;

    /**
     * 总页数
     */
    private long totalPage;

    /**
     * 当前页数据
     */
    private Object data;

    public PageInfo() {
    }

    public static PageInfo getPageInfo(Page page) {
        return new PageInfo(page.getPageNum(), page.getPageSize(), page.getTotal(), page.getPages(), page.getResult());
    }

    public static PageInfo getPageInfo(int pageNum, int pageSize, long totalCount, long totalPage, Object data) {
        return new PageInfo(pageNum, pageSize, totalCount, totalPage, data);
    }

    public PageInfo(int pageNum, int pageSize, long totalCount, long totalPage, Object data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.data = data;
    }

}
