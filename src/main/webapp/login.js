const USER_API_BASE = 'http://localhost:8080/users';

document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const payload = {
        username: document.getElementById('usernameOrEmail').value,
        password: document.getElementById('password').value
    };

    try {
        const response = await fetch(`${USER_API_BASE}/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            const token = await response.text();
            localStorage.setItem('jwt', token);
            window.location.href = 'index.html';
        } else {
            alert('Invalid credentials');
        }
    } catch (error) {
        console.error("Login failed:", error);
        alert('Network error.');
    }
});