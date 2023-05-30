package model;

public class Student {
    private String NIC;
    private String Name;
    private String Gender;
    private String DOB;
    private String Age;
    private String PassportNo;
    private String Address;
    private String ContactNo;
    private String Email;

    public Student() {
    }

    public Student(String NIC, String name, String gender, String DOB, String age, String passportNo, String address, String contactNo, String email) {
        this.NIC = NIC;
        this.Name = name;
        this.Gender = gender;
        this.DOB = DOB;
        this.Age = age;
        this.PassportNo = passportNo;
        this.Address = address;
        this.ContactNo = contactNo;
        this.Email = email;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getPassportNo() {
        return PassportNo;
    }

    public void setPassportNo(String passportNo) {
        PassportNo = passportNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return "Student{" +
                "NIC='" + NIC + '\'' +
                ", Name='" + Name + '\'' +
                ", Gender='" + Gender + '\'' +
                ", DOB='" + DOB + '\'' +
                ", Age='" + Age + '\'' +
                ", PassportNo='" + PassportNo + '\'' +
                ", Address='" + Address + '\'' +
                ", ContactNo='" + ContactNo + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }
}