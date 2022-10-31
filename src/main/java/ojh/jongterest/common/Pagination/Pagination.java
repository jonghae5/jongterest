package ojh.jongterest.common.Pagination;

import lombok.Data;

@Data
public class Pagination {

    private int firstPage = 1;
    private int lastPage = 1;
    private int currentPage = 1;
    private int nextPage = 1;
    private int previousPage = 1;

    public void create(int page ) {
        this.currentPage = page;
        this.firstPage = Math.min(firstPage, currentPage);
        this.lastPage = Math.max(lastPage, currentPage);
        this.nextPage = currentPage + 1;
        this.previousPage = currentPage - 1;
    }
}
