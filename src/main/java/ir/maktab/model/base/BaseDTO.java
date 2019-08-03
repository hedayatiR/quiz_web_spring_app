package ir.maktab.model.base;

import java.io.Serializable;

public class BaseDTO<PK extends Serializable> implements Serializable {

    public PK id;

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }
}
