package com.gplucky.task.bean;

/**
 * Created by ehsy_it on 2017/1/26.
 */
public class StockReq {

    /**
     * a表示A股，b表示B股,默认所有
     */
    private static final String STOCK_A = "a";
    /**
     * a表示A股，b表示B股,默认所有
     */
    private static final String STOCK_B = "b";
    /**
     * 每页返回条数,1(20条默认),2(40条),3(60条),4(80条)
     */
    private static final int TYPE_1 = 1;
    /**
     * 每页返回条数,1(20条默认),2(40条),3(60条),4(80条)
     */
    private static final int TYPE_2 = 2;
    /**
     * 每页返回条数,1(20条默认),2(40条),3(60条),4(80条)
     */
    private static final int TYPE_3 = 3;
    /**
     * 每页返回条数,1(20条默认),2(40条),3(60条),4(80条)
     */
    private static final int TYPE_4 = 4;

    private String key;
    private String stock;
    /**
     * 第几页,默认第1页
     */
    private int page;
    /**
     * 	每页返回条数,1(20条默认),2(40条),3(60条),4(80条)
     */
    private int type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
