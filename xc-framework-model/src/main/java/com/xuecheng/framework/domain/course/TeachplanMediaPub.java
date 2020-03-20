package com.xuecheng.framework.domain.course;

import lombok.Data;
import lombok.ToString;
import lombok.extern.apachecommons.CommonsLog;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 2018/2/7.
 */
@Data
@ToString
@Entity
@Table(name = "teachplan_media_pub")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class TeachplanMediaPub implements Serializable {
    private static final long serialVersionUID = -916357110051689485L;
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(name = "teachplan_id")
    private String teachplanId;

    @Column(name = "media_id")
    private String mediaId;

    @Column(name = "media_fileoriginalname")
    private String mediaFileOriginalName;

    @Column(name = "media_url")
    private String mediaUrl;
    private String courseId;

    @Column(name = "timestamp")
    private Date timestamp;

}
