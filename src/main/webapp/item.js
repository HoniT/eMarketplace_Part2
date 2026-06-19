const API_BASE = 'http://localhost:8080/items';

document.addEventListener('DOMContentLoaded', () => {
    updateAuthUI();
    const urlParams = new URLSearchParams(window.location.search);
    const itemId = urlParams.get('id');

    if (itemId) {
        fetchItemDetails(itemId);
    } else {
        document.getElementById('itemDetailContainer').innerHTML = '<p>Item ID not provided.</p>';
    }
});

function updateAuthUI() {
    const token = localStorage.getItem('jwt');
    const authActions = document.getElementById('auth-actions');

    if (token) {
        authActions.innerHTML = `
            <a href="new-item.html" class="btn-new">New</a>
            <button onclick="logout()" class="btn-logout">Logout</button>
        `;
    } else {
        authActions.innerHTML = `
            <a href="login.html" class="btn-new">Login</a>
            <a href="register.html" class="btn-new">Register</a>
        `;
    }
}

function logout() {
    localStorage.removeItem('jwt');
    window.location.href = 'index.html';
}

async function fetchItemDetails(id) {
    try {
        const response = await fetch(`${API_BASE}/${id}`);
        if (!response.ok) throw new Error('Item not found');

        const item = await response.json();
        renderItemDetail(item);
    } catch (error) {
        document.getElementById('itemDetailContainer').innerHTML = '<p>Failed to load item.</p>';
    }
}

function renderItemDetail(item) {
    const container = document.getElementById('itemDetailContainer');

    const imageUrl = item.photoUrl ? `${API_BASE}/image/${item.photoUrl}` : 'placeholder.jpg';

    const dateObj = new Date(item.submissionTime);
    const formattedDate = !isNaN(dateObj)
        ? dateObj.toISOString().slice(0, 16).replace('T', ' ')
        : item.submissionTime;

    container.innerHTML = `
        <div class="item-detail">
            <img src="${imageUrl}" alt="${item.name}">
            <div class="item-header">
                <h2 class="item-title">${item.name}</h2>
                <div class="item-price">${item.price}$</div>
            </div>

            <div class="item-author" style="color: #666; font-size: 1rem; margin-bottom: 5px;">
                Posted by: <strong>${item.username || 'Unknown User'}</strong>
            </div>

            <div class="item-date" style="margin-bottom: 15px;">${formattedDate}</div>
            <div class="item-description" style="margin-bottom: 25px;">
                ${item.description ? item.description.replace(/\n/g, '<br>') : 'No description provided.'}
            </div>

            <hr style="border: 0; border-top: 1px solid #eee; margin-bottom: 20px;">

            <button class="btn-buy" onclick="handleDummyPurchase('${item.name.replace(/'/g, "\\'")}', ${item.price})">
                Buy Now
            </button>
        </div>
    `;
}

function handleDummyPurchase(itemName, itemPrice) {
    alert(`Thank you for your interest in "${itemName}"!\n\nThis is a dummy button.\nTotal price: $${itemPrice}`);
}