package com.ketayao.util;

import org.apache.commons.lang3.StringUtils;

public class PageInfo {
    private int              totalPage         = 1;

    private int              prePage           = 1;

    private int              nextPage          = 1;

    private int              totalRec          = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private int              pageSize          = DEFAULT_PAGE_SIZE;

    private int              pageIndex         = 1;

    private String           url               = "";
    private String           pageHtml;
    private int              pageArraySize     = 10;

    public PageInfo() {
    }

    public PageInfo(int totalRec) {
        setTotalRec(totalRec);
    }

    public int getPageIndex() {
        if (this.pageIndex > this.totalPage) {
            this.pageIndex = this.totalPage;
        }

        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = (pageIndex > 0 ? pageIndex : 1);
    }

    public int getNextPage() {
        this.nextPage = (this.pageIndex + 1);
        if (this.nextPage > this.totalPage) {
            this.nextPage = this.totalPage;
        }

        return this.nextPage;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = (pageSize > 0 ? pageSize : 10);
    }

    public int getPrePage() {
        this.prePage = (this.pageIndex - 1);
        if (this.prePage < 1) {
            this.prePage = 1;
        }
        return this.prePage;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public int getTotalRec() {
        return this.totalRec;
    }

    public void setTotalRec(int totalRec) {
        this.totalRec = totalRec;
        this.totalPage = ((totalRec - 1) / this.pageSize + 1);
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPageHtml(String pageHtml) {
        this.pageHtml = pageHtml;
    }

    public String getPageHtml() {
        if ((this.pageHtml != null) && (!this.pageHtml.equals(""))) {
            return this.pageHtml;
        }

        return getPageArraySize();
    }

    public int getPageHtmlHeadSize() {
        return this.pageArraySize;
    }

    public void setPageHtmlHeadSize(int pageArraySize) {
        this.pageArraySize = pageArraySize;
    }

    public int getStartIndex() {
        return (getPageIndex() - 1) * getPageSize();
    }

    public int getEndIndex() {
        int ei = getPageIndex() * getPageSize();
        return ei > getTotalRec() ? getTotalRec() : ei;
    }

    private String getPageArraySize() {
        StringBuffer html = new StringBuffer();
        if (getPageIndex() != 1) {
            html.append("<a href=\"" + this.url + getPrePage() + "\">" + " &lt; " + "</a>");
        }

        if (getPageIndex() >= getPageHtmlHeadSize()) {
            html.append("<a href=\"" + this.url + 1 + "\">" + "&lt;&lt;" + "</a>");

            for (int i = 3; i > 0; i--) {
                html.append("<a href=\"" + this.url + (getPageIndex() - i) + "\">"
                            + (getPageIndex() - i) + "</a>");
            }

            html.append("<span class=\"current\">" + getPageIndex() + "</span>");

            for (int i = 1; (i <= 6) && (getPageIndex() + i < getTotalPage()); i++) {
                html.append("<a href=\"" + this.url + (getPageIndex() + i) + "\">"
                            + (getPageIndex() + i) + "</a>");
            }

            if (getPageIndex() + 6 < getTotalPage())
                html.append("<a href=\"" + this.url + getTotalPage() + "\">" + "&gt;&gt;" + "</a>");
        } else {
            for (int i = 1; (i <= getTotalPage()) && (i <= 10); i++) {
                if (i == getPageIndex())
                    html.append("<span class=\"current\">" + getPageIndex() + "</span>");
                else {
                    html.append("<a href=\"" + this.url + i + "\">" + i + "</a>");
                }
            }

            if (getTotalPage() > getPageHtmlHeadSize()) {
                html.append("<a href=\"" + this.url + getTotalPage() + "\">" + "&gt;&gt;" + "</a>");
            }
        }

        if (getPageIndex() != getTotalPage()) {
            html.append("<a href=\"" + this.url + getNextPage() + "\">" + " &gt; " + "</a>");
        }

        return html.toString();
    }

    public String getPageBar(String suffix) {
        if (StringUtils.isBlank(suffix)) {
            suffix = "";
        }
        StringBuffer html = new StringBuffer();
        html.append(
            "<span class=\"pages\">第" + getPageIndex() + "页，共 " + getTotalPage() + "页</span>");
        if (getPageIndex() != 1) {
            html.append("<a class=\"previouspostslink\" href=\"" + this.url + getPrePage() + suffix
                        + "\">" + " &lt; " + "</a>");
        }
        if (getPageIndex() >= getPageHtmlHeadSize()) {
            html.append(
                "<a class=\"first\" href=\"" + this.url + 1 + suffix + "\">" + "&lt;&lt;" + "</a>");
            for (int i = 3; i > 0; i--) {
                html.append("<a class=\"page larger\" href=\"" + this.url + (getPageIndex() - i)
                            + suffix + "\">" + (getPageIndex() - i) + "</a>");
            }
            html.append("<span class=\"current\">" + getPageIndex() + "</span>");
            for (int i = 1; (i <= 6) && (getPageIndex() + i < getTotalPage()); i++) {
                html.append("<a class=\"page larger\" href=\"" + this.url + (getPageIndex() + i)
                            + suffix + "\">" + (getPageIndex() + i) + "</a>");
            }
            if (getPageIndex() + 6 < getTotalPage()) {
                html.append("<a class=\"nextpostslink\" href=\"" + this.url + getTotalPage()
                            + suffix + "\">" + "&gt;&gt;" + "</a>");
            }
        } else {
            for (int i = 1; (i <= getTotalPage()) && (i <= 10); i++) {
                if (i == getPageIndex()) {
                    html.append("<span class=\"current\">" + getPageIndex() + "</span>");
                } else {
                    html.append("<a class=\"page larger\" href=\"" + this.url + i + suffix + "\">"
                                + i + "</a>");
                }
            }
            if (getTotalPage() > getPageHtmlHeadSize()) {
                html.append("<a class=\"last\" href=\"" + this.url + getTotalPage() + suffix + "\">"
                            + "&gt;&gt;" + "</a>");
            }
        }
        if (getPageIndex() != getTotalPage()) {
            html.append("<a class=\"nextpostslink\" href=\"" + this.url + getNextPage() + suffix
                        + "\">" + "&gt;" + "</a>");
        }
        return html.toString();
    }

    public String getBlogStyle() {
        return getPageBar(null);
    }
}