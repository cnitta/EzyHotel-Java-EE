/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import util.enumeration.BookmarkStatusEnum;

/**
 *
 * @author vincentyeo
 */
@Entity
public class BookmarkEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkId;

    @Temporal(TemporalType.TIMESTAMP)     
    private Date dateBookmarked;
    @Enumerated(EnumType.STRING)
    private BookmarkStatusEnum bookmarkStatusEnum;

    @ManyToOne
    private CustomerEntity customer;
    
    @ManyToOne
    private AffiliateContentEntity affiliateContent;

    public BookmarkEntity() {
    }

    public BookmarkEntity(Date dateBookmarked, BookmarkStatusEnum bookmarkStatusEnum) {
        this.dateBookmarked = dateBookmarked;
        this.bookmarkStatusEnum = bookmarkStatusEnum;
    }

        
    public Date getDateBookmarked() {
        return dateBookmarked;
    }

    public void setDateBookmarked(Date dateBookmarked) {
        this.dateBookmarked = dateBookmarked;
    }

    public BookmarkStatusEnum getBookmarkStatusEnum() {
        return bookmarkStatusEnum;
    }

    public void setBookmarkStatusEnum(BookmarkStatusEnum bookmarkStatusEnum) {
        this.bookmarkStatusEnum = bookmarkStatusEnum;
    }

    public Long getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(Long bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public AffiliateContentEntity getAffiliateContent() {
        return affiliateContent;
    }

    public void setAffiliateContent(AffiliateContentEntity affiliateContent) {
        this.affiliateContent = affiliateContent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookmarkId != null ? bookmarkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the bookmarkId fields are not set
        if (!(object instanceof BookmarkEntity)) {
            return false;
        }
        BookmarkEntity other = (BookmarkEntity) object;
        if ((this.bookmarkId == null && other.bookmarkId != null) || (this.bookmarkId != null && !this.bookmarkId.equals(other.bookmarkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.entity.BookmarkEntity[ id=" + bookmarkId + " ]";
    }
    
}
