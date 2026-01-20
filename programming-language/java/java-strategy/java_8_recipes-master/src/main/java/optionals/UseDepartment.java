package optionals;

import java.util.Optional;

public class UseDepartment {
    public static void main(String[] args) {
        Manager mrSlate = new Manager("Mr. Slate");

        // Department with a manager
        Department d = new Department();
        d.setName("Construction");
        d.setBoss(mrSlate);

        System.out.println("Boss: " + d.getBoss());
        // prints: Boss: Optional[Manager{name='Mr. Slate'}]

        // Department without a manager
        Department d1 = new Department();
        System.out.println("Boss: " + d1.getBoss());
        // prints: Boss: Optional.empty

        // Can't call getName on an Optional, so either...
        System.out.println("Name: " +
                d.getBoss().orElse(new Manager("Unknown")).getName());
        // prints: Name: Mr. Slate

        System.out.println("Name: " +
                d.getBoss().map(Manager::getName)); // function<Manager,String>
        // prints: Name: Optional[Mr. Slate]

        System.out.println("Name: " +
                d1.getBoss().orElse(new Manager("Unknown")).getName());
        // prints: Name: Unknown

        // or...
        System.out.println("Name: " + d.getBoss().map(Manager::getName));
        // prints: Name: Optional[Mr. Slate]

        System.out.println("Name: " + d1.getBoss().map(Manager::getName));
        // prints: Name: Optional.empty

        Company co = new Company();
        co.setDepartment(d);

        System.out.println("Company Dept: " + co.getDepartment());
        // prints: Company Dept: Optional[Department{boss=Manager{name='Mr. Slate'}}]

        System.out.println("Company Dept Manager: " + co.getDepartment()
                .map(Department::getBoss)); // function <Department,Optional<Manager>>
        // prints: Company Dept Manager: Optional[Optional[Manager{name='Mr. Slate'}]]

        System.out.println(
                co.getDepartment()
                        .flatMap(Department::getBoss)
                        .map(Manager::getName));

        Optional<Company> company = Optional.of(co);

        System.out.println(
                company
                        .flatMap(Company::getDepartment)
                        .flatMap(Department::getBoss)
                        .map(Manager::getName).orElse("No Manager Name")
        );

    }
}
