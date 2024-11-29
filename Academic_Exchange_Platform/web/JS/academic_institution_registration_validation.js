// Form validation 
function validateForm(event) {
    let email = document.getElementsByName("email")[0].value;
    let password = document.getElementsByName("password")[0].value;
    let emailErrorDiv = document.getElementById("email-error");
    let passwordErrorDiv = document.getElementById("password-error");

    let isValid = true;

    // Clear previous error messages
    emailErrorDiv.innerText = '';
    passwordErrorDiv.innerText = '';

    // Email validation
    if (!email.match(/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/)) {
        emailErrorDiv.innerText = 'Please enter a valid email.';
        isValid = false;
    }

    // Password validation
    if (password.length < 6) {
        passwordErrorDiv.innerText = 'Password must be at least 6 characters.';
        isValid = false;
    }

    // If not valid, prevent form submission
    if (!isValid) {
        event.preventDefault();
    }
}
