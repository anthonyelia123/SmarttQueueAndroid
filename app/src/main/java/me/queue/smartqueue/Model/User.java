package me.queue.smartqueue.Model;

public class User {
    public String id;
    public String firstName;
    public String lastName;
    public String email;
    public String phone;
    public String age;
    public String type;
    public int skipped;
    public boolean skip;
    public boolean finished;

    public User(String id, String firstName ,String lastName,String email,String phone,String age, String type, int skipped){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.type = type;
        this.skipped = skipped;
        this.skip = false;
        this.finished = false;
    }
    public User(){}

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getAge(){
        return age;
    }
    public void setAge(String age){
        this.age = age;
    }

    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }

    public int getSkipped(){
        return skipped;
    }
    public void setSkipped(int skipped){
        this.skipped = skipped;
    }

    public boolean isSkip(){
        return skip;
    }
    public void setSkip(boolean skipped){
        this.skip = skip;
    }

    public boolean isFinished(){
        return finished;
    }
    public void setFinished(boolean finished){
        this.finished = finished;
    }
}
