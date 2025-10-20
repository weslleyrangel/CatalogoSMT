function initUsuariosPage() {
    const pageConfig = {
        page: 'usuarios',
        title: 'Usuários do Sistema',
        contentHTML: `
            <div class="card">
                <div class="table-container">
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th class="sortable" data-sort-key="usuario">Usuário <span class="sort-icon"></span></th>
                                <th class="sortable" data-sort-key="setor">Setor <span class="sort-icon"></span></th>
                                <th class="sortable" data-sort-key="perfil">Perfil de Acesso <span class="sort-icon"></span></th>
                            </tr>
                        </thead>
                        <tbody id="usuarios-table-body"></tbody>
                    </table>
                </div>
            </div>
        `
    };
    loadLayout(pageConfig);
    
    const pageContext = {
        usuarios: [],
        sortKey: 'usuario',
        sortDirection: 'asc'
    };

    loadUsersData(pageContext);
    setupUsuariosEventListeners(pageContext);
}

function setupUsuariosEventListeners(context) {
    const tableHeader = document.querySelector('.data-table thead');
    tableHeader.addEventListener('click', (event) => {
        const headerCell = event.target.closest('th.sortable');
        if (!headerCell) return;

        const newSortKey = headerCell.dataset.sortKey;
        if (context.sortKey === newSortKey) {
            context.sortDirection = context.sortDirection === 'asc' ? 'desc' : 'asc';
        } else {
            context.sortKey = newSortKey;
            context.sortDirection = 'asc';
        }
        renderUsuariosTable(context);
    });
}

async function loadUsersData(context) {
    try {
        const computadores = await request('/computadores', 'GET');
        const usuariosMap = new Map();

        computadores.forEach(comp => {
            if (comp.usuario && !usuariosMap.has(comp.usuario)) {
                usuariosMap.set(comp.usuario, {
                    usuario: comp.usuario,
                    setor: comp.setor,
                    perfil: comp.setor === 'TI' ? 'Admin' : 'Usuário' 
                });
            }
        });
        
        context.usuarios = Array.from(usuariosMap.values());
        renderUsuariosTable(context);

    } catch (error) {
        console.error('Erro ao carregar dados dos usuários:', error);
        const tableBody = document.getElementById('usuarios-table-body');
        if (tableBody) {
            tableBody.innerHTML = '<tr><td colspan="3" style="text-align: center; color: red;">Falha ao carregar dados.</td></tr>';
        }
    }
}

function renderUsuariosTable(context) {
    const { usuarios, sortKey, sortDirection } = context;
    const tableBody = document.getElementById('usuarios-table-body');
    if (!tableBody) return;

    const sortedData = [...usuarios].sort((a, b) => {
        const valA = a[sortKey] || '';
        const valB = b[sortKey] || '';
        if (valA < valB) return sortDirection === 'asc' ? -1 : 1;
        if (valA > valB) return sortDirection === 'asc' ? 1 : -1;
        return 0;
    });

    tableBody.innerHTML = '';
    if (sortedData.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="3" style="text-align: center;">Nenhum usuário encontrado.</td></tr>`;
        return;
    }

    sortedData.forEach(dados => {
        const row = document.createElement('tr');
        const perfilClass = dados.perfil === 'Admin' ? 'status-admin' : 'status-ativo';
        
        row.innerHTML = `
            <td>${dados.usuario}</td>
            <td>${dados.setor}</td>
            <td><span class="status-badge ${perfilClass}">${dados.perfil}</span></td>
        `;
        tableBody.appendChild(row);
    });

    document.querySelectorAll('.data-table th.sortable .sort-icon').forEach(icon => icon.textContent = '');
    const activeHeader = document.querySelector(`.data-table th[data-sort-key="${sortKey}"] .sort-icon`);
    if (activeHeader) {
        activeHeader.textContent = sortDirection === 'asc' ? '▲' : '▼';
    }
}