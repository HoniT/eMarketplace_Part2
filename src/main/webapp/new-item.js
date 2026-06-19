const API_BASE = 'http://localhost:8080/items';

document.getElementById('itemForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const formElement = e.target;
    const formData = new FormData(formElement);
    const token = localStorage.getItem('jwt');

    if (!token) {
        alert('You must be logged in to post an item.');
        window.location.href = 'login.html';
        return;
    }

    try {
        const response = await fetch(API_BASE, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            body: formData
        });

        if (response.ok) {
            window.location.href = 'index.html';
        } else {
            const err = await response.text();
            alert('Error adding item: ' + err);
        }
    } catch (error) {
        console.error("Submission failed:", error);
        alert('Network error. Is the server running?');
    }
});