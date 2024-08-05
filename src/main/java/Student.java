

public class Student{

    private String idSv;
    private String name;
    private String address;
    private int age;

    public Student() {
    }

    public Student(String idSv, String name, String address, int age) {
        this.idSv = idSv;
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public Student(String idSv) {
        this.idSv=idSv;
    }

    public String getIdSv() {
        return idSv;
    }

    public void setIdSv(String idSv) {
        this.idSv = idSv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "idSv='" + idSv + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }

}
