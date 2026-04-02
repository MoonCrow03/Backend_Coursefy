package coursefy;

import coursefy.application.dto.*;
import coursefy.application.exception.*;
import coursefy.domain.*;
import coursefy.application.dto.*;
import coursefy.application.exception.*;
import coursefy.domain.*;
import coursefy.persistence.CategoryRepository;
import coursefy.persistence.LanguageRepository;
import coursefy.persistence.UserRepository;
import coursefy.security.auth.AuthenticationRequest;
import coursefy.security.auth.AuthenticationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Comparator.comparing;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
public class CourseFyApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	void getTokens() throws Exception {

		AuthenticationRequest authRequest = new AuthenticationRequest();

		authRequest.setUsername("ruben");
		authRequest.setPassword("password123");

		String requestLogin = objectMapper.writeValueAsString(authRequest);

		MvcResult mvcResult = mockMvc.perform(post("/authenticate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestLogin))
				.andExpect(status().isOk())
				.andReturn();

		String responseContent = mvcResult.getResponse().getContentAsString();
		AuthenticationResponse authResponse = new ObjectMapper().readValue(responseContent, AuthenticationResponse.class);
		String token = authResponse.getAccessToken();

		assertNotNull(token);
	}

	@Test
	void testGetAllCourses() throws Exception {
		mockMvc.perform(get("/courses")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(8)))
				.andExpect(jsonPath("$[0].title").value("Java"))

				.andExpect(jsonPath("$[*].title", containsInAnyOrder("SQL", "C#", "C++", "Unity", "Java", "SQL2", "BBDD", "History of videogames")))
				.andExpect(jsonPath("$[*].description").exists())
				.andExpect(jsonPath("$[*].imageUrl").exists())
				.andExpect(jsonPath("$[*].available").exists())
				.andExpect(jsonPath("$[*].datePublication").exists())
				.andExpect(jsonPath("$[*].dateLastUpdate").exists())
				.andExpect(jsonPath("$[*].price").exists())
				.andExpect(jsonPath("$[*].category").exists())
				.andExpect(jsonPath("$[*].language").exists());
	}

	@Test
	void testGetCourseDoesNotExist() throws Exception {
		mockMvc.perform(get("/courses/unExistingCourse")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testCreateCourse() throws Exception {
		Category category  = categoryRepository.findByName("Programming").get();
		CategoryDTO categoryDTO = new CategoryDTO(category);

		Language language = languageRepository.findByName("English").get();
		LanguageDTO languageDTO = new LanguageDTO(language);

		User user = userRepository.findByUsername("JuliaC").get();
		UserDTO userDTO = new UserDTO(user);

		CourseDTO courseDTO = new CourseDTO("POO", "This is a POO course", "https://cdn.discordapp.com/attachments/1081166366511480882/1159016158289203241/makeitmeme_qZiy9.jpeg?ex=6538b769&is=65264269&hm=503a814b2e7950e690a9577653b67c4b75a62faa4b7982636099cd45d4af5b91&", 23, userDTO, categoryDTO, languageDTO);
		Course course = new Course(courseDTO);

		// We use objectMapper because it would be a hassle to put all the other classes inside CourseDTO manually
		String cou = objectMapper.writeValueAsString(course);

		MvcResult mvcResult = mockMvc.perform(post("/courses")
						.contentType("application/json")
						.content(cou))
				.andExpect(status().isCreated())
				.andReturn();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();

		assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
				objectMapper.writeValueAsString(courseDTO));
	}

	@Test
	void updatePrice() throws Exception {
		mockMvc.perform(put("/courses/Java/price?q=15")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.price").value(15));
	}

	@Test
	void whenNegativePrice_thenReturns400AndErrorResult () throws Exception{
		mockMvc.perform(put("/courses/Java/price?q=-5")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PriceCourseNegativeException));
	}

	@Test
	@Transactional
	void deleteCourse()	throws Exception{
		mockMvc.perform(delete("/courses/SQL"))
				.andExpect(status().isNoContent());
	}

	@Test
	void whenDuplicateCourseName_thenReturns409() throws Exception {
		String course = """
                   {\"title\": \"Java\",
                     \"description\": \"This is a starting Java course\",
                     \"imageUrl\": \"https://cdn.discordapp.com/attachments/1156139456986894416/1158703579427782737/rickroll_4k-1024x768.png?ex=651d364d&is=651be4cd&hm=ec51aef4520acd9ab291edf01777e3a84832640fef69dc6cb87dd559388194c0&\",
                     \"price\": 23
                   }
                """;
		mockMvc.perform(post("/courses")
						.contentType(MediaType.APPLICATION_JSON)
						.content(course))
				.andExpect(status().isConflict())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof CourseNameDuplicateException))
				.andExpect(result -> assertEquals("Course already exist", result.getResolvedException().getMessage()));
	}

	@Test
	void whenUpdateNonExistentCourse_thenReturn404() throws Exception{

		mockMvc.perform(put("/courses/nonExistentCourse/availability")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof CourseNotFoundException));				;

	}

	@Test
	void whenTitleNotCapitalized_thenReturn400() throws Exception{
		String course = """
                   {\"title\": \""poo"\",
                     \"description\": \"This is a POO course\",
                     \"imageUrl\": \"https://cdn.discordapp.com/attachments/1081166366511480882/1159016158289203241/makeitmeme_qZiy9.jpeg?ex=6538b769&is=65264269&hm=503a814b2e7950e690a9577653b67c4b75a62faa4b7982636099cd45d4af5b91&\",
                     \"price\": 80
                   }
                """;
		mockMvc.perform(post("/courses")
						.contentType(MediaType.APPLICATION_JSON)
						.content(course))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testListCoursesByTitleOrDescription() throws Exception {
		mockMvc.perform(get("/courses/search?q=SQL")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[*].title", containsInAnyOrder("SQL", "SQL2", "BBDD")))
				.andExpect(jsonPath("$[*].description", containsInAnyOrder("This is a SQL course", "This is an other SQL course", "This is a starting SQL course")));
	}

	@Test
	void testCreateUser() throws Exception{
		String user = """
                   {\"email\": \"aroa@gmail.com\",
                     \"username\": \"AroaO\",
                     \"gender\": \"Woman\",
                     \"name\": \"Aroa\",
                     \"first_surname\": \"Ochoa\",
                     \"second_surname\": \"Bardet\"
                   }
                """;
		UserDTO expectedUser= new UserDTO("AroaO", "Aroa", "Ochoa", "Bardet", "aroa@gmail.com", "Woman");

		MvcResult mvcResult = mockMvc.perform(post("/users")
						.contentType("application/json")
						.content(user))
				.andExpect(status().isCreated())
				.andReturn();
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
				objectMapper.writeValueAsString(expectedUser));
	}

	@Test
	void testCreateUserWithErrors() throws Exception {
		String user = """
				   {\"email\": \"aroagmail.com\",
				     \"username\": \"aroaOchoaBardet\",
				     \"gender\": \"Woman\",
				     \"name\": \"Aroa\",
				     \"first_surname\": \"Ochoa\",
				     \"second_surname\": \"Bardet\"
				   }
				""";

		mockMvc.perform(post("/users")
						.contentType("application/json")
						.content(user))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.violations").isArray())
				.andExpect(jsonPath("$.violations[*].message", containsInAnyOrder("must be a well-formed email address",
						"Nickname must begin with a capital letter. Also only letters are allowed",
						"size must be between 5 and 10")));
	}
	@Test
	void whenDuplicatedUsername_then409()throws Exception{
		UserDTO expectedUser= new UserDTO("JuliaC", "name", "fs", "ls", "julia@gmail.com", "woman");
		String user = objectMapper.writeValueAsString(expectedUser);

		mockMvc.perform(post("/users")
						.contentType("application/json")
						.content(user))
				.andExpect(status().isConflict())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNameDuplicateException));
	}

	@Test
	void testCreateCategory()throws Exception{
		String category = """
                   {\"name\": \"Design\",
                     \"description\": \"Design courses\"
                   }
                """;

		CategoryDTO expectedCategory= new CategoryDTO("Design", "Design courses");

		MvcResult mvcResult = mockMvc.perform(post("/categories")
						.contentType("application/json")
						.content(category))
				.andExpect(status().isCreated())
				.andReturn();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();

		CategoryDTO actualCategory = objectMapper.readValue(actualResponseBody, CategoryDTO.class);

		assertThat(actualCategory.getName()).isEqualToIgnoringWhitespace(
				expectedCategory.getName());
		assertThat(actualCategory.getDescription()).isEqualToIgnoringWhitespace(
				expectedCategory.getDescription());
	}

	@Test
	void whenDuplicatedCategory_then409()throws Exception{

		CategoryDTO expectedCategory= new CategoryDTO("SQL", "Courses about SQL");
		String category = objectMapper.writeValueAsString(expectedCategory);

		mockMvc.perform(post("/categories")
						.contentType("application/json")
						.content(category))
				.andExpect(status().isConflict())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof CategoryNameDuplicateException))
				.andExpect(result -> assertEquals("Category already exist", result.getResolvedException().getMessage()));



	}

	@Test
	void testSearchCoursesByCategory() throws Exception{
		Category category  = categoryRepository.findByName("Programming").get();
		String categoryId = category.getId().toString();

		mockMvc.perform(get("/users/searchByCtgOrLan?ctgId=" + categoryId)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[*].title", containsInAnyOrder("Java", "C#", "C++")));
	}

	@Test
	void testSearchCoursesByLanguage() throws Exception{
		Category category  = categoryRepository.findByName("Programming").get();
		String categoryId = category.getId().toString();
		Language language  = languageRepository.findByName("Español").get();
		String languageId = language.getId().toString();

		mockMvc.perform(get("/users/searchByCtgOrLan?lanId=" + languageId)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[*].title", containsInAnyOrder("Java", "C++", "BBDD")));
	}

	@Test
	void testSearchCoursesByCategoryAndLanguage() throws Exception{
		Category category  = categoryRepository.findByName("Programming").get();
		String categoryId = category.getId().toString();

		Language language  = languageRepository.findByName("Español").get();
		String languageId = language.getId().toString();

		mockMvc.perform(get("/users/searchByCtgOrLan?ctgId=" + categoryId + "&lanId=" + languageId)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[*].title", containsInAnyOrder("Java", "C++")));
	}

	@Test
	void testSearchCoursesByUserInOrderDate() throws Exception{

		MvcResult mvcResult = mockMvc.perform(get("/users/searchByUpd?username=JuliaC")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		List<CourseDTO> newList = List.of(objectMapper.readValue(actualResponseBody, CourseDTO[].class));
		List<CourseDTO> copyList = new ArrayList<>(newList);

		copyList.sort(comparing(CourseDTO::getDateLastUpdate).reversed());

		assertEquals(copyList, newList);
	}

	@Test
	void testCreateLesson() throws Exception{

		LessonDTO expectedLesson = new LessonDTO("How to breathe", "We are going to learn how to breathe", 1, "https://youtu.be/1sgb2cUqFiY");
		String lesson = objectMapper.writeValueAsString(expectedLesson);

		MvcResult mvcResult = mockMvc.perform(post("/courses/BBDD/lessons")
						.contentType("application/json")
						.content(lesson))
				.andExpect(status().isCreated())
				.andReturn();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();

		LessonDTO actualLesson = objectMapper.readValue(actualResponseBody, LessonDTO.class);

		assertThat(actualLesson.getName()).isEqualToIgnoringWhitespace(
				expectedLesson.getName());
		assertThat(actualLesson.getDescription()).isEqualToIgnoringWhitespace(
				expectedLesson.getDescription());
		assertThat(actualLesson.getDuration() == expectedLesson.getDuration());
		assertThat(actualLesson.getVideoUrl()).isEqualToIgnoringWhitespace(
				expectedLesson.getVideoUrl());
	}

	@Test
	void testBuyCourse() throws Exception{ // Users buy courses by enrolling in them
		EnrollmentDTO expectedEnrollment = new EnrollmentDTO(25);
		String enrollment = objectMapper.writeValueAsString(expectedEnrollment);

		MvcResult mvcResult = mockMvc.perform(post("/users/RubenZ/courses/SQL/enrollments")
						.contentType("application/json")
						.content(enrollment))
				.andExpect(status().isCreated())
				.andReturn();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();

		EnrollmentDTO actualEnrollment = objectMapper.readValue(actualResponseBody, EnrollmentDTO.class);

		EnrolmentId enrolmentId = new EnrolmentId("SQL","RubenZ");

		assertEquals(enrolmentId, actualEnrollment.getId());
	}

	@Test
	void whenMakeCourseAvailableWithoutAnyLessons() throws Exception {
		mockMvc.perform(put("/courses/BBDD/availability")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NotAvailableCourseException));

	}

	@Test
	void whenModifyingAvailableCourse() throws Exception {
		mockMvc.perform(put("/courses/SQL2/price?q=15")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NotModifiableCourseException));
	}

	@Test
	void markLessonDone() throws Exception{
		mockMvc.perform(put("/users/RubenZ/courses/SQL2/enrollments/markLesson?q=How to make a chair")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void whenAllLessonsDone() throws Exception{
		mockMvc.perform(put("/users/JuliaC/courses/Unity/enrollments/markLesson?q=Unity Basics")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof AllLessonsDoneException));
	}

	@Test
	void whenStillLessonsLeft() throws Exception{
		mockMvc.perform(put("/users/JuliaC/courses/C++/enrollments/markLesson?q=C++ Advanced")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof StillLessonsLeftToDoException));
	}

	@Test
	void getTakenCourseRuben() throws Exception {
		mockMvc.perform(get("/users/RubenZ/courses/SQL2")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testListTakenCourses() throws Exception {
		mockMvc.perform(get("/users/JuliaC/courses")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(4)))
				.andExpect(jsonPath("$[*].title", containsInAnyOrder("Unity", "C++", "C#", "History of videogames")));
	}

	@Test
	void testListFinishedCourses() throws Exception {
		mockMvc.perform(get("/users/JuliaC/courses/finished")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[*].title", containsInAnyOrder("Unity", "C#", "History of videogames")));
	}

	@Test
	void testCreateReview() throws Exception{
		ReviewDTO reviewDTO = new ReviewDTO("Unity es la vida", "Unity is vida", 5);

		String review = objectMapper.writeValueAsString(reviewDTO);

		MvcResult mvcResult = mockMvc.perform(post("/users/JuliaC/courses/Unity/enrollments/reviews")
						.contentType("application/json")
						.content(review))
				.andExpect(status().isCreated())
				.andReturn();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();

		ReviewDTO actualReview = objectMapper.readValue(actualResponseBody, ReviewDTO.class);

		assertThat(actualReview.getTitle()).isEqualToIgnoringWhitespace(
				reviewDTO.getTitle());
		assertThat(actualReview.getContents()).isEqualToIgnoringWhitespace(
				reviewDTO.getContents());
		assertThat(actualReview.getSatisfactionDegree() == reviewDTO.getSatisfactionDegree());
	}

	@Test
	void testFailCreateReview() throws Exception{
		ReviewDTO reviewDTO = new ReviewDTO("C++ es BASURA", "C++ es BASURA", 0);

		String review = objectMapper.writeValueAsString(reviewDTO);

		mockMvc.perform(post("/users/JuliaC/courses/C++/enrollments/reviews")
						.contentType("application/json")
						.content(review))
				.andExpect(status().isConflict())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof CantMakeReview));
	}

	@Test
	void whenEnrollmentDuplicate_then409() throws Exception{ // Users buy courses by enrolling in them
		EnrollmentDTO expectedEnrollment = new EnrollmentDTO(25);
		String enrollment = objectMapper.writeValueAsString(expectedEnrollment);

		mockMvc.perform(post("/users/JuliaC/courses/Unity/enrollments")
						.contentType("application/json")
						.content(enrollment))
				.andExpect(status().isConflict())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof EnrollmentIdDuplicateException))
				.andExpect(result -> assertEquals("Enrollment is already done", result.getResolvedException().getMessage()));
	}

	@Test
	void whenReviewDuplicate_then409() throws Exception{ // Users buy courses by enrolling in them
		ReviewDTO expectedReview = new ReviewDTO("good", "good", 4);
		String review = objectMapper.writeValueAsString(expectedReview);

		mockMvc.perform(post("/users/RubenZ/courses/SQL2/enrollments/reviews")
						.contentType("application/json")
						.content(review))
				.andExpect(status().isConflict())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ReviewAlreadyExistException));
	}

	@Test
	void testListUsersFromCourse() throws Exception {
		mockMvc.perform(get("/users/courses/History of videogames/enrolled")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[*].username", containsInAnyOrder("JuliaC")));
	}

	@Test
	void testListTopTeachers() throws Exception {
		mockMvc.perform(get("/users/bestTeachers?n=2&y=2023")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[*].username", containsInAnyOrder("JuliaC", "RubenZ")));
	}

	@Test
	void testListTopStudents() throws Exception {
		mockMvc.perform(get("/users/topStudents?n=2")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[*].username", containsInAnyOrder("JuliaC", "RubenZ")));
	}
}