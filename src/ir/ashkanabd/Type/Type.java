package ir.ashkanabd.Type;

import ir.ashkanabd.Context;

public class Type {
    private Integer id;
    private Context context;

    public Type(Context context) {
        this.context = context;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Context getContext() {
        return context;
    }
}
