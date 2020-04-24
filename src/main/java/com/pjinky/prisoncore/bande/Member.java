package com.pjinky.prisoncore.bande;

import java.util.UUID;

public class Member {
    enum Rank {
        PRESIDENT,
        VICE_PRESIDENT,
        SECRETARY,
        SLAVE

    }

    public Rank rank;
    public UUID uuid;

    public Member(Rank paramRank, UUID paramUUID){
        this.rank = paramRank;
        this.uuid = paramUUID;
    }
}
