const API_BASE = 'http://localhost:8080/items';
let currentPage = 0;
const pageSize = 6;

document.addEventListener('DOMContentLoaded', () => {
    fetchItems(currentPage);
});

async function fetchItems(pageNumber) {
    try {
        const response = await fetch(`${API_BASE}?pageNumber=${pageNumber}&pageSize=${pageSize}`);
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