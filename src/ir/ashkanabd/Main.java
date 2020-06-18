package ir.ashkanabd;

import ir.ashkanabd.View.View;

public class Main {

    public static void main(String[] args) {
        new Main().start();
    }

    private void start(){
        Context context = new Context("data");
        new View(context);
    }
}
