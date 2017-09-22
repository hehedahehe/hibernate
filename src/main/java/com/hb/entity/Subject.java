package com.hb.entity;


/*
 * @desc
 * @author lirb
 * @datetime 2017/9/11,11:11
 */
public class Subject {
    //目前当做组件类，没有标识符属性
    private String name;

    public String getSubjectDesc() {
        return subjectDesc;
    }

    public void setSubjectDesc(String subjectDesc) {
        this.subjectDesc = subjectDesc;
    }

    private String subjectDesc;

    private Students students;

    public Students getStudents() {
        return students;
    }

    public void setStudents(Students students) {
        this.students = students;
    }



    public Subject(){}

    public Subject(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject that = (Subject) o;

//        if (id != that.id) return false;
        //保证了equals的话，其hashcode必须相同
        if(name!=null){
            if(!name.equals(that.name))
                return false;
        }else if(that.name==null){
            return true;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
