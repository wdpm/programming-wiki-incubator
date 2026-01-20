package optionals;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public class HR {
    private static final HR INSTANCE = new HR();

    private Map<Integer, Employee> employeeMap = new ConcurrentHashMap<>();

    private HR() {}

    public static HR getInstance() {
        return INSTANCE;
    }

    public int hire(Employee e) {
        Objects.requireNonNull(e);
        employeeMap.put(e.getId(), e);
        return employeeMap.containsKey(e.getId()) ? 1 : 0;
    }

    public int hire(List<Employee> emps) {
        int before = employeeMap.size();
        Map<Integer, Employee> newEmps = emps.stream()
                .collect(Collectors.toMap(Employee::getId, Function.identity()));
        employeeMap.putAll(newEmps);
        return employeeMap.size() - before;
    }


    public int fire(int id) {
        Employee emp = employeeMap.remove(id);
        return emp != null ? 1 : 0;
    }

    public int reset() {
        employeeMap.clear();
        return employeeMap.size();
    }

    public Optional<Employee> findEmployeeById(int id) {
        return Optional.ofNullable(employeeMap.get(id));
    }

    // Check the Optional content before extracting values
    public List<Employee> findEmployeesByIds1(List<Integer> ids) {
        return ids.stream()
                .map(this::findEmployeeById)
                // returns Stream<Optional<Employee>>
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    // Use a flatmap instead
    public List<Employee> findEmployeesByIds2(List<Integer> ids) {
        return ids.stream()
                .map(this::findEmployeeById)
                .flatMap(optional -> optional.map(Stream::of).orElseGet(Stream::empty))
//                .flatMap(optional -> optional.isPresent() ?
//                        Stream.of(optional.get()) :
//                        Stream.empty())
                .collect(Collectors.toList());
    }
}
