package com.zrz.entity;

import java.util.Vector;

public class MailBean {

    private String to;
    private String from;
    private String host;
    private String username;
    private String password;
    private String subject;
    private String content;
    Vector<String> file;
    private String filename;
    
    
    
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Vector<String> getFile(){
        return file;
    }
    
    public void attachFile(String fileName) {
        if(file == null)
            file = new Vector<String>();
        file.addElement(fileName);
    }
}
