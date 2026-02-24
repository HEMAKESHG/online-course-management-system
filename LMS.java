import java.util.*;

class Student{
    int id;
    String name;
    List<Course> enrolledCourses = new ArrayList<>();
    public Student(int id,String name){
        this.id = id;
        this.name = name;
    } 
}

class Instructor{
    int id;
    String name;
    List<Course> courses = new ArrayList<>();
    public Instructor(int id, String name){
        this.id = id;
        this.name = name;
    }
}

class Course{
    int id;
    String title;
    Instructor instructor;
    List<Student> students = new ArrayList<>();
    public Course(int id,String title,Instructor instructor){
        this.id = id;
        this.title = title;
        this.instructor = instructor;
    }
}

public class LMS{
    static List<Student> students = new ArrayList<>();
    static List<Instructor> instructors = new ArrayList<>();
    static List<Course> courses = new ArrayList<>();

    static final String Admin_password = "admin123";
    static Scanner sc = new Scanner(System.in);

    public static void main(String args[]){
        while(true){
            System.out.println("=== Online Course Management System ===");
            System.out.println("1. Admin");
            System.out.println("2. Student");
            System.out.println("3. Instructor");
            System.out.println("4. Exit\n");
            int MainChoice = sc.nextInt();
            sc.nextLine();
            switch (MainChoice) {
                case 1 -> adminLogin();
                case 2 -> studentMenu();
                case 3 -> instructorMenu();
                case 4 -> {
                    System.out.println("Exiting!!...");
                    return;
                }
                default -> {
                    System.out.println("Invalid choice!");
                }
            }
        }
    }
    static void adminLogin(){
        System.out.print("Enter admin password: ");
        String pass = sc.nextLine();

        if(!pass.equals(Admin_password)){
            System.out.println("Incorrect password!");
            return;
        }

        while(true){
            System.out.println("=== Admin Dashboard ===\n");
            System.out.println("1. Add Instructor");
            System.out.println("2. View Students");
            System.out.println("3. View Courses");
            System.out.println("4. Logout\n");

            int adminChoice = sc.nextInt();
            switch(adminChoice){
                case 1 -> addInstructor();
                case 2 -> viewStudents();
                case 3 -> viewCourses();
                case 4 -> {
                    System.out.println("Logging out...");
                    return;
                }
            }
        }
    }

    static void addInstructor(){
        System.out.print("Instructor id :");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Name :");
        String name = sc.nextLine();
        instructors.add(new Instructor(id, name));
        System.out.println("Instructor added successfully!");
        System.out.println();
    }

    static void viewStudents(){
        for(Student s: students){
            System.out.println(s.id + " - "+ s.name);
        }
        System.out.println();
    }

    static void studentMenu(){
        System.out.println("1. Register");
        System.out.println("2. Login\n");
        int choice = sc.nextInt();

        if(choice==1){
            System.out.print("Student ID :");
            int id = sc.nextInt(); sc.nextLine();
            System.out.print("Name :");
            String name = sc.nextLine();

            students.add(new Student(id,name));
            System.out.println("Registered Successfully");
        }else{
            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            Student student = findStudent(id);

            if(student == null) {
                System.out.println("Student not found!");
                return;
            }

            while(true){
                System.out.println("=== Student Dashboard ===");
                System.out.println("1. View Courses");
                System.out.println("2. Enroll Courses");
                System.out.println("3. Logout\n");

                int ch = sc.nextInt(); sc.nextLine();

                switch(ch){
                    case 1 -> viewCourses();
                    case 2 -> enrollCourses(student);
                    case 3 -> {
                    System.out.println("Logging out...");
                    return;
                    }
                }
            }
        }
    }   
    static void viewCourses(){
        for(Course c: courses){
            System.out.println(c.id+" - "+c.title+" led by "+c.instructor.name);
        }
        System.out.println();
    }
    static void enrollCourses(Student student){
        if(courses.isEmpty()){
            System.out.println("No courses available!");
            return;
        }
        viewCourses();
        System.out.print("Enter course id to Enroll:");
        int cid = sc.nextInt(); sc.nextLine();

        for(Course c:courses){
            if(c.id == cid){
                c.students.add(student);
                student.enrolledCourses.add(c);
                System.out.println("Student enrolled successfully!");   
                return;
            }
        }
        System.out.println("Course not found!");
        System.out.println();
    }
    static Student findStudent(int id){
        for(Student s:students){
            if(s.id == id) return s;
        }
        return null;
    }
    static void instructorMenu(){
        System.out.print("Instructor ID:");
        int id = sc.nextInt();

        Instructor instructor = findInstructor(id);
        if(instructor==null){
            System.out.println("Invalid login");
            return;
        } 
        while(true){
            System.out.println("=== Instructor Dashboard ===");
            System.out.println("1. Create Course");
            System.out.println("2. View Course");
            System.out.println("3. Logout\n");

            int ch = sc.nextInt(); sc.nextLine();
            switch(ch){
                case 1 -> createCourse(instructor);
                case 2 -> viewInstructorCourses(instructor);
                case 3 -> {return;}
            }
        }
    }

    static Instructor findInstructor(int id){
        for(Instructor i: instructors){
            if(i.id == id) return i;
        }
        return null;
    }

    static void createCourse(Instructor instructor){
        System.out.print("Enter Course ID:");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Course Name:");
        String title = sc.nextLine();

        Course course = new Course(id, title, instructor);
        courses.add(course);
        instructor.courses.add(course);
        System.out.println("Course created successfully!");
        System.out.println();
    }

    static void viewInstructorCourses(Instructor instructor){
        for(Course c: instructor.courses){
            System.out.println(c.id+" - "+c.title);
        }
        System.out.println();
    }
}