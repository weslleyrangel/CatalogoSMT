function getSidebarHTML(currentPage) {
    const links = [
        { href: 'dashboard.html', text: 'Dashboard', page: 'dashboard' },
        { href: 'computadores.html', text: 'Computadores', page: 'computadores' },
        { href: 'impressoras.html', text: 'Impressoras', page: 'impressoras' },
        { href: 'usuarios.html', text: 'Usuários', page: 'usuarios' }
    ];

    const navItems = links.map(link => 
        `<a href="${link.href}" class="nav-item ${currentPage === link.page ? 'active' : ''}">${link.text}</a>`
    ).join('');

    return `
        <aside class="sidebar">
            <div class="sidebar-header">
                <h2>Catálogo SMT</h2>
            </div>
            <nav class="sidebar-nav">
                ${navItems}
            </nav>
        </aside>
    `;
}

function getHeaderHTML() {
    const user = JSON.parse(sessionStorage.getItem('user')) || {};
    return `
        <header class="main-header">
            <h1 id="page-title"></h1>
            <div class="user-menu">
                <span id="user-name">${user.nome || 'Usuário'}</span>
                <button id="logout-button" class="btn btn-secondary">Sair</button>
            </div>
        </header>
    `;
}

function loadLayout(pageConfig) {
    const layoutContainer = document.getElementById('layout-container');
    if (!layoutContainer) return;

    const currentPage = pageConfig.page;

    layoutContainer.innerHTML = `
        ${getSidebarHTML(currentPage)}
        <main class="main-content">
            ${getHeaderHTML()}
            <div class="content" id="page-content"></div>
        </main>
    `;
    
    document.getElementById('page-title').textContent = pageConfig.title;
    document.getElementById('page-content').innerHTML = pageConfig.contentHTML;

    const logoutButton = document.getElementById('logout-button');
    if (logoutButton) {
        logoutButton.addEventListener('click', handleLogout);
    }
}