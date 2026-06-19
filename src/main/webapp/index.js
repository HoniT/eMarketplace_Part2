const API_BASE = 'http://localhost:8080/items';
let currentPage = 0;
const pageSize = 6;
let currentSort = 'DATE_DESC'; // Default sorting

document.addEventListener('DOMContentLoaded', () => {
    updateAuthUI();
    fetchItems(currentPage);
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
    window.location.reload();
}

function changeSort() {
    currentSort = document.getElementById('sortParam').value;
    currentPage = 0; // Reset to first page on sort change
    fetchItems(currentPage);
}

async function fetchItems(pageNumber) {
    try {
        const token = localStorage.getItem('jwt');
        const headers = {};
        if (token) headers['Authorization'] = `Bearer ${token}`;

        const response = await fetch(`${API_BASE}?pageNumber=${pageNumber}&pageSize=${pageSize}&sortParam=${currentSort}`, {
            headers: headers
        });

        if (!response.ok) throw new Error('Failed to fetch');

        const items = await response.json();
        renderItems(items);
        updatePagination(items);

    } catch (error) {
        console.error("Error fetching items:", error);
        document.getElementById('itemsGrid').innerHTML = '<p>Error loading items.</p>';
    }
}

function renderItems(items) {
    const grid = document.getElementById('itemsGrid');
    grid.innerHTML = '';

    items.forEach(item => {
        const card = document.createElement('div');
        card.className = 'card';
        card.onclick = () => window.location.href = `item.html?id=${item.id}`;

        const imageUrl = item.photoUrl ? `${API_BASE}/image/${item.photoUrl}` : 'placeholder.jpg';

        card.innerHTML = `
            <img src="${imageUrl}" alt="${item.name}">
            <div class="card-info">
                <div class="card-title">${item.name}</div>
                <div class="card-price">${item.price}$</div>
            </div>
        `;
        grid.appendChild(card);
    });
}

function updatePagination(items) {
    document.getElementById('pageIndicator').innerText = currentPage + 1;
    document.getElementById('prevBtn').disabled = currentPage === 0;
    document.getElementById('nextBtn').disabled = items.length < pageSize;
}

function changePage(direction) {
    currentPage += direction;
    fetchItems(currentPage);
}