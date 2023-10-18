package com.gdpdemo.GDPSprint1Project;

import java.util.Random;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.gdpdemo.GDPSprint1Project.serviceImpl.DemoServiceImp;

import com.gdpdemo.GDPSprint1Project.Repository.HomeRepository;
import com.gdpdemo.GDPSprint1Project.service.AttachmentsManager;
import com.gdpdemo.GDPSprint1Project.service.CommentsManager;
import com.gdpdemo.GDPSprint1Project.service.EmailService;
import com.gdpdemo.GDPSprint1Project.service.PostsAuthorsManager;
import com.gdpdemo.GDPSprint1Project.service.PostsManager;

import com.gdpdemo.GDPSprint1Project.storage.StorageService;

@Controller
public class HomeController {

	@Value("${upoadDir}")
	private String uploadFolder;

	@Autowired

	private static final Logger logger = Logger.getLogger(HomeController.class);
	/*
	 * { BasicConfigurator.configure(); }
	 */
	@Autowired
	private HomeRepository homeRepository;

	@Autowired
	private EmailService emailservice;

	@Autowired
	private PostsManager postsManager;

	@Autowired
	private PostsAuthorsManager postsAuthorsManager;

	@Autowired
	private StorageService storageService;

	@Autowired
	DemoServiceImp demoService;

	@Autowired
	AttachmentsManager attachmentsManager;

	ModelAndView mv = new ModelAndView();

	@RequestMapping("/")
	public String home() {
		logger.debug("Loading Scribblepad......");
		/*
		 * logger.info("INFO level message.."); logger.warn("WARN level message..!!");
		 * logger.error("ERROR level message..!!");
		 * logger.fatal("FATAL level message...!!");
		 * logger.trace("TRACE Level message..!!");
		 */
		logger.debug("Exit of Home");
		return "Home";
	}

	@RequestMapping("/createpost")
	public String createPost() {
		logger.debug("User is trying to Create a post");
		logger.debug("Exit from Creating a Post");
		return "createpost";
	}

	@RequestMapping("/SignIn")
	public String SignIn(Model model) {
		logger.debug("User is trying to Register");
		model.addAttribute("user", new Home());
		logger.debug("Exit from SignUp");
		return "SignIn";
	}

	@RequestMapping("/category")
	public String category() {
		logger.debug("Categories");
		logger.debug("Exit from Categories");
		return "category";
	}

	@RequestMapping("/dummy")
	public String dummy() {
		return "dummy";
	}

	@RequestMapping("/reported")
	public String reported() {
		return "reported";
	}

	@RequestMapping("/declined")
	public String declined() {
		return "declined";
	}

	@RequestMapping("/send-otp")
	public String sendOtp(@RequestParam("email") String email, HttpSession session) throws MessagingException {
		System.out.println("EMAIL" + email);
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		System.out.println(otp);
		emailservice.sendOtpMessage(email, otp);
		session.setAttribute("myotp", otp);
		session.setAttribute("email", email);
		return "verifyOtp";
	}

	@RequestMapping("/resetSendOtp")
	public String resetSendOtp(@RequestParam("email") String email, HttpSession session) throws MessagingException {
		System.out.println("EMAIL" + email);
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		System.out.println(otp);
		emailservice.sendOtpMessage(email, otp);
		session.setAttribute("myotp", otp);
		session.setAttribute("email", email);
		return "ResetverifyOtp";
	}

	@RequestMapping("/SignUp")
	public String SignUp(Model model) {
		model.addAttribute("user", new Home());
		return "SignUp";
	}

	@RequestMapping(value = "/ForgotPassword", method = RequestMethod.POST)
	public String registeredUser(@Validated @ModelAttribute("user") Home user, BindingResult result, Model model,
			HttpSession session) {
		try {
			if (!user.getPassword().equals(user.getRePassword())) {
				session.setAttribute("message", "Re-type your password correctly!!");
				throw new Exception("Re-type your password correctly!!");
			}

			if (result.hasErrors()) {
				System.out.println("Error" + result.toString());
				model.addAttribute("user", user);
				return "SignIn";
			}

			Home home = this.homeRepository.getUserByUserName(user.getEmail());
			if (home == null) {
				System.out.println("USER" + user);
				/* Home save = homeRepository.save(user) */;
				demoService.save(user);

				model.addAttribute("user", new Home());
				session.setAttribute("email", user.getEmail());
				return "ForgotPassword";
			}
			if (user.getEmail().equals(home.getEmail())) {
				session.setAttribute("message", "User with this email already exists");
				return "SignIn";
			}
			// session.setAttribute("message", new Message("Successfully Registered!!!",
			// "alert-success"));

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", e.getMessage());
			return "SignIn";
		}
		return "SignIn";
	}

	/*
	 * @RequestMapping("/Login") public String Login() { return "Login"; }
	 */
	@RequestMapping("/ForgotPassword")
	public String ForgotPassword() {
		return "ForgotPassword";
	}

	@RequestMapping("/ResetPassword")
	public String ResetPassword() {
		return "ResetPassword";
	}

	@RequestMapping("/ChangePassword")
	public String ChangePassword() {
		return "ChangePassword";
	}

	@RequestMapping("/library")
	public String library(Model model) {
		model.addAttribute("title", "My Post");
		model.addAttribute("posts", postsManager.getPostByCategory("library"));
		return "library";
	}

	@RequestMapping("/sports")
	public String sports(Model model) {
		model.addAttribute("title", "My Post");
		model.addAttribute("posts", postsManager.getPostByCategory("sports"));
		return "sports";
	}

	@RequestMapping("/cultural")
	public String cultural(Model model) {
		model.addAttribute("title", "My Post");
		model.addAttribute("posts", postsManager.getPostByCategory("culture"));
		return "cultural";
	}

	@RequestMapping("/admin")
	public String admin() {
		return "admin";
	}

	@RequestMapping("/misc")
	public String misc(Model model) {
		model.addAttribute("title", "My Post");
		model.addAttribute("posts", postsManager.getPostByCategory("mis"));
		return "misc";
	}

	@RequestMapping("/Login")
	public String Login(Model model) {
		model.addAttribute("title", "Login Page");
		return "Login";
	}

	@RequestMapping("/mylibraryposts")
	public String myLibraryPost(Model model) {
		model.addAttribute("title", "My Post");
		model.addAttribute("category","library");
		model.addAttribute("posts", postsManager.getAllPostsOrdered(-1, null, null, "library"));
		return "mylibraryposts";

	}

	@RequestMapping("/mysportsposts")
	public String mySportsPost(Model model) {
		model.addAttribute("title", "My Post");
		model.addAttribute("category","sports");
		model.addAttribute("posts", postsManager.getAllPostsOrdered(-1, null, null, "sports"));
		return "mysportsposts";

	}

	@RequestMapping("/mycultureposts")
	public String myCulturePost(Model model) {
		model.addAttribute("title", "My Post");
		model.addAttribute("category","cultural");
		model.addAttribute("posts", postsManager.getAllPostsOrdered(-1, null, null, "culture"));
		return "mycultureposts";

	}

	@RequestMapping("/mymiscposts")
	public String myMiscPost(Model model) {
		model.addAttribute("title", "My Post");
		model.addAttribute("category","misc");
		model.addAttribute("posts", postsManager.getAllPostsOrdered(-1, null, null, "mis"));
		return "mymiscposts";

	}

	/*
	 * @PostMapping("/add_comment") public String
	 * addComment(@RequestBody @ModelAttribute("comment") Comments comment,
	 * BindingResult br, int id_post, Model model) { if (br.hasErrors()) { return
	 * "home"; } // comment.setUsername(username); commentsManager.save(comment);
	 * return "redirect:/"; }
	 */
	@RequestMapping(value = "/category", method = RequestMethod.POST)
	public String loginUser(@Valid @ModelAttribute("user") Home user, BindingResult result, Model model,
			HttpSession session) {
		try {
			if (user.getEmail() == "" || user.getPassword() == "") {
				throw new Exception("Please Enter your Credentials!!");

			}
			/*
			 * if (result.hasErrors()) { System.out.println("Error" + result.toString());
			 * model.addAttribute("user", user); return "Login"; }
			 */
			System.out.println("USER" + user.getEmail());
			/* System.out.println(emailId); */
			Home home = this.homeRepository.getUserByUserName(user.getEmail());
			if (home == null) {
				session.setAttribute("message", "User does not exits with this email !!");
				logger.error("User does not exits with this email !!");
				return "Login";
			} else {
				/*
				 * if (!home.getPassword().matches(user.getPassword())) {
				 * session.setAttribute("message", "Please Enter Correct Password");
				 * logger.error("Please Enter Correct Password"); return "Login";
				 */
				boolean check = demoService.retrieve(home, user.getPassword());
				if (!check) {
					session.setAttribute("message", "Please Enter Correct Password");
					logger.error("Please Enter Correct Password");
					return "Login";
				}
			}
			session.setAttribute("firstName", home.getFirstName());
			session.setAttribute("lastName", home.getLastName());
			session.setAttribute("fullName", home.fullName());
			session.setAttribute("email", home.getEmail());
			logger.debug("User enters into website");
			return "category";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message(e.getMessage(), "alert-danger"));
			return "Login";
		}

	}

	@RequestMapping(value = "/verify-otp", method = RequestMethod.POST)
	public String validateOtp(@RequestParam("otp") int otpnum, HttpSession session) {
		Integer myOtp = (int) session.getAttribute("myotp");
		String email = (String) session.getAttribute("email");
		if (myOtp == otpnum) {
			Home home = this.homeRepository.getUserByUserName(email);
			session.setAttribute("message", new Message("You have Successfully Registered!!!", "alert-success"));
			return "Sucess";
		} else {
			session.setAttribute("message", "You have entered wrong otp");
			return "verifyOtp";
		}

	}

	@RequestMapping(value = "/resetverifyotp", method = RequestMethod.POST)
	public String resetValidateOtp(@RequestParam("otp") int otpnum, HttpSession session) {
		Integer myOtp = (int) session.getAttribute("myotp");
		String email = (String) session.getAttribute("email");
		if (myOtp == otpnum) {
			Home home = this.homeRepository.getUserByUserName(email);
			if (home == null) {
				session.setAttribute("message", "User does not exits with this email !!");
				return "ResetPassword";
			} else {
				session.setAttribute("message", new Message("You have Successfully Registered!!!", "alert-success"));
			}
			return "password_change_form";
		} else {
			session.setAttribute("message", "You have entered wrong otp");
			return "ResetverifyOtp";
		}

	}

	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	public String changePassword(@RequestParam("newpassword") String newpassword, HttpSession session) {
		String email = (String) session.getAttribute("email");
		Home home = this.homeRepository.getUserByUserName(email);
		home.setPassword(newpassword);
		home.setRePassword(newpassword);
		this.homeRepository.save(home);
		session.setAttribute("message", new Message("Password Successfully Changed", "alert-success"));
		return "ResetSuccess";

	}

	@RequestMapping(value = "/add_post", method = RequestMethod.POST)
	public String addPost(@RequestBody @Valid @ModelAttribute("posts") Posts postss, BindingResult br,
			BindingResult bindingResultForId, @RequestParam("files") MultipartFile[] files, HttpSession session) {
		if (br.hasErrors() || bindingResultForId.hasErrors()) {
			return "home";
		}
		String email = (String) session.getAttribute("email");
		Home home = this.homeRepository.getUserByUserName(email);
		postss.setContent(splitString(postss.getPost_content()));
		postsManager.save(postss);
		postsAuthorsManager.save(postss.getId(), home.getId());
		storageService.store(files);
		attachmentsManager.save(files, postss.getId());

		return "redirect:/category";
	}

	public String splitString(String post_content) {
		String s = "";
		if (post_content.length() > 150) {
			s = post_content.substring(0, 150) + "....";
		}
		return s;
	}

}
