package files;

import javafx.application.Application;
import javafx.collections.*;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Initialize DB (creates tables if not exists)
        DatabaseManager.initDB();

        ObservableList<Institute> institutes;

        if (DatabaseManager.isEmpty()) {
            // ── First run: seed sample data ────────────────────────────────
            institutes = FXCollections.observableArrayList();

            Institute pieas = new Institute("PIEAS", 0, "Admin", "admin@pieas", "1234");
            pieas.getEducationDivision().setCreditHourFee(120);
            pieas.getHostelDivision().setHostelFee(2500);
            pieas.getTransportDivision().setFeePerSeat(800);

            // Extra administrator
            pieas.addAdministrator(new Administrator("Vice Admin", "vadmin@pieas", "5678", pieas));

            // Teachers
            Teacher drAli   = new Teacher("Dr. Ali Khan",   "ali@pieas",   "pass123");
            Teacher drSara  = new Teacher("Dr. Sara Malik",  "sara@pieas",  "pass456");
            Teacher drImran = new Teacher("Dr. Imran Baig",  "imran@pieas", "pass789");
            pieas.getEducationDivision().addTeacher(drAli);
            pieas.getEducationDivision().addTeacher(drSara);
            pieas.getEducationDivision().addTeacher(drImran);

            // Courses — all assigned
            Course math    = new Course("Mathematics",     3);
            Course physics = new Course("Physics",         3);
            Course cs101   = new Course("CS101",           2);
            Course linAlg  = new Course("Linear Algebra",  3);
            Course thermo  = new Course("Thermodynamics",  2);
            math.assignTeacher(drAli);
            physics.assignTeacher(drSara);
            cs101.assignTeacher(drImran);
            linAlg.assignTeacher(drAli);
            thermo.assignTeacher(drSara);
            pieas.getEducationDivision().addCourse(math);
            pieas.getEducationDivision().addCourse(physics);
            pieas.getEducationDivision().addCourse(cs101);
            pieas.getEducationDivision().addCourse(linAlg);
            pieas.getEducationDivision().addCourse(thermo);

            // Hostels
            Hostel blockA = new Hostel("Block A (Boys)",  10, 2, 2500);
            Hostel blockB = new Hostel("Block B (Girls)",  8, 3, 2500);
            pieas.getHostelDivision().addHostel(blockA);
            pieas.getHostelDivision().addHostel(blockB);

            // Transport routes
            Route r1 = new Route("Hamid",  "AB-123", 20, "Main Gate -> City Center -> Railway Station");
            Route r2 = new Route("Khalid", "CD-456", 15, "Main Gate -> Model Town -> Johar Town");
            Route r3 = new Route("Arshad", "EF-789", 25, "Main Gate -> Gulberg -> DHA Phase 5");
            pieas.getTransportDivision().addRoute(r1);
            pieas.getTransportDivision().addRoute(r2);
            pieas.getTransportDivision().addRoute(r3);

            // Students
            Student ahmed  = new Student("Ahmed Raza",    "ahmed1@pieas",  "abc");
            Student fatima = new Student("Fatima Noor",   "fatima2@pieas", "abc");
            Student bilal  = new Student("Bilal Hussain", "bilal3@pieas",  "abc");
            Student sana   = new Student("Sana Zafar",    "sana4@pieas",   "abc");
            ahmed.setInstitute(pieas);
            fatima.setInstitute(pieas);
            bilal.setInstitute(pieas);
            sana.setInstitute(pieas);

            ahmed.enrollCourse(math);    ahmed.enrollCourse(cs101);
            fatima.enrollCourse(physics); fatima.enrollCourse(thermo);
            bilal.enrollCourse(math);    bilal.enrollCourse(linAlg);
            sana.enrollCourse(cs101);    sana.enrollCourse(physics);

            // Hostel for ahmed and fatima & sana
            blockA.addStudent(ahmed);
            ahmed.restoreHostelStatus("Room 1, Block A (Boys)");
            ahmed.addFeeEntity(new FeeEntity("Hostel Fee", 2500));
            blockB.addStudent(fatima);
            fatima.restoreHostelStatus("Room 1, Block B (Girls)");
            fatima.addFeeEntity(new FeeEntity("Hostel Fee", 2500));
            blockB.addStudent(sana);
            sana.restoreHostelStatus("Room 1, Block B (Girls)");
            sana.addFeeEntity(new FeeEntity("Hostel Fee", 2500));

            // Transport for ahmed and bilal
            r1.addStudent(ahmed);
            ahmed.restoreTransportStatus("Seat 1 in bus AB-123");
            ahmed.addFeeEntity(new FeeEntity("Transport Fee", 800));
            r2.addStudent(bilal);
            bilal.restoreTransportStatus("Seat 1 in bus CD-456");
            bilal.addFeeEntity(new FeeEntity("Transport Fee", 800));

            pieas.getEducationDivision().addStudent(ahmed);
            pieas.getEducationDivision().addStudent(fatima);
            pieas.getEducationDivision().addStudent(bilal);
            pieas.getEducationDivision().addStudent(sana);

            // Applicants
            Applicant zara   = new Applicant("Zara Khan",    "zara123",  920, 1100);
            Applicant usman  = new Applicant("Usman Tariq",  "usman456", 850, 1100);
            Applicant hira   = new Applicant("Hira Rashid",  "hira789",  790, 1100);
            pieas.getEducationDivision().addApplicant(zara);
            pieas.getEducationDivision().addApplicant(usman);
            pieas.getEducationDivision().addApplicant(hira);
            EducationDivision.setMeritPositions(pieas.getEducationDivision().getApplicants());

            institutes.add(pieas);

            // Save seeded data
            DatabaseManager.saveAll(institutes);

        } else {
            // ── Subsequent runs: load from DB ──────────────────────────────
            institutes = DatabaseManager.loadAll();
        }

        sceneManager sm = new sceneManager(institutes);
        stage.setScene(sm.getTheScene());
        stage.setTitle("Institute Management System");
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}