// Form validation 
function validateForm(event) {
    let email = document.getElementsByName("email")[0].value;
    let password = document.getElementsByName("password")[0].value;
    let name = document.getElementsByName("name")[0].value; 
    let academicPosition = document.getElementsByName("academicPosition")[0].value; 
    
    let emailErrorDiv = document.getElementById("email-error");
    let passwordErrorDiv = document.getElementById("password-error");
    let nameErrorDiv = document.getElementById("name-error");
    let academicPositionErrorDiv = document.getElementById("academicPosition-error");

    let isValid = true;

    // Clear previous error messages
    emailErrorDiv.innerText = '';
    passwordErrorDiv.innerText = '';
    nameErrorDiv.innerText = '';
    academicPositionErrorDiv.innerText = '';

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
    
    // Name validation 
    if (!name.match(/^[A-Za-z\s]+$/)) {
        nameErrorDiv.innerText = 'Name can only contain letters and spaces.';
        isValid = false;
    }

    // Academic Position validation 
    if (!academicPosition.match(/^[A-Za-z\s]+$/)) {
        academicPositionErrorDiv.innerText = 'Academic position can only contain letters and spaces.';
        isValid = false;
    }

    // If not valid, prevent form submission
    if (!isValid) {
        event.preventDefault();
    }
}
