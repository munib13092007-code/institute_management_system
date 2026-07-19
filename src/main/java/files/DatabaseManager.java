package files;

import java.sql.*;
import javafx.collections.*;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:ims.db";

    public static void initDB() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement st = conn.createStatement()) {

            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS institutes (" +
                            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "  name TEXT NOT NULL," +
                            "  credit_hr_fee REAL DEFAULT 0," +
                            "  hostel_fee REAL DEFAULT 0," +
                            "  transport_fee REAL DEFAULT 0" +
                            ")");
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS administrators (" +
                            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "  name TEXT," +
                            "  login_id TEXT," +
                            "  password TEXT," +
                            "  institute_id INTEGER" +
                            ")");
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS teachers (" +
                            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "  name TEXT," +
                            "  login_id TEXT," +
                            "  password TEXT," +
                            "  institute_id INTEGER" +
                            ")");
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS courses (" +
                            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "  name TEXT," +
                            "  credit_hours REAL," +
                            "  teacher_login_id TEXT," +
                            "  institute_id INTEGER" +
                            ")");
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS students (" +
                            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "  name TEXT," +
                            "  login_id TEXT," +
                            "  password TEXT," +
                            "  transport_status INTEGER DEFAULT 0," +
                            "  seat_number TEXT," +
                            "  hostel_status INTEGER DEFAULT 0," +
                            "  room_number TEXT," +
                            "  institute_id INTEGER" +
                            ")");
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS student_courses (" +
                            "  student_login_id TEXT," +
                            "  course_name TEXT," +
                            "  institute_id INTEGER" +
                            ")");
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS fee_entities (" +
                            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "  student_login_id TEXT," +
                            "  title TEXT," +
                            "  amount REAL," +
                            "  institute_id INTEGER" +
                            ")");
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS applicants (" +
                            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "  name TEXT," +
                            "  password TEXT," +
                            "  obtained_marks INTEGER," +
                            "  total_marks INTEGER," +
                            "  merit_position INTEGER," +
                            "  admitted INTEGER DEFAULT 0," +
                            "  alloted_id TEXT," +
                            "  institute_id INTEGER" +
                            ")");
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS hostels (" +
                            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "  name TEXT," +
                            "  rooms INTEGER," +
                            "  occupancy_per_room INTEGER," +
                            "  fee REAL," +
                            "  institute_id INTEGER" +
                            ")");
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS routes (" +
                            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "  driver TEXT," +
                            "  bus_number TEXT," +
                            "  seats INTEGER," +
                            "  stops TEXT," +
                            "  institute_id INTEGER" +
                            ")");

        } catch (SQLException e) {
            System.err.println("DB init error: " + e.getMessage());
        }
    }

    // ─── Check if DB is empty ─────────────────────────────────────────────────

    public static boolean isEmpty() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT COUNT(*) AS cnt FROM institutes")) {
            if (rs.next())
                return rs.getInt("cnt") == 0;
        } catch (SQLException e) {
            System.err.println("DB isEmpty check error: " + e.getMessage());
        }
        return true;
    }

    // ─── Save All ─────────────────────────────────────────────────────────────

    public static void saveAll(ObservableList<Institute> institutes) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            conn.setAutoCommit(false);

            // Clear all tables
            try (Statement st = conn.createStatement()) {
                st.executeUpdate("DELETE FROM fee_entities");
                st.executeUpdate("DELETE FROM student_courses");
                st.executeUpdate("DELETE FROM students");
                st.executeUpdate("DELETE FROM courses");
                st.executeUpdate("DELETE FROM teachers");
                st.executeUpdate("DELETE FROM applicants");
                st.executeUpdate("DELETE FROM administrators");
                st.executeUpdate("DELETE FROM hostels");
                st.executeUpdate("DELETE FROM routes");
                st.executeUpdate("DELETE FROM institutes");
            }

            for (Institute inst : institutes) {
                // Insert institute
                int instId;
                try (PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO institutes(name,credit_hr_fee,hostel_fee,transport_fee) VALUES(?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, inst.getName());
                    ps.setDouble(2, inst.getEducationDivision().getCreditHourFee());
                    ps.setDouble(3, inst.getHostelDivision().getHostelFee());
                    ps.setDouble(4, inst.getTransportDivision().getFeePerSeat());
                    ps.executeUpdate();
                    ResultSet keys = ps.getGeneratedKeys();
                    keys.next();
                    instId = keys.getInt(1);
                }

                // Administrators
                for (Administrator a : inst.getAdministrators()) {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO administrators(name,login_id,password,institute_id) VALUES(?,?,?,?)")) {
                        ps.setString(1, a.getName());
                        ps.setString(2, a.getID());
                        ps.setString(3, a.getRawPassword());
                        ps.setInt(4, instId);
                        ps.executeUpdate();
                    }
                }

                // Teachers
                for (Teacher t : inst.getEducationDivision().getTeachers()) {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO teachers(name,login_id,password,institute_id) VALUES(?,?,?,?)")) {
                        ps.setString(1, t.getName());
                        ps.setString(2, t.getID());
                        ps.setString(3, t.getRawPassword());
                        ps.setInt(4, instId);
                        ps.executeUpdate();
                    }
                }

                // Courses
                for (Course c : inst.getEducationDivision().getCourses()) {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO courses(name,credit_hours,teacher_login_id,institute_id) VALUES(?,?,?,?)")) {
                        ps.setString(1, c.getName());
                        ps.setDouble(2, c.getCreditHours());
                        String tId = c.getTeacherID();
                        ps.setString(3, tId == null ? "" : tId);
                        ps.setInt(4, instId);
                        ps.executeUpdate();
                    }
                }

                // Students
                for (Student s : inst.getEducationDivision().getStudents()) {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO students(name,login_id,password,transport_status,seat_number," +
                                    "hostel_status,room_number,institute_id) VALUES(?,?,?,?,?,?,?,?)")) {
                        ps.setString(1, s.getName());
                        ps.setString(2, s.getID());
                        ps.setString(3, s.getRawPassword());
                        ps.setInt(4, s.getTranportValue() ? 1 : 0);
                        ps.setString(5, s.getSeatNumber());
                        ps.setInt(6, s.getHostelValue() ? 1 : 0);
                        ps.setString(7, s.getRoomNumber());
                        ps.setInt(8, instId);
                        ps.executeUpdate();
                    }
                    // Student-course junctions
                    for (Course c : s.getEnrolledCourses()) {
                        try (PreparedStatement ps = conn.prepareStatement(
                                "INSERT INTO student_courses(student_login_id,course_name,institute_id) VALUES(?,?,?)")) {
                            ps.setString(1, s.getID());
                            ps.setString(2, c.getName());
                            ps.setInt(3, instId);
                            ps.executeUpdate();
                        }
                    }
                    // Fee entities
                    for (FeeEntity fe : s.getFeeDetails()) {
                        try (PreparedStatement ps = conn.prepareStatement(
                                "INSERT INTO fee_entities(student_login_id,title,amount,institute_id) VALUES(?,?,?,?)")) {
                            ps.setString(1, s.getID());
                            ps.setString(2, fe.getTitle());
                            ps.setDouble(3, fe.getAmount());
                            ps.setInt(4, instId);
                            ps.executeUpdate();
                        }
                    }
                }

                // Applicants
                for (Applicant ap : inst.getEducationDivision().getApplicants()) {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO applicants(name,password,obtained_marks,total_marks,merit_position," +
                                    "admitted,alloted_id,institute_id) VALUES(?,?,?,?,?,?,?,?)")) {
                        ps.setString(1, ap.getName());
                        ps.setString(2, ap.getPassword());
                        ps.setInt(3, ap.getObtainedMarks());
                        ps.setInt(4, ap.getTotalMarks());
                        ps.setInt(5, ap.getMeritPosition());
                        ps.setInt(6, ap.getAdmissionStatus() ? 1 : 0);
                        ps.setString(7, ap.getAllotedID() == null ? "" : ap.getAllotedID());
                        ps.setInt(8, instId);
                        ps.executeUpdate();
                    }
                }

                // Hostels
                for (Hostel h : inst.getHostelDivision().getHostels()) {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO hostels(name,rooms,occupancy_per_room,fee,institute_id) VALUES(?,?,?,?,?)")) {
                        ps.setString(1, h.getName());
                        ps.setInt(2, h.getNumberOfRooms());
                        ps.setInt(3, h.getOccupancyPerRoom());
                        ps.setDouble(4, h.getFee());
                        ps.setInt(5, instId);
                        ps.executeUpdate();
                    }
                }

                // Routes
                for (Route r : inst.getTransportDivision().getRoutes()) {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO routes(driver,bus_number,seats,stops,institute_id) VALUES(?,?,?,?,?)")) {
                        ps.setString(1, r.getDriver());
                        ps.setString(2, r.getBusNumber());
                        ps.setInt(3, r.getTotalSeats());
                        ps.setString(4, r.getStopsString());
                        ps.setInt(5, instId);
                        ps.executeUpdate();
                    }
                }
            }

            conn.commit();
        } catch (SQLException e) {
            System.err.println("DB save error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ─── Load All ─────────────────────────────────────────────────────────────

    public static ObservableList<Institute> loadAll() {
        ObservableList<Institute> institutes = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(DB_URL)) {

            // Load institutes
            try (Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM institutes")) {

                while (rs.next()) {
                    int instId = rs.getInt("id");
                    String instName = rs.getString("name");
                    double crFee = rs.getDouble("credit_hr_fee");
                    double hFee = rs.getDouble("hostel_fee");
                    double tFee = rs.getDouble("transport_fee");

                    // Load first admin to bootstrap Institute constructor
                    String adminName = "Admin", adminId = "admin", adminPwd = "admin";
                    try (PreparedStatement aps = conn.prepareStatement(
                            "SELECT * FROM administrators WHERE institute_id=? LIMIT 1")) {
                        aps.setInt(1, instId);
                        ResultSet ar = aps.executeQuery();
                        if (ar.next()) {
                            adminName = ar.getString("name");
                            adminId = ar.getString("login_id");
                            adminPwd = ar.getString("password");
                        }
                    }

                    Institute inst = new Institute(instName, institutes.size(), adminName, adminId, adminPwd);
                    inst.getEducationDivision().setCreditHourFee(crFee);
                    inst.getHostelDivision().setHostelFee(hFee);
                    inst.getTransportDivision().setFeePerSeat(tFee);

                    // Load remaining admins
                    try (PreparedStatement aps = conn.prepareStatement(
                            // "SELECT * FROM administrators WHERE institute_id=? OFFSET 1")) {
                            "SELECT * FROM administrators WHERE institute_id=? ")) {
                        aps.setInt(1, instId);
                        ResultSet ar = aps.executeQuery();
                        while (ar.next()) {
                            inst.addAdministrator(new Administrator(
                                    ar.getString("name"), ar.getString("login_id"),
                                    ar.getString("password"), inst));
                        }
                    }

                    // Load teachers
                    try (PreparedStatement tps = conn.prepareStatement(
                            "SELECT * FROM teachers WHERE institute_id=?")) {
                        tps.setInt(1, instId);
                        ResultSet tr = tps.executeQuery();
                        while (tr.next()) {
                            inst.getEducationDivision().addTeacher(new Teacher(
                                    tr.getString("name"), tr.getString("login_id"),
                                    tr.getString("password")));
                        }
                    }

                    // Load courses (teacher assignment done after)
                    try (PreparedStatement cps = conn.prepareStatement(
                            "SELECT * FROM courses WHERE institute_id=?")) {
                        cps.setInt(1, instId);
                        ResultSet cr = cps.executeQuery();
                        while (cr.next()) {
                            Course course = new Course(cr.getString("name"), cr.getDouble("credit_hours"));
                            String tLoginId = cr.getString("teacher_login_id");
                            if (tLoginId != null && !tLoginId.isEmpty()) {
                                for (Teacher t : inst.getEducationDivision().getTeachers()) {
                                    if (t.getID().equals(tLoginId)) {
                                        course.assignTeacher(t);
                                        break;
                                    }
                                }
                            }
                            inst.getEducationDivision().addCourse(course);
                        }
                    }

                    // Load students
                    try (PreparedStatement sps = conn.prepareStatement(
                            "SELECT * FROM students WHERE institute_id=?")) {
                        sps.setInt(1, instId);
                        ResultSet sr = sps.executeQuery();
                        while (sr.next()) {
                            Student stu = new Student(
                                    sr.getString("name"), sr.getString("login_id"),
                                    sr.getString("password"));
                            stu.setInstitute(inst);

                            // Restore transport/hostel status fields
                            if (sr.getInt("transport_status") == 1) {
                                stu.restoreTransportStatus(sr.getString("seat_number"));
                            }
                            if (sr.getInt("hostel_status") == 1) {
                                stu.restoreHostelStatus(sr.getString("room_number"));
                            }

                            // Load enrolled courses
                            try (PreparedStatement scps = conn.prepareStatement(
                                    "SELECT course_name FROM student_courses WHERE student_login_id=? AND institute_id=?")) {
                                scps.setString(1, stu.getID());
                                scps.setInt(2, instId);
                                ResultSet scr = scps.executeQuery();
                                while (scr.next()) {
                                    String cName = scr.getString("course_name");
                                    for (Course c : inst.getEducationDivision().getCourses()) {
                                        if (c.getName().equals(cName)) {
                                            stu.enrollCourseRestore(c);
                                            break;
                                        }
                                    }
                                }
                            }

                            // Load fee entities
                            try (PreparedStatement fps = conn.prepareStatement(
                                    "SELECT * FROM fee_entities WHERE student_login_id=? AND institute_id=?")) {
                                fps.setString(1, stu.getID());
                                fps.setInt(2, instId);
                                ResultSet fr = fps.executeQuery();
                                while (fr.next()) {
                                    stu.addFeeEntity(new FeeEntity(
                                            fr.getString("title"), fr.getDouble("amount")));
                                }
                            }

                            inst.getEducationDivision().addStudent(stu);
                        }
                    }

                    // Load applicants
                    try (PreparedStatement aps2 = conn.prepareStatement(
                            "SELECT * FROM applicants WHERE institute_id=?")) {
                        aps2.setInt(1, instId);
                        ResultSet ar2 = aps2.executeQuery();
                        while (ar2.next()) {
                            Applicant ap = new Applicant(
                                    ar2.getString("name"), ar2.getString("password"),
                                    ar2.getInt("obtained_marks"), ar2.getInt("total_marks"));
                            ap.setMeritPosition(ar2.getInt("merit_position"));
                            if (ar2.getInt("admitted") == 1) {
                                ap.admit(ar2.getString("alloted_id"));
                            }
                            inst.getEducationDivision().addApplicant(ap);
                        }
                    }

                    // Load hostels
                    try (PreparedStatement hps = conn.prepareStatement(
                            "SELECT * FROM hostels WHERE institute_id=?")) {
                        hps.setInt(1, instId);
                        ResultSet hr = hps.executeQuery();
                        while (hr.next()) {
                            inst.getHostelDivision().addHostel(new Hostel(
                                    hr.getString("name"), hr.getInt("rooms"),
                                    hr.getInt("occupancy_per_room"), hr.getDouble("fee")));
                        }
                    }

                    // Load routes
                    try (PreparedStatement rps = conn.prepareStatement(
                            "SELECT * FROM routes WHERE institute_id=?")) {
                        rps.setInt(1, instId);
                        ResultSet rr = rps.executeQuery();
                        while (rr.next()) {
                            inst.getTransportDivision().addRoute(new Route(
                                    rr.getString("driver"), rr.getString("bus_number"),
                                    rr.getInt("seats"), rr.getString("stops")));
                        }
                    }

                    institutes.add(inst);
                }
            }

        } catch (SQLException e) {
            System.err.println("DB load error: " + e.getMessage());
            e.printStackTrace();
        }

        return institutes;
    }
}
