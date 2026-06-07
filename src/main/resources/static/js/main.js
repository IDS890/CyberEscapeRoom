document.addEventListener("DOMContentLoaded", () => {
    // 1. Loading Screen logic
    const loader = document.getElementById('loading-screen');
    if (loader) {
        setTimeout(() => {
            loader.style.opacity = '0';
            setTimeout(() => loader.style.display = 'none', 500);
            startTypingEffect();
        }, 800); // Fake connection delay
    } else {
        startTypingEffect();
    }
});

// 2. Fake Terminal Typing Effect
function startTypingEffect() {
    const terminalElement = document.querySelector('.typewriter');
    if (!terminalElement) return;

    const text = terminalElement.getAttribute('data-text');
    terminalElement.innerHTML = '';
    let i = 0;

    function type() {
        if (i < text.length) {
            terminalElement.innerHTML += text.charAt(i);
            i++;
            setTimeout(type, 30);
        }
    }
    type();
}

// 3. Form Interception for Visual Feedback
function submitChallenge(event, formId, correctValue, wrongExplanation) {
    event.preventDefault(); // Stop standard submission
    const form = document.getElementById(formId);
    const selected = form.querySelector('input[name="answer"]:checked');
    const feedbackBox = document.getElementById('feedback-box');
    const submitBtn = form.querySelector('button[type="submit"]');

    if (!selected) {
        feedbackBox.className = 'feedback-box danger';
        feedbackBox.innerHTML = '⚠️ ERROR: No parameters selected. Please select an option.';
        return;
    }

    submitBtn.disabled = true;
    submitBtn.innerHTML = "Processing...";

    if (selected.value === correctValue) {
        // Correct Answer Animation
        feedbackBox.className = 'feedback-box success';
        feedbackBox.innerHTML = '✅ Access Granted. Correct parameter detected. Bypassing security...';
    } else {
        // Incorrect Answer Animation
        feedbackBox.className = 'feedback-box danger';
        feedbackBox.innerHTML = `❌ Access Denied. ${wrongExplanation}<br><em>Proceeding with compromised status...</em>`;
    }

    // Wait 2.5 seconds to read the feedback, then submit the form to Spring Boot
    setTimeout(() => {
        form.submit();
    }, 2500);
}