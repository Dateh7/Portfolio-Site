package com.projectportfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Random;

@Controller
public class MainController {

    @Autowired
    private MailService mailService;

    private final List<Project> projects = List.of(
            new Project("eazycuts", "EazyCuts", "Barbershop booking app",
                    """
<p>EazyCuts is a modern booking system designed specifically for small barbershops that need a simple, reliable way to manage appointments and walk-ins without complex software or manual paper systems.</p>

<p>Customers can easily book a haircut by selecting a date and choosing from a list of available time slots, all color-coded to show what’s open, booked, or reserved for walk-ins. Once booked, they receive an automatic confirmation and later, a reminder when their time is near.</p>

<p>For walk-in clients, a QR code displayed outside the shop links directly to a walk-in page where they can join the queue for the next available time slot — no calls or questions needed.</p>

<p>Behind the scenes, the system smartly prevents double bookings, blocks past and expired slots, and keeps everything running smoothly. It's designed to be simple for barbers to use, fast for clients, and dependable enough for real-world use.</p>

<p>This project was created to solve an everyday problem in a practical, intuitive way while still being built to scale and improve in future versions.</p>
""",
                    "Java, Spring Boot, Thymeleaf, MySQL, HTML/CSS/JavaScript, SMTP (Mail), QR Code Integration",
                    null,
                    "/images/eazycuts.png"
            ),

            new Project("festivalpass", "FestivalPass V1", "Multi-festival platform",
                    """
<p><strong>FestivalPass V1</strong> is a desktop application designed to streamline how music festivals are managed.</p>

<p>It allows customers to browse festivals, purchase tickets, and check in to events, while giving vendors the tools to apply for stalls and track their applications. Staff members are equipped with a comprehensive dashboard to manage artists, schedules, vendor assignments, and ticketing data.</p>

<p>The application emphasizes ease of use for all user roles while maintaining strong data integrity behind the scenes.</p>

<ul>
    <li> Customers: Browse, buy, and check in to events</li>
    <li> Vendors: Apply for stalls and view status</li>
    <li> Staff: Manage festivals, artists, sponsors, tickets, and finances</li>
</ul>

<p>Built with a focus on relational database practices, the system ensures accurate reporting on ticket sales, revenue tracking, and festival logistics.</p>
<p>It brings together core functions needed to run a modern festival in one cohesive tool, making it easier for event organizers to manage operations with clarity and control.</p>
""",
                    "Java, JavaFX, MS Access",
                    null,
                    "/images/festivalpass.png"
            ),

            new Project("readmewhenyouneedme", "Read Me When You Need Me", "Support site gift",
                    """
<p><strong>Read Me When You Need Me</strong> is a personal web-based gift designed as a digital scrapbook.</p>

<p>It includes 20 themed pages filled with heartfelt messages, memories, and interactive experiences tailored to specific emotions — like when you're sad, excited, or can’t sleep.</p>

<p>The project combines emotion, structure, and creativity to form a lasting digital experience that feels both personal and alive.</p>

<ul>
    <li>20 custom-designed emotional support pages</li>
    <li>Personal styling and animation effects</li>
    <li>Hosted online as a living memory</li>
</ul>

<p>More than just a website, it’s an emotional touchpoint — one that continues to grow over time.</p>
""",
                    "HTML, CSS",
                    "https://yourcustomlink.com",
                    "/images/readme.png"
            ),

            new Project("athletics", "Athletics Manager", "School athletics system",
                    "A tool to help manage teams, athletes, and events for high school sports.",
                    "Java, JavaFX, MySQL",
                    null,
                    "/images/athletics.png"
            )
    );


    @GetMapping("/projects")
    public String projectsPage(Model model) {
        model.addAttribute("projects", projects); // <- Make sure this line exists
        return "projects";
    }

    @GetMapping("/projects/{id}")
    public String projectDetails(@PathVariable String id, Model model) {
        Project found = projects.stream()
                .filter(p -> p.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);

        if (found == null) return "redirect:/projects";

        model.addAttribute("project", found);
        return "project-details";
    }

    @GetMapping("/contact")
    public String showContactForm(Model model) {
        int a = new Random().nextInt(6) + 2;
        int b = new Random().nextInt(5) + 1;
        model.addAttribute("captchaA", a);
        model.addAttribute("captchaB", b);
        model.addAttribute("captchaSum", a + b);
        return "contact";
    }

    @PostMapping("/contact")
    public String handleContact(@RequestParam String name,
                                @RequestParam String email,
                                @RequestParam String message,
                                @RequestParam int captchaAnswer,
                                @RequestParam int captchaSum,
                                Model model) {
        if (captchaAnswer != captchaSum) {
            model.addAttribute("errorMessage", "Incorrect CAPTCHA. Try again.");
            return showContactForm(model);
        }

        try {
            mailService.sendMail(email, "New Portfolio Message from " + name, message);
            model.addAttribute("successMessage", "Message sent! I’ll get back to you soon.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to send message. Try again later.");
        }

        return showContactForm(model);
    }





    @GetMapping("/")
    public String home() {
        return "index";
    }



    @GetMapping("/about")
    public String about() {
        return "about";
    }


}

