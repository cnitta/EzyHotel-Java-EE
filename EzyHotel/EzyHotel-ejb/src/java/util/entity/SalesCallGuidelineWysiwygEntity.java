/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Wai Kit
 */
@Entity
public class SalesCallGuidelineWysiwygEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesCallGuidelineWysiwygId;
    private String keyString;
    private String text;
    private String type;
    
    @ManyToOne
    private SalesCallGuidelineEntity salesCallGuideline;
    
    public SalesCallGuidelineWysiwygEntity(Long salesCallGuidelineWysiwygId, String keyString, String text, String type) {
        this.salesCallGuidelineWysiwygId = salesCallGuidelineWysiwygId;
        this.keyString = keyString;
        this.text = text;
        this.type = type;
    }

    public SalesCallGuidelineWysiwygEntity() {}

    /**
     * @return the salesCallGuidelineWysiwyhId
     */
    public Long getSalesCallGuidelineWysiwygId() {
        return salesCallGuidelineWysiwygId;
    }

    /**
     * @param salesCallGuidelineWysiwygId the salesCallGuidelineWysiwygId to set
     */
    public void setSalesCallGuidelineWysiwygId(Long salesCallGuidelineWysiwygId) {
        this.salesCallGuidelineWysiwygId = salesCallGuidelineWysiwygId;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return keyString;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String keyString) {
        this.keyString = keyString;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the salesCallGuidelineEntity
     */
    public SalesCallGuidelineEntity getSalesCallGuidelineEntity() {
        return salesCallGuideline;
    }

    /**
     * @param salesCallGuidelineEntity the salesCallGuidelineEntity to set
     */
    public void setSalesCallGuidelineEntity(SalesCallGuidelineEntity salesCallGuideline) {
        this.salesCallGuideline = salesCallGuideline;
    }
    
        @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.salesCallGuidelineWysiwygId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SalesCallGuidelineWysiwygEntity other = (SalesCallGuidelineWysiwygEntity) obj;
        if (!Objects.equals(this.salesCallGuidelineWysiwygId, other.salesCallGuidelineWysiwygId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SalesCallGuidelineWysiwygEntity{" + "salesCallGuidelineWysiwygId=" + salesCallGuidelineWysiwygId + '}';
    }
    
}
