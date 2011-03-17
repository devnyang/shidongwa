/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dalsong.util;


import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

/**
 * �?��用来表示每一句歌词的�? * 它封装了歌词的内容以及这句歌词的起始时间
 * 和结束时间，还有�?��实用的方�? * @author hadeslee
 */
public class Sentence implements Serializable {

    private static final long serialVersionUID = 20071125L;
    private String fromTime;//这句的起始时�?时间是以毫秒为单�?    private long toTime;//这一句的结束时间
    private String content;//这一句的内容
    private final static long DISAPPEAR_TIME = 1000L;//歌词从显示完到消失的时间

    public Sentence(String content, String fromTime) {
        this.content = content;
        this.fromTime = fromTime;

    }



    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }




    /**
     * 得到这一句的内容
     * @return 内容
     */
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String toString() {
        return "{" + fromTime + "(" + content + ")" ;
    }
    }
