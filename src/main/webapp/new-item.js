const API_BASE = 'http://localhost:8080/items';

document.getElementById('itemForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const formElement = e.target;
    const formData = new FormData(formElement);

    try {
        const response = await fetch(API_BASE, {
            method: 'POST',
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