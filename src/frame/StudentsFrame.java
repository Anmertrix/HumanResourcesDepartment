package frame;

/**
 * Created by andriy on 23.06.15.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import javax.swing.*;

import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import HumanResourcesDepartment.Group;
import HumanResourcesDepartment.ManagementSystem;
import HumanResourcesDepartment.Student;

public class StudentsFrame extends JFrame implements ActionListener, ListSelectionListener, ChangeListener {
    private static final String MOVE_GR = "moveGroup";
    private static final String CLEAR_GR = "clearGroup";
    private static final String INSERT_ST = "insertStudent";
    private static final String UPDATE_ST = "updateStudent";
    private static final String DELETE_ST = "deleteStudent";
    private static final String ALL_STUDENTS = "allStudent";

    private ManagementSystem ms = null;
    private JList grpList;
    private JTable stdList;
    private JSpinner spYear;

    public StudentsFrame() throws Exception {

        getContentPane().setLayout(new BorderLayout());  // Устанавливаем layout для всей клиентской части формы

        JMenuBar menuBar = new JMenuBar(); // Создаем строку меню
        JMenu menu = new JMenu("Отчеты");  // Создаем выпадающее меню
        JMenuItem menuItem = new JMenuItem("Все студенты"); // Создаем пункт в выпадающем меню
        menuItem.setName(ALL_STUDENTS);
        menuItem.addActionListener(this);  // Добавляем листенер
        menu.add(menuItem);         // Вставляем пункт меню в выпадающее меню
        menuBar.add(menu);          // Вставляем выпадающее меню в строку меню
        setJMenuBar(menuBar);       // Устанавливаем меню для формы

        JPanel top = new JPanel();  // Создаем верхнюю панел, где будет поле для ввода года
        top.setLayout(new FlowLayout(FlowLayout.LEFT));  // Устанавливаем для нее layout

        top.add(new JLabel("Год обучения:"));  // Вставляем пояснительную надпись
        // Делаем спин-поле
        //   1. Задаем модель поведения - только цифры
        //   2. Вставляем в панель
        SpinnerModel sm = new SpinnerNumberModel(2006, 1900, 2100, 1);
        spYear = new JSpinner(sm);
        spYear.addChangeListener(this);   // Добавляем листенер
        top.add(spYear);

        JPanel bot = new JPanel();   // Создаем нижнюю панель и задаем ей layout
        bot.setLayout(new BorderLayout());

        // Создаем левую панель для вывода списка групп
        // Она у нас
        GroupPanel left = new GroupPanel();
        // Задаем layout и задаем "бордюр" вокруг панели
        left.setLayout(new BorderLayout());
        left.setBorder(new BevelBorder(BevelBorder.LOWERED));

        ms = ManagementSystem.getInstance();  // Получаем коннект к базе и создаем объект ManagementSystem
        Vector<Group> gr = new Vector<Group>(ms.getGroups());   // Получаем список групп
        left.add(new JLabel("Группы:"), BorderLayout.NORTH);    // Создаем надпись
        // Создаем визуальный список и вставляем его в скроллируемую
        // панель, которую в свою очередь уже кладем на панель left
        grpList = new JList(gr);
        grpList.addListSelectionListener(this);   // Добавляем листенер
        grpList.setSelectedIndex(0);              // Сразу выделяем первую группу
        left.add(new JScrollPane(grpList), BorderLayout.CENTER);

        JButton btnMvGr = new JButton("Переместить");   // Создаем кнопки для групп
        btnMvGr.setName(MOVE_GR);
        JButton btnClGr = new JButton("Очистить");
        btnClGr.setName(CLEAR_GR);
        btnMvGr.addActionListener(this);  // Добавляем листенер
        btnClGr.addActionListener(this);  // Добавляем листенер
        // Создаем панель, на которую положим наши кнопки и кладем ее вниз
        JPanel pnlBtnGr = new JPanel();
        pnlBtnGr.setLayout(new GridLayout(1, 2));
        pnlBtnGr.add(btnMvGr);
        pnlBtnGr.add(btnClGr);
        left.add(pnlBtnGr, BorderLayout.SOUTH);

        JPanel right = new JPanel();           // Создаем правую панель для вывода списка студентов
        right.setLayout(new BorderLayout());   // Задаем layout и задаем "бордюр" вокруг панели
        right.setBorder(new BevelBorder(BevelBorder.LOWERED));

        right.add(new JLabel("Студенты:"), BorderLayout.NORTH);   // Создаем надпись
        // Создаем таблицу и вставляем ее в скроллируемую
        // панель, которую в свою очередь уже кладем на панель right
        // Наша таблица пока ничего не умеет - просто положим ее как заготовку
        // Сделаем в ней 4 колонки - Фамилия, Имя, Отчество, Дата рождения
        stdList = new JTable(1, 4);
        right.add(new JScrollPane(stdList), BorderLayout.CENTER);

        JButton btnAddSt = new JButton("Добавить"); // Создаем кнопки для студентов
        btnAddSt.setName(INSERT_ST);
        btnAddSt.addActionListener(this);
        JButton btnUpdSt = new JButton("Исправить");
        btnUpdSt.setName(UPDATE_ST);
        btnUpdSt.addActionListener(this);
        JButton btnDelSt = new JButton("Удалить");
        btnDelSt.setName(DELETE_ST);
        btnDelSt.addActionListener(this);

        // Создаем панель, на которую положим наши кнопки и кладем ее вниз
        JPanel pnlBtnSt = new JPanel();
        pnlBtnSt.setLayout(new GridLayout(1, 3));
        pnlBtnSt.add(btnAddSt);
        pnlBtnSt.add(btnUpdSt);
        pnlBtnSt.add(btnDelSt);
        right.add(pnlBtnSt, BorderLayout.SOUTH);

        // Вставляем панели со списками групп и студентов в нижнюю панель
        bot.add(left, BorderLayout.WEST);
        bot.add(right, BorderLayout.CENTER);

        // Вставляем верхнюю и нижнюю панели в форму
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(bot, BorderLayout.CENTER);

        // Задаем границы формы
        setBounds(100, 100, 700, 500);
    }

    // Метод для обеспечения интерфейса ActionListener
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Component) {
            Component c = (Component) e.getSource();
            if (c.getName().equals(MOVE_GR)) {
                moveGroup();
            }
            if (c.getName().equals(CLEAR_GR)) {
                clearGroup();
            }
            if (c.getName().equals(ALL_STUDENTS)) {
                showAllStudents();
            }
            if (c.getName().equals(INSERT_ST)) {
                insertStudent();
            }
            if (c.getName().equals(UPDATE_ST)) {
                updateStudent();
            }
            if (c.getName().equals(DELETE_ST)) {
                deleteStudent();
            }
        }
    }

    // Метод для обеспечения интерфейса ListSelectionListener
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            reloadStudents();
        }
    }
    // Метод для обеспечения интерфейса ChangeListener
    public void stateChanged(ChangeEvent e) {
        reloadStudents();
    }

    // метод для обновления списка студентов для определенной группы
    void reloadStudents() {
        // Создаем анонимный класс для потока
        Thread t = new Thread() {
            // Переопределяем в нем метод run
            public void run() {
                if (stdList != null) {
                    // Получаем выделенную группу
                    Group g = (Group) grpList.getSelectedValue();
                    // Получаем число из спинера
                    int y = ((SpinnerNumberModel) spYear.getModel()).getNumber().intValue();
                    try {
                        // Получаем список студентов
                        Collection<Student> s = ms.getStudentsFromGroup(g, y);
                        // И устанавливаем модель для таблицы с новыми данными
                        stdList.setModel(new StudentTableModel(new Vector<Student>(s)));
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
                    }
                }
                // Вводим искусственную задержку на 3 секунды
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                }
            }
            // Окончание нашего метода run
        };
        // Окончание определения анонимного класса

        // И теперь мы запускаем наш поток
        t.start();

    }

    // метод для показа всех студентов
    private void showAllStudents() {
        // Создаем анонимный класс для потока
        Thread t = new Thread() {
            // Переопределяем в нем метод run
            public void run() {
                if (stdList != null) {
                    try {
                        // Получаем список студентов
                        Collection<Student> s = ms.getAllStudents();
                        // И устанавливаем модель для таблицы с новыми данными
                        stdList.setModel(new StudentTableModel(new Vector<Student>(s)));
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
                    }
                }
                // Вводим искусственную задержку на 3 секунды
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                }
            }
            // Окончание нашего метода run
        };
        // Окончание определения анонимного класса

        // И теперь мы запускаем наш поток
        t.start();
    }

    // метод для переноса группы
    private void moveGroup() {
        Thread t = new Thread() {
            public void run() {
                // Если группа не выделена - выходим. Хотя это крайне маловероятно
                if (grpList.getSelectedValue() == null) {
                    return;
                }
                try {
                    // Получаем выделенную группу
                    Group g = (Group) grpList.getSelectedValue();
                    // Получаем число из спинера
                    int y = ((SpinnerNumberModel) spYear.getModel()).getNumber().intValue();
                    // Создаем наш диалог
                    GroupDialog gd = new GroupDialog(y, ms.getGroups());
                    // Задаем ему режим модальности - нельзя ничего кроме него выделить
                    gd.setModal(true);
                    // Показываем диалог
                    gd.setVisible(true);
                    // Если нажали кнопку OK - перемещаем в новую группу с новым годом
                    // и перегружаем список студентов
                    if (gd.getResult()) {
                        ms.moveStudentsToGroup(g, y, gd.getGroup(), gd.getYear());
                        reloadStudents();
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
                }
            }
        };
        t.start();
    }

    // метод для очистки группы
    private void clearGroup() {
        Thread t = new Thread() {
            public void run() {
                // Проверяем - выделена ли группа
                if (grpList.getSelectedValue() != null) {
                    if (JOptionPane.showConfirmDialog(StudentsFrame.this,
                            "Вы хотите удалить студентов из группы?", "Удаление студентов",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        // Получаем выделенную группу
                        Group g = (Group) grpList.getSelectedValue();
                        // Получаем число из спинера
                        int y = ((SpinnerNumberModel) spYear.getModel()).getNumber().intValue();
                        try {
                            // Удалили студентов из группы
                            ms.removeStudentsFromGroup(g, y);
                            // перегрузили список студентов
                            reloadStudents();
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
                        }
                    }
                }
            }
        };
        t.start();
    }

    // метод для добавления студента
    private void insertStudent() {
        Thread t = new Thread() {
            public void run() {
                try {
                    // Создаем наш диалог
                    StudentDialog sd = new StudentDialog(ms.getGroups(), true, StudentsFrame.this);

                    // Задаем ему режим модальности - нельзя ничего кроме него выделить
                    sd.setModal(true);
                    // Показываем диалог
                    sd.setVisible(true);
                    // Если нажали кнопку OK - перемещаем в новую группу с новым годом
                    // и перегружаем список студентов
                    if (sd.getResult()) {
                        Student s = sd.getStudent();
                        ms.insertStudent(s);
                        reloadStudents();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
                }
            }
        };
        t.start();
    }

    // метод для редактирования студента
    private void updateStudent() {
        Thread t = new Thread() {
            public void run() {
                if (stdList != null) {
                    StudentTableModel stm = (StudentTableModel) stdList.getModel();
                    // Проверяем - выделен ли хоть какой-нибудь студент
                    if (stdList.getSelectedRow() >= 0) {
                        // Вот где нам пригодился метод getStudent(int rowIndex)
                        Student s = stm.getStudent(stdList.getSelectedRow());
                        try {
                            // Исправляем данные на студента - поэтому false
                            // Также заметим, что необходимо указать не просто this, а StudentsFrame.this
                            // Иначе класс не будет воспринят - он же другой - анонимный
                            // Создаем наш диалог
                            StudentDialog sd = new StudentDialog(ms.getGroups(), false, StudentsFrame.this);
                            sd.setStudent(s);
                            // Задаем ему режим модальности - нельзя ничего кроме него выделить
                            sd.setModal(true);
                            // Показываем диалог
                            sd.setVisible(true);
                            // Если нажали кнопку OK - перемещаем в новую группу с новым годом
                            // и перегружаем список студентов
                            if (sd.getResult()) {
                                ms.deleteStudent(s);
                                ms.insertStudent(sd.getStudent());
                                reloadStudents();
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
                        }
                    } else {  // Если студент не выделен - сообщаем пользователю, что это
                        JOptionPane.showMessageDialog(StudentsFrame.this,
                                "Необходимо выделить студента в списке");
                    }
                }
            }
        };
        t.start();
    }

    // метод для удаления студента
    private void deleteStudent() {
        Thread t = new Thread() {
            public void run() {
                if (stdList != null) {
                    StudentTableModel stm = (StudentTableModel) stdList.getModel();
                    // Проверяем - выделен ли хоть какой-нибудь студент
                    if (stdList.getSelectedRow() >= 0) {
                        if (JOptionPane.showConfirmDialog(StudentsFrame.this,
                                "Вы хотите удалить студента?", "Удаление студента",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            // Вот где нам пригодился метод getStudent(int rowIndex)
                            Student s = stm.getStudent(stdList.getSelectedRow());
                            try {
                                ms.deleteStudent(s);
                                reloadStudents();
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
                            }
                        }
                    } // Если студент не выделен - сообщаем пользователю, что это необходимо
                    else {
                        JOptionPane.showMessageDialog(StudentsFrame.this, "Необходимо выделить студента в списке");
                    }
                }
            }
        };
        t.start();
    }

    public static void main(String args[]) {
        // Запуск формы лучше производить в специальном треде
        // event-dispatching thread - EDT
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Мы сразу отменим продолжение работы, если не сможем получить
                    // коннект к базе данных
                    StudentsFrame sf = new StudentsFrame();
                    sf.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    sf.setVisible(true);
                    // Перегрузка списка нам нужна в этом треде
                    // т.к. при создании формы списка студентов еще нет
                    sf.reloadStudents();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}

// Наш внутренний класс - переопределенная панель.
class GroupPanel extends JPanel {

    public Dimension getPreferredSize() {
        return new Dimension(260, 0);
    }
}