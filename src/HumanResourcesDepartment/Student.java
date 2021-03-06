package HumanResourcesDepartment;

/**
 * Created by andriy on 23.06.15.
 */


import java.text.DateFormat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Collator;
import java.util.Date;
import java.util.Locale;

public class Student implements Comparable {


    private int studentId;      // поле ИД СТУДЕНТА
    private String firstName;   // поле ИМЯ
    private String surName;     // поле ФАМИЛИЯ
    private String patronymic;  // поле ОТЧЕСТВО
    private Date dateOfBirth;   // поле ДАТА РОЖДЕНИЯ
    private char sex;           // поле ПОЛ
    private int groupId;        // поле ИД ГРУППЫ
    private int educationYear;  // поле ГОД ОБУЧЕНИЯ

    public Student(ResultSet rs) throws SQLException {
        setStudentId(rs.getInt(1));
        setFirstName(rs.getString(2));
        setPatronymic(rs.getString(3));
        setSurName(rs.getString(4));
        setSex(rs.getString(5).charAt(0));
        setDateOfBirth(rs.getDate(6));
        setGroupId(rs.getInt(7));
        setEducationYear(rs.getInt(8));
    }

    public Student() {
    }

    // get/set для ДАТА РОЖДЕНИЯ
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    // get/set для ГОД ОБУЧЕНИЯ
    public int getEducationYear() {
        return educationYear;
    }

    public void setEducationYear(int educationYear) {
        this.educationYear = educationYear;
    }

    // get/set для ИД ГРУППЫ
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    // get/set для ИД СТУДЕНТА
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    // get/set для ИМЯ
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // get/set для ОТЧЕСТВО
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    // get/set для ФАМИЛИЯ
    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    // get/set для ПОЛ
    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    // DateFormat - класс для преобразования даты
    // в строку в определеннном формате.
    // Подробнее смотрите документацию по этому методу
    public String toString() {
        return surName + " " + firstName + " " + patronymic + ", "
                + DateFormat.getDateInstance(DateFormat.SHORT).format(dateOfBirth)
                + ", Группа ИД=" + groupId + " Год:" + educationYear;
    }

    public int compareTo(Object obj) {
        Collator c = Collator.getInstance(new Locale("ru"));
        c.setStrength(Collator.PRIMARY);
        return c.compare(this.toString(), obj.toString());
    }
}