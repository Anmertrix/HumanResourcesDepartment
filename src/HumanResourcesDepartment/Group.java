package HumanResourcesDepartment;

/**
 * Created by andriy on 23.06.15.
 */
public class Group {

    private int groupId;       // поле ИД ГРУППЫ
    private String nameGroup;  // поле ИМЯ ГРУППЫ
    private String curator;     // поле КУРАТОР
    private String speciality;  // поле СПЕЦИАЛЬНОСТЬ

    // get/set для КУРАТОР
    public String getCurator() {
        return curator;
    }

    public void setCurator(String curator) {
        this.curator = curator;
    }

    // get/set для ИД ГРУППЫ
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    // get/set для ИМЯ ГРУППЫ
    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    // get/set для СПЕЦИАЛЬНОСТЬ
    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String toString() {
        return nameGroup;
    }
}
