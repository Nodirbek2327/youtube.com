package com.example.entity;

import com.example.enums.PlaylistStatus;
import com.example.enums.VideoType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Table(name = "video")
@Entity
public class VideoEntity extends BaseStringEntity{

    @Column(name = "preview_attach_id", nullable = false)
    private String previewAttachId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preview_attach_id", insertable = false, updatable = false)
    private AttachEntity previewAttach;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

    @Column(name = "attach_id", nullable = false)
    private String attachId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PlaylistStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private VideoType type;

    @Column(name = "shared_count")
    private Integer sharedCount = 0;

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "channel_id", nullable = false)
    private String channelId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private ChannelEntity channel;

    @Column(name = "like_count")
    private Integer likeCount = 0;

    @Column(name = "dislike_count")
    private Integer dislikeCount = 0;

}
