package coursefy.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private static final String[] WHITE_LIST_URL = {
            "/authenticate",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL).permitAll()
                                /* ENROLLMENTS */
                                // Create course
                                .requestMatchers(POST,"/users/{userId}/courses/{courseId}/enrollments").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                                // Mark lesson done
                                .requestMatchers(PUT,"/users/{userId}/courses/{courseId}/enrollments/markLesson").hasAnyRole("STUDENT", "TEACHER", "ADMIN")

                                /* REVIEWS */
                                // Create review
                                .requestMatchers(POST,"/users/{userId}/courses/{courseId}/enrollments/reviews").hasAnyRole("ADMIN", "STUDENT")
                                // Update review
                                .requestMatchers(PUT,"/users/{userId}/courses/{courseId}/enrollments/reviews/{reviewTitle}").hasAnyRole("ADMIN", "STUDENT")
                                // Get user's reviews listed by creation date
                                .requestMatchers(GET,"/users/{userId}/courses/{courseId}/enrollments/reviews/listByDate").hasAnyRole("UNREGISTERED", "STUDENT", "TEACHER", "ADMIN")
                                // Get user's reviews listed by satisfaction degree
                                .requestMatchers(GET,"/users/{userId}/courses/{courseId}/enrollments/reviews/listByRating").hasAnyRole("UNREGISTERED", "STUDENT", "TEACHER", "ADMIN")

                                /* COURSES */
                                // Create course
                                .requestMatchers(POST, "/courses").hasAnyRole("TEACHER", "ADMIN")
                                // Update price
                                .requestMatchers(PUT,"/courses/{id}/price").hasAnyRole("TEACHER", "ADMIN")
                                // Update availability
                                .requestMatchers(PUT,"/courses/{id}/availability").hasAnyRole("ADMIN", "TEACHER")
                                // Get course
                                .requestMatchers(GET,"/courses/{id}").hasAnyRole("TEACHER", "ADMIN")
                                // Update course
                                .requestMatchers(PUT,"/courses/{id}").hasAnyRole("TEACHER", "ADMIN")
                                // Get courses (all information)
                                .requestMatchers(GET,"/courses").hasAnyRole("TEACHER", "ADMIN")
                                // Get available courses
                                .requestMatchers(GET, "/courses/available").hasAnyRole("UNREGISTERED", "STUDENT", "TEACHER", "ADMIN")
                                // List courses by Title Or Description
                                .requestMatchers(GET,"/courses/search").hasAnyRole("TEACHER", "ADMIN")
                                // Delete all courses
                                .requestMatchers(DELETE,"/courses").hasAnyRole("ADMIN")
                                // Delete course
                                .requestMatchers(DELETE,"/courses/{id}").hasAnyRole("ADMIN")

                                /* USERS */
                                // Create user
                                .requestMatchers(POST,"/users").hasAnyRole("ADMIN")
                                // Get all users
                                .requestMatchers(GET,"/users").hasAnyRole("ADMIN")
                                // Get taken course
                                .requestMatchers(GET,"/users/{userId}/courses/{courseId}").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                                // List courses by category or languages
                                .requestMatchers(GET,"/users/searchByCtgOrLan").hasAnyRole("TEACHER", "ADMIN")
                                // List courses by last update
                                .requestMatchers(GET,"/users/searchByUpd").hasAnyRole("TEACHER", "ADMIN")
                                // List taken courses
                                .requestMatchers(GET,"/users/{userId}/courses").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                                // List finished courses
                                .requestMatchers(GET,"/users/{userId}/courses/finished").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                                // List user enrolled in course
                                .requestMatchers(GET,"/users/courses/{courseId}/enrolled").hasAnyRole("ADMIN", "STUDENT", "TEACHER")
                                // List top teachers
                                .requestMatchers(GET,"/users/bestTeachers").hasAnyRole("ADMIN","STUDENT", "TEACHER")
                                // List top students
                                .requestMatchers(GET,"/users/topStudents").hasAnyRole("ADMIN","STUDENT", "TEACHER")

                                /* LESSONS */
                                // Create lesson
                                .requestMatchers(POST,"/lessons").hasAnyRole("ADMIN", "TEACHER")
                                // Update lesson
                                .requestMatchers(PUT,"/lessons/{lessonName}").hasAnyRole("ADMIN", "TEACHER")

                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
