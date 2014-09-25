package com.yidumen.dao.constant;

/**
 *
 * @author 蔡迪旻<yidumen.com>
 */
public enum VideoStatus {
    PUBLISH("发布"),
    VERIFY("审核"),
    ARCHIVE("存档");
    
    private final String descript;

    private VideoStatus(String descript) {
        this.descript = descript;
    }

    public String getDescript() {
        return descript;
    }
    
}
