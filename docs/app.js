const views = {
    home: document.getElementById("home"),
    detail: document.getElementById("detail"),
    signup: document.getElementById("signup")
};

function showView(name) {
    Object.values(views).forEach(v => v.classList.remove("active"));
    views[name].classList.add("active");
}

/* CTA */
document.getElementById("cta-start").addEventListener("click", () => {
    showView("signup");
});

document.getElementById("cta-gh").addEventListener("click", () => {
    window.open("https://github.com/AminA0097/spring-ai-test-agent", "_blank");
});

/* BACK BUTTONS */
document.getElementById("back-home").addEventListener("click", () => {
    showView("home");
});

document.getElementById("back-home-2").addEventListener("click", () => {
    showView("home");
});

/* OPTIONAL FEATURE HOOK (future extension) */
const features = {
    repo: { title: "Architecture Analysis", text: "Analyzes Spring Boot structure." },
    tests: { title: "Test Generation", text: "Generates JUnit tests automatically." },
    ci: { title: "CI Execution", text: "Runs tests in Docker pipeline." },
    heal: { title: "Self-Healing", text: "Fixes failing tests automatically." }
};