package com.ivan.url_shortener;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;


@Entity
public class ShortUrl
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String longUrl;
    private String shortCode;
    private int clicks = 0;
    private LocalDateTime createdAt = LocalDateTime.now();
    
    //Getter and setter  
    public Long getId()
    {
        return id;
    } 
    public void setId(Long id) 
    {
        this.id = id;
    }
    public String getLongUrl()
    {
        return longUrl;
    }
    public void setLongUrl(String longUrl)
    {
        this.longUrl = longUrl;
    }
    public String getShortCode()
    {
        return shortCode;
    }
    public void setShortCode(String shortCode)
    {
        this.shortCode = shortCode;
    }
    public int getClicks()
    {
        return clicks;
    }
    public void setClicks(int clicks)
    {
        this.clicks = clicks;
    }
    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }
}
