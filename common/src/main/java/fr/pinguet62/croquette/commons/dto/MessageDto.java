package fr.pinguet62.croquette.commons.dto;

import java.util.Date;

public final class MessageDto {

    private String content;

    private Date date;

    private Integer id;

    private Boolean sent;

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getSent() {
        return sent;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

}
