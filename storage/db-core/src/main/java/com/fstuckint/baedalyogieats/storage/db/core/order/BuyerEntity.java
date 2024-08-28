package com.fstuckint.baedalyogieats.storage.db.core.order;

import com.fstuckint.baedalyogieats.storage.db.core.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "p_buyers")
public class BuyerEntity extends BaseEntity {

    private String nickname;

    private UUID userUuid;

    @Builder
    public BuyerEntity(String nickname, UUID userUuid) {
        this.nickname = nickname;
        this.userUuid = userUuid;
    }

}
