class Student {
    private name;
    private age;

    constructor(name, age) {
        this.name = name;
        this.age = age;
    }

    get userInfo() {
        return 'name：' + this.name + ',age：' + this.age;
    }


}

const stu = new Student('foo', 100);
console.log(stu.userInfo);