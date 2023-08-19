package com.example.entity;

import com.example.enums.PlaylistStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "play_list")
@Entity
public class PlaylistEntity extends BaseEntity{

    @Column(name = "channel_id")
    private String channelId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private ChannelEntity channel;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PlaylistStatus status=PlaylistStatus.PUBLIC;

    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "prt_id")
    private Integer prtId;
}
