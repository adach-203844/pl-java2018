package pl.com.tt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by pwykowski
 */
public class Controller {

    public static void main(String[] args) {
        process("kaka");
    }

    public static int process(String input) {
        // Do some really time consuming processing...
//        try {
//            Thread.sleep(4000);
            final List<String> lista = new ArrayList<>();
            lista.add("adasd");
            lista.add("gsdfdf");
            lista.add("kaka");
            Stream<String> stream = lista.stream();
            Stream<String> kaka = stream.filter(e -> e.equals("kaka"));
            System.out.println(kaka);
//        } catch (InterruptedException e) {
            // Shhh, we do not like being woken up
        return 9;
        }
//        return 303;
//    }

}
