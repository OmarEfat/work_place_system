import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class GUI {
    static final int FROM_LEFT = 40;
    static JFrame  login_frame;
    static Database db = new Database();
    static JTable j;
    public GUI() {
    /*
    Main page:
    This page will contain login interface that will the employee to enter his user name or password
     */
        // Creating the frames
        login_frame = new JFrame("Login page");
        JPanel login_panel = new JPanel();
        login_frame.setSize(280,230);
        login_panel.setSize(280,230);
        login_frame.add(login_panel);
        login_frame.setVisible(true);
        login_frame.setLayout(null);
        login_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login_panel.setLayout(null);
        login_frame.setLocationRelativeTo(null);
        //login_frame.setResizable(false);

        // Creating the text fields and buttons
        JLabel welcome = new JLabel("Welcome");
        welcome.setBounds(80,0,100,50);
        welcome.setFont((new Font("Arial", Font.PLAIN, 20)));


        JLabel user_name = new JLabel("Usercode:");
        user_name.setBounds(20,50,100,20);

        JLabel password = new JLabel("Password:");
        password.setBounds(20,80,100,20);

        JTextField user_field = new JTextField();
        JPasswordField pass_field = new JPasswordField();
        user_field.setBounds(90,53,100,21);
        pass_field.setBounds(90,83,100,21);

        JButton login = new JButton("Login");
        login.setBounds(90,120,100,20);
/*
        JButton newE = new JButton("New Employee");
        newE.setBounds(75,160,130,21);

 */

        login_panel.add(user_name);
        login_panel.add(password);
        login_panel.add(user_field);
        login_panel.add(pass_field);
        login_panel.add(login);
        login_panel.add(welcome);
        login_panel.revalidate();
        login_panel.repaint();

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user_name = user_field.getText();
                String password = pass_field.getText();
                if(db.found(user_name,password))
                {
                    if(db.type.equals("IT"))
                    {
                        IT_frame(user_name);
                    }
                    else if(db.type.equals("Employee"))
                    {
                        employee_frame(user_name);
                    }
                    else if(db.type.equals("Supervisor"))
                    {
                        supervisor_frame();
                    }
                }
                else
                {
                        JOptionPane.showMessageDialog(null, "The username or password are incorrect. Please try again or contact IT department", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });



    }

        public static void IT_frame(String user_name)
    {
        JFrame IT_frame = new JFrame();
        IT_frame.setSize(400,400);
        login_frame.setVisible(false);
        IT_frame.setVisible(true);
        IT_frame.setLocationRelativeTo(null);
        IT_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        IT_frame.setResizable(false);
        JLabel welcome_msg = new JLabel("Welcome to IT Portal");
        welcome_msg.setFont(new Font("Serif", Font.BOLD, 20));
        Dimension size = welcome_msg.getPreferredSize();
        welcome_msg.setBounds(200-size.width/2,size.height,size.width,size.height);
        JLabel list_msg = new JLabel("Here is the list of all employee:");
        list_msg.setBounds(5,70,200,30);
        list_msg.setFont(new Font("Serif",Font.BOLD, 12));
        PrintTable(db.employee_table(), IT_frame);
        JButton add_new = new JButton("Add Employee");
        add_new.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newE_frame();
            }
        });
        add_new.setBounds(220,200,150,50);
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IT_frame.setVisible(false);
                IT_frame(user_name);
            }
        });
        refresh.setBounds(220,100,150,50);
        JButton delete_E = new JButton("Delete Employee");
        delete_E.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteE_frame();
            }
        });
        delete_E.setBounds(220,300,150,50);
        IT_frame.add(welcome_msg);
        IT_frame.add(list_msg);
        IT_frame.add(add_new);
        IT_frame.add(refresh);
        IT_frame.add(delete_E);
        IT_frame.setLayout(null);



    }

    public static void PrintTable(ArrayList<String[]> table , JFrame f)
    {
        String[] columnNames  = table.get(0);
        String[][] data = new String[table.size()-1][];
        int data_counter = 0 ;
        for(int i = 1 ; i<table.size();i++)
        {
            data[data_counter]=table.get(i);
            data_counter++;
        }

        j = new JTable(data,columnNames);
        j.setBounds(0, 100, 200, 400);
        JScrollPane sp = new JScrollPane(j);
        j.setDefaultEditor(Object.class, null);
        f.add(j);

    }

    public static void newE_frame()
    {
        JFrame newE = new JFrame();
        newE.setSize(200,350);
        newE.setLayout(null);
        newE.setVisible(true);
        newE.setLocationRelativeTo(null);
        newE.setResizable(false);

        JLabel user_name = new JLabel("username:");
        JLabel password = new JLabel("password:");
        JLabel Name = new JLabel("Name");
        JLabel type = new JLabel("type:");
        JTextField name_text = new JTextField();
        JTextField pass_text = new JTextField();
        JTextField Name_text = new JTextField();
        String[]types = {"Employee","Supervisor","IT"};
        JComboBox type_combo = new JComboBox(types);
        JButton add_button = new JButton("Add");

        user_name.setBounds(0,0,100,30);
        name_text.setBounds(0,30,100,20);

        password.setBounds(0,50,100,30);
        pass_text.setBounds(0,80,100,20);

        type.setBounds(0,100,100,30);
        type_combo.setBounds(0,130,100,20);

        Name.setBounds(0,165,100,30);
        Name_text.setBounds(0,195,100,20);

        add_button.setBounds(0,230,100,80);
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.addEmployee(Integer.valueOf(name_text.getText()),pass_text.getText(),(String)type_combo.getSelectedItem(),Name_text.getText());
                newE.setVisible(false);
            }
        });

        newE.add(user_name);
        newE.add(name_text);
        newE.add(password);
        newE.add(pass_text);
        newE.add(type);
        newE.add(type_combo);
        newE.add(add_button);
        newE.add(Name);
        newE.add(Name_text);




    }

    public static void deleteE_frame()
    {
        JFrame newE = new JFrame("Delete");
        newE.setSize(150,200);
        newE.setLayout(null);
        newE.setVisible(true);
        newE.setLocationRelativeTo(null);
        newE.setResizable(false);

        JLabel user_name = new JLabel("username:");
        JTextField name_text = new JTextField();
        user_name.setBounds(0,0,100,30);
        name_text.setBounds(0,30,100,20);

        JButton delete_button = new JButton("Delete");
        delete_button.setBounds(0,50,100,100);
        delete_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.deleteEmployee(Integer.valueOf(name_text.getText()));
                newE.setVisible(false);
            }
        });
        newE.add(user_name);
        newE.add(name_text);
        newE.add(delete_button);

    }

    public static void supervisor_frame()
    {
            JFrame sup_frame = new JFrame("Supervisor Frame");
            sup_frame.setSize(300,300);
            sup_frame.setVisible(true);
            login_frame.setVisible(false);
            sup_frame.setLocationRelativeTo(null);
            JLabel welcome_msg = new JLabel("Welcome to Supervisor Portal");
            welcome_msg.setFont(new Font("Serif", Font.BOLD, 15));
            welcome_msg.setBounds(30,0,250,20);

            String[] employee_list = new String[db.employee_list().size()];
             employee_list = db.employee_list().toArray(employee_list);
            JComboBox employee = new JComboBox(employee_list);
            JLabel emp_label = new JLabel("Employee list:");
            emp_label.setBounds(10,30,100,30);
            employee.setBounds(10,80,200,30);

            JLabel task = new JLabel("Task:");
            task.setBounds(10,130,100,30);

            JTextField enter_task = new JTextField();
            enter_task.setBounds(10,170,200,30);

            JButton button = new JButton("Assign");
            button.setBounds(0,220,100,30);

            JButton completed_tasks = new JButton("Completed tasks");
            completed_tasks.setBounds(110,220,150,30);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    db.add_task((String)employee.getSelectedItem(),enter_task.getText());
                    JOptionPane.showMessageDialog(null, "Task added successfully");
                }
            });

            completed_tasks.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PrintTable(db.completed_tasksList(),completed_frame());
                    j.setBounds(0, 100, 400, 400);
                }
            });

            sup_frame.add(welcome_msg);
            sup_frame.add(completed_tasks);
            sup_frame.add(emp_label);
            sup_frame.add(employee);
            sup_frame.add(task);
            sup_frame.add(enter_task);
            sup_frame.add(button);
            sup_frame.setLayout(null);

    }

    public static void employee_frame(String username)
    {
        JFrame employee_frame = new JFrame();
        employee_frame.setSize(400,400);
        employee_frame.setVisible(true);
        employee_frame.setLayout(null);
        employee_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        employee_frame.setLocationRelativeTo(null);
        employee_frame.setResizable(false);
        JPanel list_panel = new JPanel();
        JLabel your_tasks = new JLabel("Welcome to Employee portal");
        your_tasks.setFont(new Font("Serif", Font.BOLD, 20));
        your_tasks.setBounds(70,0,300,50);
        list_panel.setBounds(0,100,400,100);
        JLabel inst = new JLabel("Here is the list of tasks:");
        inst.setBounds(100,70,400,30);
        JButton completed = new JButton("Completed");
        completed.setBounds(0,300,250,50);
        String[] tasks = new String[db.employee_tasks(username).size()];
        tasks = db.employee_tasks(username).toArray(tasks);

        JList list = new JList(tasks);
        list_panel.setBackground(Color.gray);
        list_panel.add(list);
        JButton refresh = new JButton("Refresh");
        refresh.setBounds(280,300,100,50);
        employee_frame.add(inst);
        employee_frame.add(your_tasks);
        employee_frame.add(list_panel);
        employee_frame.add(completed);
        employee_frame.add(refresh);

        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employee_frame.setVisible(false);
                employee_frame(username);
            }
        });

        completed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.completed_task(username,(String)list.getSelectedValue());
                JOptionPane.showMessageDialog(null, "Supervisor notified with completed task");
                employee_frame.repaint();
                employee_frame.repaint();
            }
        });


    }

    public static JFrame completed_frame()
    {
        JFrame completed_frame = new JFrame();
        completed_frame.setResizable(false);
        completed_frame.setLayout(null);
        JButton empty = new JButton("Empty list");
        empty.setBounds(250,70,100,20);
        JLabel welcome_msg = new JLabel("The list of Completed tasks:");
        completed_frame.setSize(400,400);
        welcome_msg.setFont((new Font("Arial", Font.BOLD, 20)));
        welcome_msg.setBounds(0,0,300,100);
        completed_frame.add(welcome_msg);
        completed_frame.add(empty);
        completed_frame.setVisible(true);
        empty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            db.delete_completed();
            completed_frame.setVisible(false);
            completed_frame();
            }
        });
        return completed_frame;
    }

}
