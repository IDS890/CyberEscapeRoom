package com.example.cyberescaperoom1.controller;

import com.example.cyberescaperoom1.controller.Challenge;
import com.example.cyberescaperoom1.controller.GameState;
import com.example.cyberescaperoom1.controller.InvestigationTask;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class GameController {

    private final Map<Integer, Challenge> challenges = new HashMap<>();

    public GameController() {
        initializeChallenges();
    }

    private void initializeChallenges() {
        // Challenge 1: Phishing
        challenges.put(1, new Challenge(1, "Phishing Investigation",
                "An employee reported a suspicious email asking for urgent action.",
                "<div class='evidence-box'><strong>From:</strong> IT-Support &lt;admin@1t-support-update.com&gt;<br><strong>Subject:</strong> URGENT: Password Expiry<br><strong>Body:</strong> Your account expires in 2 hours. <a href='#'>Click here to verify</a>.</div>",
                Arrays.asList(
                        new InvestigationTask("task1", "Identify the suspicious sender anomaly:", Map.of("a", "Uses the word admin", "b", "Domain is 1t-support-update instead of it-support", "c", "Sent to an employee"), "b"),
                        new InvestigationTask("task2", "What social engineering tactic is used?", Map.of("a", "Creating a false sense of urgency", "b", "Offering a financial reward", "c", "Baiting with free software"), "a")
                ),
                new InvestigationTask("final", "Is this email legitimate or phishing?", Map.of("legit", "Legitimate - Proceed", "phish", "Phishing - Quarantine"), "phish"),
                "Hackers often use lookalike domains and artificial urgency to force mistakes."
        ));

        // Challenge 2: Passwords
        challenges.put(2, new Challenge(2, "Credential Breach",
                "Several accounts were compromised. Review the leaked hashes and plaintext passwords.",
                "<div class='evidence-box'>1. admin : P@ssword123!<br>2. jsmith : jsmith2023<br>3. sconnors : T!m3Trav3l&P1zza99<br>4. tjones : P@ssword123!</div>",
                Arrays.asList(
                        new InvestigationTask("task1", "Identify the reused password issue:", Map.of("a", "jsmith used their name", "b", "admin and tjones share a password", "c", "sconnors used symbols"), "b"),
                        new InvestigationTask("task2", "Identify the strongest password:", Map.of("a", "P@ssword123!", "b", "jsmith2023", "c", "T!m3Trav3l&P1zza99"), "c")
                ),
                new InvestigationTask("final", "Which account was MOST likely brute-forced first?", Map.of("jsmith", "jsmith", "sconnors", "sconnors", "admin", "admin"), "jsmith"),
                "Using usernames or current years in passwords makes them highly vulnerable to dictionary attacks."
        ));

        // Challenge 3: Kill Chain
        challenges.put(3, new Challenge(3, "Kill Chain Timeline",
                "A cyber attack occurred. Review the sequence of events.",
                "<div class='evidence-box'>08:00 - Employee receives email with PDF.<br>08:05 - Employee opens PDF.<br>08:06 - Background script downloads payload.<br>08:10 - Outbound connection to unknown IP.</div>",
                Arrays.asList(
                        new InvestigationTask("task1", "Opening the PDF represents which stage?", Map.of("a", "Reconnaissance", "b", "Exploitation", "c", "Actions on Objectives"), "b"),
                        new InvestigationTask("task2", "The outbound connection represents:", Map.of("a", "Command & Control (C2)", "b", "Weaponization", "c", "Delivery"), "a")
                ),
                new InvestigationTask("final", "At which stage is it easiest for an email filter to stop the attack?", Map.of("a", "Command & Control", "b", "Delivery", "c", "Installation"), "b"),
                "Stopping the initial delivery via email filtering is the most effective proactive defense."
        ));

        // Challenge 4: Logs
        challenges.put(4, new Challenge(4, "Log Analysis",
                "Investigate suspicious login activity on the VPN.",
                "<div class='evidence-box'>[12:01] User: bwayne | IP: 192.168.1.50 | Success<br>[03:15] User: bwayne | IP: 45.33.22.11 (RU) | Success<br>[03:16] User: bwayne | Action: Export DB | Success</div>",
                Arrays.asList(
                        new InvestigationTask("task1", "Find the unusual login time:", Map.of("a", "12:01", "b", "03:15", "c", "03:16"), "b"),
                        new InvestigationTask("task2", "What action indicates potential data theft?", Map.of("a", "Success login", "b", "Export DB", "c", "Connecting to VPN"), "b")
                ),
                new InvestigationTask("final", "Is account 'bwayne' compromised?", Map.of("yes", "Yes - Disable Account", "no", "No - Normal Activity"), "yes"),
                "Impossible travel (two distant IPs in a short time) and off-hours data exports are massive red flags."
        ));

        // Challenge 5: URLs
        challenges.put(5, new Challenge(5, "URL Investigation",
                "Analyze these URLs submitted to the abuse inbox.",
                "<div class='evidence-box'>URL A: https://portal.office.com/login<br>URL B: http://office-portal-login.net/auth<br>URL C: https://office.com.secure-login.info</div>",
                Arrays.asList(
                        new InvestigationTask("task1", "Which URL uses a fake top-level domain to trick users?", Map.of("a", "URL A", "b", "URL B", "c", "URL C"), "c"),
                        new InvestigationTask("task2", "Which URL is lacking encryption?", Map.of("a", "URL A", "b", "URL B", "c", "URL C"), "b")
                ),
                new InvestigationTask("final", "Which is the ONLY legitimate URL?", Map.of("a", "URL A", "b", "URL B", "c", "URL C"), "a"),
                "Always verify the root domain. 'secure-login.info' is the actual domain in URL C, not office.com."
        ));

        // Challenges 6-10 (Structured identically for brevity in code, expanding on the same concepts)
        challenges.put(6, new Challenge(6, "Social Engineering Case", "A caller claims to be IT support.", "<div class='evidence-box'>Caller: 'Hi, I need your password to fix the server.'</div>", Collections.singletonList(new InvestigationTask("task1", "Identify the red flag:", Map.of("a", "Asking for password", "b", "Calling by phone"), "a")), new InvestigationTask("final", "Grant access?", Map.of("yes", "Yes", "no", "No"), "no"), "IT will never ask for your password."));
        challenges.put(7, new Challenge(7, "Malware Identification", "Security software detected unusual activity.", "<div class='evidence-box'>Process: svch0st.exe (Running from C:/Temp)</div>", Collections.singletonList(new InvestigationTask("task1", "What is suspicious?", Map.of("a", "Running from Temp", "b", "It is an exe"), "a")), new InvestigationTask("final", "Action?", Map.of("kill", "Kill Process", "ignore", "Ignore"), "kill"), "Legitimate system processes do not run from Temp folders."));
        challenges.put(8, new Challenge(8, "Security Awareness", "Review employee behavior.", "<div class='evidence-box'>Employee left PC unlocked and wrote password on sticky note.</div>", Collections.singletonList(new InvestigationTask("task1", "Identify unsafe action:", Map.of("a", "Sticky note", "b", "Taking a break"), "a")), new InvestigationTask("final", "Security risk level?", Map.of("high", "High", "low", "Low"), "high"), "Physical security is just as important as digital security."));
        challenges.put(9, new Challenge(9, "Threat Hunt", "Search for attacker activity.", "<div class='evidence-box'>Multiple failed logins followed by a successful login, then immediate creation of a new admin account.</div>", Collections.singletonList(new InvestigationTask("task1", "What does this indicate?", Map.of("a", "Brute force & Persistence", "b", "Forgot password"), "a")), new InvestigationTask("final", "Attacker objective?", Map.of("persist", "Maintain Access", "destroy", "Destroy Data"), "persist"), "Creating new admin accounts is a common persistence mechanism."));
        challenges.put(10, new Challenge(10, "Final Defense", "A ransomware attack is underway.", "<div class='evidence-box'>Alert: Mass file encryption detected on FileShare01.</div>", Collections.singletonList(new InvestigationTask("task1", "First response step?", Map.of("a", "Isolate FileShare01", "b", "Email the CEO"), "a")), new InvestigationTask("final", "Best recovery plan?", Map.of("pay", "Pay Ransom", "restore", "Restore from Offline Backups"), "restore"), "Isolation prevents spread. Offline backups guarantee recovery without paying."));
    }

    private GameState getOrCreateState(HttpSession session) {
        GameState state = (GameState) session.getAttribute("gameState");
        if (state == null) {
            state = new GameState();
            session.setAttribute("gameState", state);
        }
        return state;
    }

    @GetMapping("/challenge/{id}")
    public String challenge(@PathVariable int id, HttpSession session, Model model) {
        GameState state = getOrCreateState(session);
        state.setCurrentSector(id);
        model.addAttribute("state", state);
        model.addAttribute("challenge", challenges.get(id));
        return "challenge";
    }

    @PostMapping("/submit/{id}")
    public String submit(@PathVariable int id, @RequestParam Map<String, String> allParams, HttpSession session, Model model) {
        GameState state = getOrCreateState(session);
        Challenge challenge = challenges.get(id);

        int tasksCorrect = 0;
        List<String> feedback = new ArrayList<>();

        // Grade standard tasks
        for (InvestigationTask task : challenge.getTasks()) {
            String submittedAnswer = allParams.get(task.getId());
            if (task.getCorrectAnswer().equals(submittedAnswer)) {
                tasksCorrect++;
                feedback.add("✅ " + task.getQuestion() + " (Correct)");
            } else {
                feedback.add("❌ " + task.getQuestion() + " (Incorrect)");
            }
        }

        // Grade Final Decision
        String finalAnswer = allParams.get("final");
        boolean finalDecisionCorrect = challenge.getFinalDecision().getCorrectAnswer().equals(finalAnswer);

        if (finalDecisionCorrect) {
            feedback.add("✅ FINAL DECISION: Correct Call.");
        } else {
            feedback.add("❌ FINAL DECISION: Incorrect Call. The network remains at risk.");
        }

        // Update State
        state.addInvestigationScore(id, tasksCorrect, challenge.getTasks().size(), finalDecisionCorrect);

        model.addAttribute("state", state);
        model.addAttribute("challenge", challenge);
        model.addAttribute("feedbackLogs", feedback);
        model.addAttribute("perfectScore", (tasksCorrect == challenge.getTasks().size() && finalDecisionCorrect));
        model.addAttribute("nextSector", id < 10 ? id + 1 : -1);

        return "feedback";

    }
    // --- ADD THESE MISSING ROUTES BACK IN --- //

    @GetMapping("/")
    public String dashboard(HttpSession session, Model model) {
        model.addAttribute("state", getOrCreateState(session));
        return "index";
    }

    @GetMapping("/map")
    public String map(HttpSession session, Model model) {
        model.addAttribute("state", getOrCreateState(session));
        return "map";
    }

    @GetMapping("/result")
    public String result(HttpSession session, Model model) {
        model.addAttribute("state", getOrCreateState(session));
        return "result";
    }

    @PostMapping("/reset")
    public String reset(HttpSession session) {
        session.invalidate(); // Clears the current session
        return "redirect:/";  // Restarts the game
    }
}