package com.pjinky.prisoncore.bande;

import java.util.UUID;

public class Relations {
    public UUID gangUUID;
    enum Type{
        ALLY,
        RIVAL
    }

    public Type type;

    public Relations(UUID paramUUID, Type paramType){
        this.type = paramType;
        this.gangUUID = paramUUID;
    }
}
