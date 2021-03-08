import java.util.List;
import java.util.Set;
import java.util.TreeSet;

class Holder<T> {
    private T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}

class Main {
    public static void main(String... args) {
        // initialize an instance of Holder to make the code below compiled
        Holder<String> holder = new Holder<>();
        TreeSet<String> set = new TreeSet<>(Set.of("Gamma", "Alpha", "Omega"));

        // do not change the code below
        holder.set("This is an instance of String");

        String value = holder.get();
        System.out.println(value);
    }
}