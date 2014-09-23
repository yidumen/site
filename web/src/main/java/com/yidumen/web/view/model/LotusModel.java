package com.yidumen.web.view.model;

/**
 * 包装佛经对象，将原文与释义放在一起
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class LotusModel {

    private String partIdentifier;
    private String title;
    private Long originalId;
    private Long vernacularId;

    public String getPartIdentifier() {
        return partIdentifier;
    }

    public void setPartIdentifier(String partIdentifier) {
        this.partIdentifier = partIdentifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getOriginalId() {
        return originalId;
    }

    public void setOriginalId(Long originalId) {
        this.originalId = originalId;
    }

    public Long getVernacularId() {
        return vernacularId;
    }

    public void setVernacularId(Long vernacularId) {
        this.vernacularId = vernacularId;
    }
}
