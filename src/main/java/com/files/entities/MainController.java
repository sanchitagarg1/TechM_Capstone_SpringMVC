package com.files.entities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
//@RequestMapping("/")
public class MainController {

    @Autowired
    private Datadao datadao;

    @Autowired
    private BookDataDao bookDataDao;

    @GetMapping("/")
    public String index(Model model) {
        List<TourPackage> tourPackages = getTourPackagesFromDatabase();
        model.addAttribute("tourPackages", tourPackages);
        return "Main";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "Register"; 
    }
    
    @PostMapping("/otpVerify")
    public String showOtpVerifyPage() {
        return "OTPverify"; 
    }

    @PostMapping("/registerServlet")
    public String registerServlet(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") Long phone,
            @RequestParam("address") String address,
            @RequestParam("role") String role,
            @RequestParam("pass") String pass,
            @RequestParam("pass1") String pass1,
            @RequestParam("otp") String otp,
            @RequestParam("myotp") String myotp,
            Model model) {

        Data data = new Data();
        data.setName(name);
        data.setEmail(email);
        data.setPhone(phone);
        data.setAddress(address);
        data.setRole(role);

        if (myotp.equals(otp)) {
            model.addAttribute("otpMessage", "OTP verified!");

            if (pass.equals(pass1)) {
                data.setPassword(pass);
                int status = datadao.insertRecord(data);

                if (status == 1) {
                    model.addAttribute("message", "Updated Successfully! You can now login.");
                    return "Main";
                } else {
                    model.addAttribute("message", "Unable to Register. Please try again!");
                    return "Register";
                }
            } else {
                model.addAttribute("message", "Password and repassword didn't match.");
                return "Register";
            }
        } else {
            model.addAttribute("otpMessage", "Invalid OTP.");
            return "Register";
        }
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("inemail") String email, @RequestParam("inpassword") String password,
            HttpSession session, Model model) {
        Data user = datadao.checklogin(email, password);
        if (user != null) {
            session.setAttribute("name", user.getName());
            session.setAttribute("email", user.getEmail());
            if ("Admin".equals(user.getRole())) {
                List<BookData> bookRecords = getBookRecordsFromDatabase();
                List<Data> record = getRecordsFromDatabase();
                session.setAttribute("bookRecords", bookRecords);
                session.setAttribute("record", record);
                return "AdminProfile";
            } else {
                model.addAttribute("name", user.getName());
                model.addAttribute("email", user.getEmail());
                model.addAttribute("imageFileName", user.getImageFileName());
                return "Index";
            }
        } else {
            return "wrong";
        }
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam("email") String email, @RequestParam("pass") String pass,
            @RequestParam("pass1") String pass1, Model model) {
        Data user = datadao.searchUser(email);
        if (user != null) {
            sendOTP(email);
            model.addAttribute("otp", "OTP sent successfully. Check your email.");
            model.addAttribute("email", email);
            model.addAttribute("pass", pass);
            model.addAttribute("pass1", pass1);
            return "OTPverifyReset";
        } else {
            model.addAttribute("error", "User not found.");
            return "Main";
        }
    }

    @GetMapping("/update-profile")
    public String updateProfileForm(@RequestParam("email") String email, Model model) {
        Data user = datadao.searchUser(email);
        if (user != null) {
            model.addAttribute("user", user);
            return "UpdateUserByAdmin";
        } else {
            return "wrong";
        }
    }

    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute Data user, Model model) {
        int status = datadao.updaterecord(user);
        if (status == 1) {
            model.addAttribute("message", "Profile updated successfully!");
            return "AdminProfile";
        } else {
            model.addAttribute("error", "Failed to update profile.");
            return "UpdateUserByAdmin";
        }
    }

    @PostMapping("/send-mail")
    public String sendMail(@RequestParam("to") String to, @RequestParam("subject") String sub,
            @RequestParam("message") String msg, Model model) {
        sendEmail(to, sub, msg);
        model.addAttribute("message", "Mail sent successfully!");
        return "Main";
    }

    @PostMapping("/upload-profile-image")
    public String uploadProfileImage(@RequestParam("email") String email, @RequestParam("image") MultipartFile file,
            Model model) {
        if (!file.isEmpty()) {
            String imageFileName = file.getOriginalFilename();
            saveImageToFileSystem(email, file);
            int status = datadao.UploadImage(email, imageFileName);
            if (status == 1) {
                model.addAttribute("message", "Profile image uploaded successfully!");
            } else {
                model.addAttribute("error", "Failed to upload profile image.");
            }
        } else {
            model.addAttribute("error", "No file selected.");
        }
        return "AdminProfile";
    }

    @ModelAttribute("tourPackages")
    public List<TourPackage> getTourPackagesFromDatabase() {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String driver = "oracle.jdbc.driver.OracleDriver";
        String username = "user1";
        String password = "user1";
        List<TourPackage> tourPackages = new ArrayList<>();
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);
            String sql = "SELECT * FROM tourpackage";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TourPackage tourPackage = new TourPackage();
                tourPackage.setFromLocation(rs.getString("from_location"));
                tourPackage.setToLocation(rs.getString("to_location"));
                tourPackage.setTravelMode(rs.getString("travel_mode"));
                tourPackage.setPrice(rs.getString("price"));
                tourPackages.add(tourPackage);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return tourPackages;
    }

    private List<BookData> getBookRecordsFromDatabase() {
        int pages = 1;
        int total = 7;
        int offset = (pages > 1) ? (pages - 1) * total : 0;
        return bookDataDao.getRecords(offset, total);
    }

    private List<Data> getRecordsFromDatabase() {
        int pg = 1;
        int total1 = 8;
        if (pg == 1) {
            pg--;
        } else {
            pg = (pg - 1) * total1;
        }
        return datadao.getRecords(pg, total1);
    }

    private void saveImageToFileSystem(String email, MultipartFile file) {
        String uploadPath = "H:/java_eclipse/ToursAndTravels/src/main/webapp/ProfileImages/"
                + file.getOriginalFilename();
        try {
            FileOutputStream fos = new FileOutputStream(uploadPath);
            InputStream is = file.getInputStream();
            byte[] data = new byte[is.available()];
            is.read(data);
            fos.write(data);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendOTP(String email) {
        String from = "yatraxpress0@gmail.com";
        String sub = "OTP for Registration";
        String otp = generateOTP();
        String msg = "Use this Otp for Registration : " + otp;
        sendEmail(email, sub, msg);
    }

    private void sendEmail(String to, String sub, String msg) {
        String from = "yatraxpress0@gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("yatraxpress0@gmail.com", "teon fcgg ifht lnhr");
            }
        });
        MimeMessage mm = new MimeMessage(session);
        try {
            mm.setFrom(from);
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mm.setSubject(sub);
            mm.setText(msg);
            Transport.send(mm);
            System.out.println("Mail Sent");
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    private String generateOTP() {
        int len = 4;
        String numbers = "0123456789";
        Random random = new Random();
        char[] otp = new char[len];
        for (int i = 0; i < len; i++) {
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return String.valueOf(otp);
    }
}
