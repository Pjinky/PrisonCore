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
    UUID uuid;
}
