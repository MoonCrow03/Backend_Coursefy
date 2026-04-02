package coursefy.utils;

import coursefy.domain.*;
import coursefy.persistence.*;
import edu.tecnocampus.labinternet.coursefy.domain.*;
import edu.tecnocampus.labinternet.coursefy.persistence.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LanguageRepository languageRepository;
    private final LessonRepository lessonRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ReviewRepository reviewRepository;

    public DataInitializer(CourseRepository courseRepository, UserRepository userRepository, CategoryRepository categoryRepository, LanguageRepository languageRepository, LessonRepository lessonRepository, EnrollmentRepository enrollmentRepository, ReviewRepository reviewRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.languageRepository = languageRepository;
        this.lessonRepository = lessonRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void run(String[] args){

        User user1 = new User();
        user1.setUsername("JuliaC");
        user1.setEmail("julia@gmail.com");
        user1.setName("Julia");
        user1.setFirst_surname("Castro");
        user1.setSecond_surname("Sevilla");
        user1.setGender(User.Gender.valueOf("Woman"));
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("RubenZ");
        user2.setEmail("ruben@gmail.com");
        user2.setName("Ruben");
        user2.setFirst_surname("Zuniga");
        user2.setSecond_surname("Arcon");
        user2.setGender(User.Gender.valueOf("Man"));
        userRepository.save(user2);

        Category category1 = new Category();
        category1.setName("Programming");
        category1.setDescription("Courses about programming");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("SQL");
        category2.setDescription("Courses about SQL");
        categoryRepository.save(category2);

        Category category3 = new Category();
        category3.setName("Videogames");
        category3.setDescription("Courses about videogames");
        categoryRepository.save(category3);

        Language language1 = new Language();
        language1.setName("Español");
        languageRepository.save(language1);

        Language language2 = new Language();
        language2.setName("English");
        languageRepository.save(language2);

        Course course1 = new Course();
        course1.setTitle("Java");
        course1.setDescription("This is a starting Java course");
        course1.setPrice(23);
        course1.setImageUrl("https://media.discordapp.net/attachments/895679820657229824/1185269892233056376/image.png?ex=658eff9b&is=657c8a9b&hm=3f660a573342c35796b66ff41df3512f3a50c7b08e3af5203b22a58c7fb9ae1e&=&format=webp&quality=lossless&width=1266&height=655");
        course1.setDatePublication(LocalDate.now());
        course1.setDateLastUpdate(LocalDate.now());
        course1.setCategory(category1);
        course1.setLanguage(language1);
        course1.setUser(user1);

        courseRepository.save(course1);

        Course course2 = new Course();
        course2.setTitle("SQL");
        course2.setDescription("This is a starting SQL course");
        course2.setPrice(30);
        course2.setImageUrl("https://media.discordapp.net/attachments/1081166366511480882/1082271888358785104/makeitmeme_SOiqx.jpeg?ex=651cab7d&is=651b59fd&hm=dfa8d251f0169f1fc0acd0e9b5d469eb8eff8aa98ff454f42357885329da5d63&=&width=1153&height=889");
        course2.setDatePublication(LocalDate.now().minusDays(1));
        course2.setDateLastUpdate(LocalDate.now().minusDays(1));
        course2.setCategory(category2);
        course2.setLanguage(language2);
        course2.setUser(user1);

        courseRepository.save(course2);

        Course course3 = new Course();
        course3.setTitle("C#");
        course3.setDescription("This is a starting C# course");
        course3.setPrice(53);
        course3.setImageUrl("https://media.discordapp.net/attachments/1081166366511480882/1082271786240057344/makeitmeme_1QgmA.jpeg?ex=651cab65&is=651b59e5&hm=3f62b29292438f8a4da20d49d836e912b4483de36ce804d50c6825e9dc99cab2&=&width=1011&height=889");
        course3.setDatePublication(LocalDate.now().minusDays(2));
        course3.setDateLastUpdate(LocalDate.now().minusDays(2));
        course3.setCategory(category1);
        course3.setLanguage(language2);
        course3.setUser(user1);

        courseRepository.save(course3);

        Course course4 = new Course();
        course4.setTitle("C++");
        course4.setDescription("This is a starting C++ course");
        course4.setPrice(53);
        course4.setImageUrl("https://media.discordapp.net/attachments/1081166366511480882/1082274624332644392/makeitmeme_7ZaDu.jpeg?ex=651cae0a&is=651b5c8a&hm=821b3c030ed2197e4bd6592aca49fd5084e95470d25b2ce53ca3863401ac4713&=&width=795&height=889");
        course4.setDatePublication(LocalDate.now().minusDays(3));
        course4.setDateLastUpdate(LocalDate.now().minusDays(3));
        course4.setCategory(category1);
        course4.setLanguage(language1);
        course4.setUser(user1);

        courseRepository.save(course4);

        Course course5 = new Course();
        course5.setTitle("Unity");
        course5.setDescription("This is a starting Unity course");
        course5.setPrice(53);
        course5.setImageUrl("https://media.discordapp.net/attachments/1081166366511480882/1082267323899056178/makeitmeme_5Gg6H.jpeg?ex=651ca73d&is=651b55bd&hm=8cec40b7896d7383f52408c573f01fc198bdf4c493f422086179e38cde674b81&=&width=1264&height=889");
        course5.setDatePublication(LocalDate.now().minusDays(4));
        course5.setDateLastUpdate(LocalDate.now().minusDays(4));
        course5.setCategory(category3);
        course5.setLanguage(language2);
        course5.setUser(user2);

        courseRepository.save(course5);

        Course course6 = new Course();
        course6.setTitle("BBDD");
        course6.setDescription("This is a SQL course");
        course6.setPrice(23);
        course6.setImageUrl("https://cdn.discordapp.com/attachments/1033772380855746566/1163793393411964988/IMG_20230925_134916_509.png?ex=6540de0f&is=652e690f&hm=b0cf4356c0a2825a3254f884e4484f8f6559c66458c081613b1554da9a6cdcf2&");
        course6.setDatePublication(LocalDate.now().minusDays(5));
        course6.setDateLastUpdate(LocalDate.now().minusDays(5));
        course6.setCategory(category2);
        course6.setLanguage(language1);
        course6.setUser(user2);

        courseRepository.save(course6);

        Course course7 = new Course();
        course7.setTitle("SQL2");
        course7.setDescription("This is an other SQL course");
        course7.setPrice(23);
        course7.setImageUrl("https://cdn.discordapp.com/attachments/1081166366511480882/1159021472833097749/makeitmeme_PrQa4.jpeg?ex=6538bc5c&is=6526475c&hm=f07c4daebe6a98a4e600096b229cabd903289e1084f3436ce45a48accdd2abf4&");
        course7.setDatePublication(LocalDate.now().minusDays(6));
        course7.setDateLastUpdate(LocalDate.now().minusDays(6));
        course7.setCategory(category2);
        course7.setLanguage(language2);
        course7.setUser(user2);

        courseRepository.save(course7);

        Course course8 = new Course();
        course8.setTitle("History of videogames");
        course8.setDescription("This is a history of videogames course");
        course8.setPrice(45);
        course8.setImageUrl("https://media.discordapp.net/attachments/1080952426925916242/1171006782630535218/image.png?ex=6589408d&is=6576cb8d&hm=90a4c28bd9fc327787319fade44e8b5f6d61e2536f92082b17ec4ec6b6a4a555&=&format=webp&quality=lossless&width=665&height=621");
        course8.setDatePublication(LocalDate.now().minusDays(9));
        course8.setDateLastUpdate(LocalDate.now().minusDays(9));
        course8.setCategory(category3);
        course8.setLanguage(language2);
        course8.setUser(user2);

        courseRepository.save(course8);

        Lesson lesson1 = new Lesson();
        lesson1.setName("Foundations of programming");
        lesson1.setDescription("This is a lesson about the foundations of programming");
        lesson1.setDuration(2);
        lesson1.setVideoUrl("https://youtu.be/dQw4w9WgXcQ");
        lessonRepository.save(lesson1);

        Lesson lesson2 = new Lesson();
        lesson2.setName("OOP");
        lesson2.setDescription("This is a lesson about the object oriented programming");
        lesson2.setDuration(4);
        lesson2.setVideoUrl("https://youtu.be/d-diB65scQU");
        course7.addLesson(lesson2);
        lessonRepository.save(lesson2);
        courseRepository.save(course7);

        Lesson lesson3 = new Lesson();
        lesson3.setName("Unity Basics");
        lesson3.setDescription("This is a lesson about Unity basics");
        lesson3.setDuration(3);
        lesson3.setVideoUrl("https://youtu.be/wDgQdr8ZkTw");
        course5.addLesson(lesson3);
        lessonRepository.save(lesson3);
        courseRepository.save(course5);

        Lesson lesson4 = new Lesson();
        lesson4.setName("Inventory system");
        lesson4.setDescription("In this lesson we are going to do an inventory system");
        lesson4.setDuration(3);
        lesson4.setVideoUrl("https://youtu.be/2ajD1GDbEzA");
        lessonRepository.save(lesson4);

        Lesson lesson5 = new Lesson();
        lesson5.setName("How to make a chair");
        lesson5.setDescription("In this lesson we are going to learn how to make a chair");
        lesson5.setDuration(3);
        lesson5.setVideoUrl("https://www.youtube.com/watch?v=twpFviS9z6Y");
        course7.addLesson(lesson5);
        course7.updateAvailability();
        lessonRepository.save(lesson5);
        courseRepository.save(course7);

        Lesson lesson6 = new Lesson();
        lesson6.setName("C++ Basics");
        lesson6.setDescription("In this lesson we are going to learn the C++ basics");
        lesson6.setDuration(3);
        lesson6.setVideoUrl("https://www.youtube.com/watch?v=twpFviS9z6Y");
        course4.addLesson(lesson6);
        lessonRepository.save(lesson6);
        courseRepository.save(course4);

        Lesson lesson7 = new Lesson();
        lesson7.setName("C++ Advanced");
        lesson7.setDescription("In this lesson we are going to learn advanced C++");
        lesson7.setDuration(3);
        lesson7.setVideoUrl("https://www.youtube.com/watch?v=twpFviS9z6Y");
        course4.addLesson(lesson6);
        lessonRepository.save(lesson7);
        courseRepository.save(course4);

        Lesson lesson8 = new Lesson();
        lesson8.setName("C# Basics");
        lesson8.setDescription("In this lesson we are going to learn advanced C++");
        lesson8.setDuration(3);
        lesson8.setVideoUrl("https://www.youtube.com/watch?v=twpFviS9z6Y");
        course3.addLesson(lesson8);
        lessonRepository.save(lesson8);
        courseRepository.save(course3);

        Lesson lesson9 = new Lesson();
        lesson9.setName("What is play");
        lesson9.setDescription("In this lesson we are going to learn about the meaning of playing");
        lesson9.setDuration(4);
        lesson9.setVideoUrl("https://www.youtube.com/watch?v=twpFviS9z6Y");
        course8.addLesson(lesson9);
        lessonRepository.save(lesson9);
        courseRepository.save(course8);

        Enrollment enrollment1 = new Enrollment();
        enrollment1.setId(new EnrolmentId(course7.getTitle(), user2.getUsername()));
        enrollment1.setCourse(course7);
        enrollment1.setUser(user2);
        enrollment1.setDateEnrollment(LocalDate.now());
        enrollment1.setDateUpdate(LocalDate.now());
        enrollment1.markLessonDone(lesson2);

        enrollmentRepository.save(enrollment1);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setId(new EnrolmentId(course5.getTitle(), user1.getUsername()));
        enrollment2.setCourse(course5);
        enrollment2.setUser(user1);
        enrollment2.setDateEnrollment(LocalDate.now());
        enrollment2.setDateUpdate(LocalDate.now());
        enrollment2.markLessonDone(lesson3);

        enrollmentRepository.save(enrollment2);

        Enrollment enrollment3 = new Enrollment();
        enrollment3.setId(new EnrolmentId(course4.getTitle(), user1.getUsername()));
        enrollment3.setCourse(course4);
        enrollment3.setUser(user1);
        enrollment3.setDateEnrollment(LocalDate.now());
        enrollment3.setDateUpdate(LocalDate.now());

        enrollmentRepository.save(enrollment3);

        Enrollment enrollment4 = new Enrollment();
        enrollment4.setId(new EnrolmentId(course3.getTitle(), user1.getUsername()));
        enrollment4.setCourse(course3);
        enrollment4.setUser(user1);
        enrollment4.setDateEnrollment(LocalDate.now());
        enrollment4.setDateUpdate(LocalDate.now());
        enrollment4.markLessonDone(lesson8);

        enrollmentRepository.save(enrollment4);

        Enrollment enrollment5 = new Enrollment();
        enrollment5.setId(new EnrolmentId(course8.getTitle(), user1.getUsername()));
        enrollment5.setCourse(course8);
        enrollment5.setUser(user1);
        enrollment5.setDateEnrollment(LocalDate.now());
        enrollment5.setDateUpdate(LocalDate.now());
        enrollment5.markLessonDone(lesson9);

        enrollmentRepository.save(enrollment5);

        Enrollment enrollment6 = new Enrollment();
        enrollment6.setId(new EnrolmentId(course8.getTitle(), user2.getUsername()));
        enrollment6.setCourse(course8);
        enrollment6.setUser(user2);
        enrollment6.setDateEnrollment(LocalDate.now().minusMonths(5));
        enrollment6.markLessonDone(lesson9);
        enrollment6.setDateUpdate(LocalDate.now().minusMonths(5));

        enrollmentRepository.save(enrollment6);

        Review review1 = new Review();
        review1.setTitle("C# is very easy");
        review1.setContents("C# is a very easy programming language");
        review1.setSatisfactionDegree(4);
        review1.setCreationDate(LocalDate.now());
        review1.setLastUpdateDate(LocalDate.now());
        review1.setUser(user1);
        review1.setCourse(course3);
        reviewRepository.save(review1);
        course3.addReview(review1);
        courseRepository.save(course3);

        Review review2 = new Review();
        review2.setTitle("SQL2 is very complicated");
        review2.setContents("The exams are very difficult");
        review2.setSatisfactionDegree(2);
        review2.setCreationDate(LocalDate.now());
        review2.setLastUpdateDate(LocalDate.now());
        review2.setUser(user2);
        review2.setCourse(course7);
        reviewRepository.save(review2);
        course7.addReview(review2);
        courseRepository.save(course7);

        Review review3 = new Review();
        review3.setTitle("This is booooring");
        review3.setContents("History of videogames is extremely boring");
        review3.setSatisfactionDegree(2);
        review3.setCreationDate(LocalDate.now());
        review3.setLastUpdateDate(LocalDate.now());
        review3.setUser(user2);
        review3.setCourse(course8);
        reviewRepository.save(review3);
        course8.addReview(review3);
        courseRepository.save(course8);

        Review review4 = new Review();
        review4.setTitle("This is awesome!");
        review4.setContents("History of videogames is awesome!");
        review4.setSatisfactionDegree(5);
        review4.setCreationDate(LocalDate.now().minusMonths(3));
        review4.setLastUpdateDate(LocalDate.now().minusMonths(3));
        review4.setUser(user1);
        review4.setCourse(course8);
        reviewRepository.save(review4);
        course8.addReview(review4);
        courseRepository.save(course8);
    }
}
