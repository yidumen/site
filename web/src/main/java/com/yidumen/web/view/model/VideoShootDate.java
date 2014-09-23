package com.yidumen.web.view.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class VideoShootDate implements Comparable<VideoShootDate> {

    private int year;
    private final List<String> month;

    public VideoShootDate() {
        month = new ArrayList<>();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void addMonth(String month) {
        this.month.add(month);
    }

    public List<String> getMonth() {
        return month;
    }

    @Override
    public int compareTo(VideoShootDate o) {
        return o.getYear() - this.year;
    }
}
