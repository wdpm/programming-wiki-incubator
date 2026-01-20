package optionals;

public class UseOptionalMap {
    public static void main(String[] args) {
        OptionalMap<String, Integer> uo = new OptionalMap<>();

        uo.put("a", 1);
        uo.put("b", 2);
        uo.put("c", 2);
        System.out.println(uo.getMap());       // {a=1, b=2, c=2}
        System.out.println(uo.getMap().get("d"));    // null
        System.out.println(uo.get("d"));             // Optional.empty
        System.out.println(uo.get("d").orElse(999)); // 999
    }
}
