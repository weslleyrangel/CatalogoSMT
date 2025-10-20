function initImpressorasPage() {
    const pageConfig = {
        page: 'impressoras',
        title: 'Gerenciar Impressoras',
        contentHTML: `
            <div class="page-header">
                <button id="open-add-modal-btn" class="btn btn-primary">Adicionar Nova</button>
            </div>
            <div class="card">
                <div class="toolbar">
                    <input type="text" id="search-input" class="form-input" placeholder="Buscar por patrimônio, modelo...">
                </div>
                <div class="table-container">
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th class="sortable" data-sort-key="patrimonio">Patrimônio <span class="sort-icon"></span></th>
                                <th class="sortable" data-sort-key="fabricante">Fabricante <span class="sort-icon"></span></th>
                                <th class="sortable" data-sort-key="modelo">Modelo <span class="sort-icon"></span></th>
                                <th class="sortable" data-sort-key="tipo">Tipo <span class="sort-icon"></span></th>
                                <th class="sortable" data-sort-key="localizacao">Localização <span class="sort-icon"></span></th>
                                <th class="sortable" data-sort-key="status">Status <span class="sort-icon"></span></th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody id="impressoras-table-body"></tbody>
                    </table>
                </div>
            </div>

            <div id="item-modal" class="modal">
                <div class="modal-content">
                    <div class="modal-header">
                        <h2 id="modal-title">Adicionar Impressora</h2>
                        <span id="close-item-modal-btn" class="close-btn">&times;</span>
                    </div>
                    <div class="modal-body">
                        <form id="item-form">
                            <div class="form-group"> <label for="patrimonio" class="form-label">Patrimônio</label> <input type="text" id="patrimonio" name="patrimonio" class="form-input" required> </div>
                            <div class="form-group"> <label for="fabricante" class="form-label">Fabricante</label> <input type="text" id="fabricante" name="fabricante" class="form-input" required> </div>
                            <div class="form-group"> <label for="modelo" class="form-label">Modelo</label> <input type="text" id="modelo" name="modelo" class="form-input" required> </div>
                            <div class="form-group"> <label for="tipo" class="form-label">Tipo</label> <input type="text" id="tipo" name="tipo" class="form-input" required> </div>
                            <div class="form-group"> <label for="localizacao" class="form-label">Localização</label> <input type="text" id="localizacao" name="localizacao" class="form-input" required> </div>
                            <div class="form-group"> <label for="status" class="form-label">Status</label> <select id="status" name="status" class="form-input" required> <option value="Ativo">Ativo</option> <option value="Manutenção">Manutenção</option> <option value="Inativo">Inativo</option> </select> </div>
                            <div class="modal-footer"> <button type="submit" class="btn btn-primary">Salvar</button> </div>
                        </form>
                    </div>
                </div>
            </div>

            <div id="delete-confirm-modal" class="modal">
                <div class="modal-content">
                    <div class="modal-header"> <h2>Confirmar Exclusão</h2> <span id="close-delete-modal-btn" class="close-btn">&times;</span> </div>
                    <div class="modal-body"> <p>Você tem certeza que deseja excluir a impressora com patrimônio <strong id="delete-item-id"></strong>?</p> </div>
                    <div class="modal-footer"> <button id="cancel-delete-btn" class="btn btn-secondary">Cancelar</button> <button id="confirm-delete-btn" class="btn btn-danger">Confirmar Exclusão</button> </div>
                </div>
            </div>
        `
    };
    loadLayout(pageConfig);
    
    const pageContext = {
        currentEditingId: null,
        deleteTargetId: null,
        impressoras: [],
        sortKey: 'patrimonio',
        sortDirection: 'asc'
    };

    loadImpressorasData(pageContext);
    setupImpressoraEventListeners(pageContext);
}

function setupImpressoraEventListeners(context) {
    const itemModal = document.getElementById('item-modal');
    const deleteModal = document.getElementById('delete-confirm-modal');
    const itemForm = document.getElementById('item-form');
    const searchInput = document.getElementById('search-input');
    const tableBody = document.getElementById('impressoras-table-body');
    const confirmDeleteBtn = document.getElementById('confirm-delete-btn');
    const tableHeader = document.querySelector('.data-table thead');

    document.getElementById('open-add-modal-btn').onclick = () => openImpressoraModal(null, context);
    document.getElementById('close-item-modal-btn').onclick = () => itemModal.style.display = 'none';
    
    // CORREÇÃO: Associa a função de submissão ao formulário
    if (itemForm) {
        itemForm.onsubmit = (event) => handleImpressoraFormSubmit(event, context);
    }

    document.getElementById('close-delete-modal-btn').onclick = () => deleteModal.style.display = 'none';
    document.getElementById('cancel-delete-btn').onclick = () => deleteModal.style.display = 'none';
    confirmDeleteBtn.onclick = () => handleConfirmDelete(context);

    tableBody.addEventListener('click', (event) => {
        const target = event.target;
        if (target.matches('.btn-edit')) {
            openImpressoraModal(target.dataset.id, context);
        } else if (target.matches('.btn-delete')) {
            openDeleteImpressoraModal(target.dataset.id, context);
        }
    });

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
        renderImpressorasTable(context);
    });

    let debounceTimer;
    searchInput.oninput = () => {
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(() => {
            loadImpressorasData(context, searchInput.value);
        }, 300);
    };

    window.onclick = (event) => {
        if (event.target === itemModal) itemModal.style.display = 'none';
        if (event.target === deleteModal) deleteModal.style.display = 'none';
    };
}

async function openImpressoraModal(patrimonio = null, context) {
    const modal = document.getElementById('item-modal');
    const title = document.getElementById('modal-title');
    const form = document.getElementById('item-form');
    const patrimonioInput = document.getElementById('patrimonio');

    form.reset();
    context.currentEditingId = patrimonio;

    if (patrimonio) {
        title.textContent = 'Editar Impressora';
        patrimonioInput.disabled = true;
        const item = await request(`/impressoras/${patrimonio}`, 'GET');
        Object.keys(item).forEach(key => {
            if (form.elements[key]) form.elements[key].value = item[key];
        });
    } else {
        title.textContent = 'Adicionar Impressora';
        patrimonioInput.disabled = false;
    }
    modal.style.display = 'block';
}

async function loadImpressorasData(context, searchTerm = '') {
    try {
        const endpoint = searchTerm ? `/impressoras/filtrar?termo=${encodeURIComponent(searchTerm)}` : '/impressoras';
        context.impressoras = await request(endpoint, 'GET');
        renderImpressorasTable(context);
    } catch (error) {
        console.error('Erro ao carregar impressoras:', error);
        document.getElementById('impressoras-table-body').innerHTML = '<tr><td colspan="7" style="text-align: center; color: red;">Falha ao carregar dados.</td></tr>';
    }
}

function renderImpressorasTable(context) {
    const { impressoras, sortKey, sortDirection } = context;
    const tableBody = document.getElementById('impressoras-table-body');
    if (!tableBody) return;

    const sortedData = [...impressoras].sort((a, b) => {
        const valA = a[sortKey] || '';
        const valB = b[sortKey] || '';
        if (valA < valB) return sortDirection === 'asc' ? -1 : 1;
        if (valA > valB) return sortDirection === 'asc' ? 1 : -1;
        return 0;
    });

    tableBody.innerHTML = '';
    if (sortedData.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="7" style="text-align: center;">Nenhuma impressora encontrada.</td></tr>`;
        return;
    }

    sortedData.forEach(imp => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${imp.patrimonio}</td>
            <td>${imp.fabricante}</td>
            <td>${imp.modelo}</td>
            <td>${imp.tipo}</td>
            <td>${imp.localizacao}</td>
            <td><span class="status-badge status-${imp.status.toLowerCase()}">${imp.status}</span></td>
            <td class="actions-cell">
                <button class="btn btn-secondary btn-sm btn-edit" data-id="${imp.patrimonio}">Editar</button>
                <button class="btn btn-danger btn-sm btn-delete" data-id="${imp.patrimonio}">Excluir</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
    
    document.querySelectorAll('.data-table th.sortable .sort-icon').forEach(icon => icon.textContent = '');
    const activeHeader = document.querySelector(`.data-table th[data-sort-key="${sortKey}"] .sort-icon`);
    if (activeHeader) {
        activeHeader.textContent = sortDirection === 'asc' ? '▲' : '▼';
    }
}

function openDeleteImpressoraModal(patrimonio, context) {
    const deleteModal = document.getElementById('delete-confirm-modal');
    document.getElementById('delete-item-id').textContent = patrimonio;
    context.deleteTargetId = patrimonio;
    deleteModal.style.display = 'block';
}

async function handleConfirmDelete(context) {
    if (!context.deleteTargetId) return;
    try {
        await request(`/impressoras/${context.deleteTargetId}`, 'DELETE');
        document.getElementById('delete-confirm-modal').style.display = 'none';
        loadImpressorasData(context);
    } catch (error) {
        alert(`Falha ao excluir a impressora: ${error.message}`);
    }
}

// NOVA FUNÇÃO: Lógica para enviar o formulário
async function handleImpressoraFormSubmit(event, context) {
    event.preventDefault();
    const data = Object.fromEntries(new FormData(event.target).entries());

    try {
        const method = context.currentEditingId ? 'PUT' : 'POST';
        const endpoint = context.currentEditingId ? `/impressoras/${context.currentEditingId}` : '/impressoras';
        await request(endpoint, method, data);
        document.getElementById('item-modal').style.display = 'none';
        loadImpressorasData(context);
    } catch (error) {
        alert(`Erro ao salvar impressora: ${error.message}`);
    }
}