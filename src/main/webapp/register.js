const USER_API_BASE = 'http://localhost:8080/users';

document.getElementById('registerForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const payload = {
        username: document.getElementById('username').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value,
        birthday: document.getElementById('birthday').value
    };

    try {
        const response = await fetch(`${USER_API_BASE}/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            alert('Registration successful! Please log in.');
            window.location.href = 'login.html';
        } else {
            const err = await response.text();
            alert('Error during registration: ' + err);
        }
    } catch (error) {
        console.error("Registration failed:", error);
        alert('Network error.');
    }
});