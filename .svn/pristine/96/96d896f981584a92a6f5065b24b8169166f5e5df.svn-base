package com.ceyi.project.dcjewelry.admin;

import pw.wecode.project.framework.jdbc.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lingh on 2017/4/5.
 */
public class DatatablesPage<T> {
    private int draw;
    private int recordsTotal;
    private int recordsFiltered;
    private List<T> data = new ArrayList<>();

    public DatatablesPage() {

    }

    public DatatablesPage(int total, List<T> data) {
        this.recordsTotal = total;
        this.recordsFiltered = total;
        this.data = data;
    }

    public DatatablesPage(List<T> data) {
        this(data.size(), data);
    }

    public DatatablesPage(Page<T> page) {
        this(page.getRecordCount(), page.getData());
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
